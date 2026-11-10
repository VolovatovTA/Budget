@AGENTS.md

## Claude Code

The loop: an issue labelled `agent` → `coder` implements it on a branch and opens the PR → `reviewer`
(correctness, threads, leaks, tests) and then `architect` (layers, UDF, SOLID/DRY/KISS/YAGNI, implicit
decisions) post reviews with inline findings and a verdict → `coder` fixes, resolves the threads and
replies `coder fix N` (three rounds, then the maintainer) → CI green → the maintainer merges, or
auto-merge for the classes decision 0006 allows → `restacker` rebases the open PRs. Agents run on the Claude subscription only
(decision 0006): no `ANTHROPIC_API_KEY` anywhere.


- Agents in `.claude/agents/`: `coder` (implements an issue), `reviewer` (developer review),
  `architect` (architecture review), `restacker` (rebases open PRs after a merge). `coder` runs the two
  reviewers before declaring done.
- Git writes (commit, push, PR) never on weekdays 08:00–17:00 Europe/Belgrade; nights and weekends any
  time. Local edits, builds and tests always.
- PRs target `master`, are merged by the maintainer with squash or rebase (linear history); stacked
  PRs are rebased after each merge.
