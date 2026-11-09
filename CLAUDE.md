@AGENTS.md

## Claude Code

- Agent `architect` (`.claude/agents/architect.md`): reviews a branch for architecture (layers, UDF,
  SOLID, readability) and for decisions the code fixes implicitly; run it before declaring a
  non-trivial change done. Read-only.
- Git writes (commit, push, PR) only after 17:00 local time; local edits, builds and tests any time.
- PRs target `master`, are merged by the maintainer with squash or rebase (linear history); stacked
  PRs are rebased after each merge.
