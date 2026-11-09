#!/bin/zsh
# After a stacked PR is merged into master (squash or rebase), rebase every PR above it and retarget
# the first one onto master. Prints one RESTACK line per action, nothing when there is nothing to do.
set -u
REPO=$(git rev-parse --show-toplevel)
WT=$REPO/build/wt-restack
cd "$REPO" || exit 1
[ -d "$WT" ] || git worktree add --detach -q "$WT" master || exit 1
cd "$WT" || exit 1
git fetch -q origin --prune 2>/dev/null
# `restack.sh --next`: master moved; rebase only the PR that is next in line (lowest number, base master,
# behind master) so its CI runs against current code, and report PRs GitHub marks as conflicting.
# Other PRs are left alone until their turn: no CI minutes spent on branches nobody merges yet.
if [ "${1:-}" = "--next" ]; then
  tip=$(git rev-parse origin/master)
  gh pr list --state open --base master --json number,headRefName,mergeable -q 'sort_by(.number)[] | "\(.number) \(.headRefName) \(.mergeable)"' | {
    done_next=0
    while read -r n head mergeable; do
      if [ "$mergeable" = "CONFLICTING" ]; then echo "RESTACK #$n: CONFLICT with master — needs a hand"; continue; fi
      [ $done_next = 1 ] && continue
      git merge-base --is-ancestor "$tip" "origin/$head" 2>/dev/null && { done_next=1; continue; }
      git checkout -q --detach "origin/$head" || continue
      if git rebase -q "$tip" >/dev/null 2>&1; then
        git push -q --force-with-lease="$head:$(git rev-parse "origin/$head")" origin "HEAD:refs/heads/$head" 2>/dev/null \
          && echo "RESTACK #$n: $head (next in line) rebased onto master ($(git rev-parse --short "$tip"))"
      else
        git rebase --abort >/dev/null 2>&1; echo "RESTACK #$n: CONFLICT rebasing $head onto master — needs a hand"
      fi
      done_next=1
    done
  }
  git checkout -q --detach origin/master 2>/dev/null; exit 0
fi
# `restack.sh --children <branch>`: a base branch got new commits (not rewritten); rebase the PRs on top of it.
if [ "${1:-}" = "--children" ]; then
  parent=$2
  gh pr list --state open --base "$parent" --json number,headRefName -q '.[] | "\(.number) \(.headRefName)"' | while read -r n head; do
    old=$(git merge-base "origin/$parent" "origin/$head"); tip=$(git rev-parse "origin/$parent")
    [ "$old" = "$tip" ] && continue
    git checkout -q --detach "origin/$head" || continue
    if git rebase -q --onto "$tip" "$old" >/dev/null 2>&1; then
      git push -q --force-with-lease="$head:$(git rev-parse "origin/$head")" origin "HEAD:refs/heads/$head" 2>/dev/null \
        && echo "RESTACK #$n: $head rebased onto $parent ($(git rev-parse --short "$tip"))"
      zsh "$REPO/scripts/restack.sh" --children "$head"
    else
      git rebase --abort >/dev/null 2>&1; echo "RESTACK #$n: CONFLICT rebasing $head onto $parent — needs a hand"
    fi
  done
  git checkout -q --detach origin/master 2>/dev/null; exit 0
fi
typeset -A oldtip newtip done_pr
prs=$(gh pr list --state open --limit 50 --json number,headRefName,baseRefName \
  -q '.[] | "\(.number) \(.headRefName) \(.baseRefName)"')
for pass in 1 2 3 4 5 6 7 8; do
  changed=0
  while read -r n head base; do
    [ -z "${n:-}" ] && continue
    [ -n "${done_pr[$n]:-}" ] && continue
    retarget=0
    merged=""
    if [ "$base" = "master" ]; then
      # GitHub already retargeted this PR (base branch deleted); find the squashed base commit still in its history
      best=999999
      # every commit of a recently merged PR counts: the branch may have been built on an earlier commit of it
      for sha in $(gh pr list --state merged --limit 20 --json commits -q '.[].commits[].oid' 2>/dev/null); do
        git merge-base --is-ancestor "$sha" "origin/$head" 2>/dev/null || continue
        git merge-base --is-ancestor "$sha" origin/master 2>/dev/null && continue
        d=$(git rev-list --count "$sha..origin/$head"); [ "$d" -lt "$best" ] && { best=$d; merged=$sha; }
      done
      [ -z "$merged" ] && continue
    else
      merged=""; best=999999
      for sha in $(gh pr list --state merged --head "$base" --limit 1 --json commits -q '.[0].commits[].oid' 2>/dev/null); do
        git merge-base --is-ancestor "$sha" "origin/$head" 2>/dev/null || continue
        d=$(git rev-list --count "$sha..origin/$head"); [ "$d" -lt "$best" ] && { best=$d; merged=$sha; }
      done
    fi
    if [ -n "$merged" ]; then
      oldbase=$merged; newbase=$(git rev-parse origin/master); [ "$base" = master ] || retarget=1; label=master
    elif [ -n "${newtip[$base]:-}" ]; then
      oldbase=${oldtip[$base]}; newbase=${newtip[$base]}; label=$base
    else
      continue
    fi
    oldtip[$head]=$(git rev-parse "origin/$head")
    if git merge-base --is-ancestor "$newbase" "${oldtip[$head]}"; then
      # already on top of the new base: only the retarget may be missing
      newtip[$head]=${oldtip[$head]}
    else
      git checkout -q --detach "origin/$head" || continue
      if ! git rebase -q --onto "$newbase" "$oldbase" >/dev/null 2>&1; then
        git rebase --abort >/dev/null 2>&1
        echo "RESTACK #$n: CONFLICT rebasing $head onto $label — needs a hand"
        done_pr[$n]=1; continue
      fi
      newtip[$head]=$(git rev-parse HEAD)
      git push -q --force-with-lease="$head:${oldtip[$head]}" origin "HEAD:refs/heads/$head" 2>/dev/null \
        || { echo "RESTACK #$n: push of $head rejected"; done_pr[$n]=1; continue; }
      echo "RESTACK #$n: $head rebased onto $label ($(git rev-parse --short "$newbase"))"
    fi
    if [ $retarget = 1 ]; then
      gh pr edit "$n" --base master >/dev/null 2>&1 && echo "RESTACK #$n: base $base → master"
    fi
    done_pr[$n]=1; changed=1
  done <<< "$prs"
  [ $changed = 0 ] && break
done
git checkout -q --detach origin/master 2>/dev/null
[ -z "$prs" ] && echo "RESTACK done: no open PRs"
exit 0
