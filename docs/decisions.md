# Decisions

A lightweight decision log. One entry per decision that shapes the code: what, why, what it costs,
when to revisit. Agents add an entry with status **Proposed** when a rule in
[architecture.md](architecture.md) or a non-goal in [goals.md](goals.md) is in the way, and stop;
the maintainer accepts or rejects. An implicit decision found in code (something the code now assumes
and later work would have to undo) is reported by the architect review and recorded here too.

Format:

```
## NNNN · Title            Status: Proposed | Accepted | Rejected | Superseded · Date
Context — Decision — Consequences — Revisit when
```

## 0001 · Dependencies point down, graph is asserted · Accepted · 2026-10-09

Context: features had started importing each other and the core.
Decision: `androidApp → features → data → core` with the allowed edges listed in the root build and
checked by `assertModuleGraph`; `bottom-navigation:host` is the single feature allowed to depend on
features. Consequences: a shared need between two features goes to `core` or a data module, never to a
feature-to-feature edge. Revisit when: `host` moves into `androidApp` (then no exception is needed).

## 0002 · Material lives inside the design system · Accepted · 2026-11-09

Context: screens mixed Material 2 defaults with kit components and three fonts.
Decision: `core:designsystem` owns tokens (light/dark), the theme (Material 3 scheme mapped from the
tokens, legacy Material 2 colours kept for the migration) and every component; features use kit
components only. Consequences: a component swap is one PR across all call sites; no second typeface,
no literal colours in features. Revisit when: all Material 2 widgets are gone from features (then the
M2 bridge in `BudgetTheme` is deleted).

## 0003 · Entity colours derive from the id, not from stored data · Accepted · 2026-11-09

Context: categories and wallets have no colour field; the refresh colours them.
Decision: slot = `stablePaletteIndex(id, palette.size)` over `String.hashCode`; palettes per theme with
the same hue order; seven category hues, four wallet gradients. Consequences: the same entity has the
same colour on every screen and device without storage; the eighth category repeats a hue; an id change
(local → server id after sync) changes the colour. Revisit when: two-way sync assigns server ids to
local entities, or users ask to pick colours — then a colour field on the entity replaces the hash.

## 0004 · Tests pin behaviour, not coverage · Accepted · 2026-11-09

Context: constant-restating tests appeared to satisfy the 50 % changed-files gate.
Decision: a test has at most two assertions (Konsist `TestShapeTest`), names one behaviour and would
fail on a concrete production change; constants and data holders are excluded from Kover instead of
tested. Consequences: coverage may read lower; reviewers audit every test in a diff (count, purpose,
regression caught). Revisit when: never, by design.

## 0005 · Backend archived, the app is local-first · Accepted · 2026-11-09

Context: the server behind `core:network` is a separate project and is not expected back soon; the
app's goals (local mode, spreadsheet sync, bank imports) do not need it.
Decision: the remote path is frozen, not deleted. `remoteBack` stays a flavor that must compile but is
not what CI tests, the device checks or the store build use; `localMock` is renamed to the local mode
and its JSON mocks are replaced by the local repositories (Room) as work on the `data:*` direction
proceeds; `core:network`, the auth interceptor and token storage receive no new code. Sign-in /
sign-up are skipped in the local mode (a local account is created silently); the screens stay for the
day the backend returns. Consequences: every new feature is designed against local data only; the
design refresh still restyles auth screens only as far as shared components reach them; `localMock`
flow tests become the real tests of the shipped app. Revisit when: the server project is alive again
with a stable API — then the remote repositories are re-wired behind the same interfaces, and sync
between local data and the server becomes a feature like the spreadsheet sync.

## 0006 · Agent-driven development loop · Accepted · 2026-11-10

Context: the maintainer wants the design refresh and the infrastructure work to proceed with agents
doing the implementation and the reviews, with the maintainer merging most PRs and reading the rest.
Decision:
- **Billing.** Every automated Claude run (GitHub Actions, routines, cloud sessions) uses the
  maintainer's Claude subscription through an OAuth token (`claude setup-token`, secret
  `CLAUDE_CODE_OAUTH_TOKEN`). No `ANTHROPIC_API_KEY` exists in the repository, organisation or
  workflow environment; every agent workflow starts with a step that fails if that variable is set.
  "Extra usage" / usage credits stay off in claude.ai, so runs stop at the subscription limit.
- **Roles.** `coder` implements one issue per PR; `reviewer` and `architect` comment on every PR;
  `restacker` rebases PRs stacked on a merged branch and, after each merge, only the PR next in line
  (CI minutes are not spent on branches nobody merges yet). Agent files live in `.claude/agents/`.
- **Fix rounds (amendment 2026-11-10).** The two reviews of a pull request are posted as GitHub
  reviews with inline comments, the reviewer's first and the architect's after it (one workflow, two
  jobs in sequence), each with a verdict line (`**Verdict:** mergeable` or `changes needed`) and a round
  number; from round 2 a review repeats the previous round's findings, struck through when fixed. On an
  `agent/*` branch the architect's review starts a coder fix round: the coder treats the findings as
  claims to verify, fixes everything that holds except a Low one whose fix would be larger than the
  problem (each skipped one gets a sentence in the reply), the workflow pushes
  fast-forward to the same branch, replies with "**coder fix N**", resolves the threads marked fixed
  and answers the declined ones in place. Three rounds per PR; when the reviews after the third still
  ask for changes, the PR is labelled `needs-maintainer` and the maintainer is mentioned. Pushes, PR
  creation and review posts are made as a GitHub App the maintainer owns (secrets `AGENT_APP_ID`,
  `AGENT_APP_PRIVATE_KEY`, login in the repository variable `AGENT_BOT_LOGIN`), never with the
  repository `GITHUB_TOKEN`, whose events start no workflows, and never with the token the Claude
  action mints for itself, which it revokes when its step ends: every agent push runs CI and both
  reviews — a rebase too, so a declined finding raised again costs a round and ends with the
  maintainer at the cap, by design. Only inline threads opened by the agents are coder input; a
  person's note reaches the coder through `@claude`. Pushes and PR creation happen in workflow
  steps, never by the agent; a diff touching build scripts, checks, baselines or the agent
  instructions is rejected unless the issue is labelled `infra`, and `.github/workflows/**` is
  rejected always: the action refuses a workflow file that differs from `master`, so an agent could
  never get such a change reviewed by the loop — workflows are the maintainer's by hand. The `agent` and `infra` labels are set by the maintainer only, on issues they
  wrote or read in full: the workflow cannot tell who applied a label, so the label is the trust
  decision. An `infra` PR is never auto-merged, whatever class it falls in; the documentation class
  below therefore covers only `docs/**` and `*.md` outside the agent instructions.
  Revisit when: a fix round weakens a check or most PRs reach the cap.
- **Merging.** The maintainer merges by default. Auto-merge (`gh pr merge --auto --squash`) is allowed
  only when all of: CI green; the architect reported "no architecture findings"; the PR is entirely one
  of — formatting produced by detekt auto-correct, a
  baseline that only shrinks, a patch-level dependency update. Everything with behaviour or design
  waits for the maintainer. Documentation PRs (`docs/**`, `*.md` except `AGENTS.md` and
  `CLAUDE.md`) get no agent review (the review workflow ignores those paths) and are merged by the
  maintainer after reading them; `.claude/**`, `.github/**`, `AGENTS.md` and `CLAUDE.md` need the
  `infra` label and the maintainer.
- **Time window.** No commits, pushes or PRs on weekdays between 08:00 and 17:00 Europe/Belgrade —
  by people or agents; nights and weekends are free. An issue labelled during working hours is picked
  up by the 17:05 sweep.
Consequences: the maintainer's work is labelling issues, reading reports and merging; the gates
(detekt, Konsist, module graph, Kover, flow test, baseline-growth guard, buildHealth) are what keeps
an agent honest, so weakening any of them is itself a review finding.
Revisit when: the subscription limit is hit regularly (then fewer scheduled runs, not an API key), or a
merged agent PR causes a regression the gates did not catch (then the class is removed from
auto-merge).

