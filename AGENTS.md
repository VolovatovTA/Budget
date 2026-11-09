# AGENTS.md

Instructions for coding agents working on Budget, an Android personal-finance app (Kotlin, Compose,
Koin, Room, multi-module Gradle). Read [docs/goals.md](docs/goals.md) and
[docs/architecture.md](docs/architecture.md) before changing design; decisions are in
[docs/decisions.md](docs/decisions.md).

## Commands

| What | Command |
|---|---|
| Debug build (offline flavor) | `./gradlew :androidApp:assembleLocalMockDebug` |
| Unit tests of every module | `./gradlew testLocalMockDebugUnitTest testDebugUnitTest` |
| Architecture rules | `./gradlew :konsist:test assertModuleGraph` |
| Static analysis | `./gradlew detekt` |
| Coverage report | `./gradlew koverHtmlReportCoverage` |
| Flow test on a device | `./gradlew :androidApp:connectedLocalMockDebugAndroidTest` |

CI runs build, unit tests + coverage gate, Konsist, module graph, detekt and the flow test on an
emulator for every pull request.

## Map

- `androidApp/` – DI wiring per flavor (`localMock`, `remoteBack`), navigation, `MainActivity`.
- `features/<name>/` – one screen or flow: `*Screen.kt`, `*ViewModel.kt`, `presentation/` (state,
  mappers), `data/` (repository interfaces and implementations until `data:*` modules exist).
- `features/currency-rates/` – the data module (Room + API) that home and transactions read.
- `core/network` (API, DTOs, mocks), `core/common` (auth, tokens, errors), `core/model` (currencies,
  entities), `core/designsystem` (tokens, theme, components, icons).
- `konsist/` – architecture rules as tests. `config/detekt/` – static-analysis conventions.

## Rules

1. **Dependencies point down**: app → features → data → core. Features never depend on each other
   (`host` excepted). Checked by `assertModuleGraph`.
2. **UDF per screen**: immutable state out of the ViewModel, actions in, repositories as the source of
   truth. No business state inside composables, no `loadData()` on every screen entry in new code.
3. **The kit owns the look**: components, colours, radii and spacing come from `core:designsystem`;
   no `androidx.compose.material*` widgets, literal colours or dp radii in `features/*`.
4. **Every string is a resource** in `values` and `values-ru`, in the same PR.
5. **Coroutines**: `viewModelScope.launch(errorLogger.handler { … })`, no root `async`,
   `CancellationException` is rethrown. Checked by Konsist and detekt.
6. **Tests pin behaviour**: at most two assertions per test (Konsist), one behaviour per test, no tests
   for the coverage number — exclude constants from Kover instead.
7. **Never weaken a check to pass it**: no edits to Konsist rules, detekt config, baselines or Kover
   excludes, no `@Suppress`, no deleted tests, unless the task is about that rule. A touched file
   loses its baseline entries when the fix is cheap; the baseline only shrinks.
8. **Small PRs**: about 300 changed lines, one topic, body of 2–5 lines, no commit lists. Generated
   files (baselines) are exempt and say so in the body.
9. **Non-goals stay out** ([goals.md](docs/goals.md)); a direction listed there must stay reachable.
10. **A TODO needs an issue.** Write `TODO(#123)` / `FIXME(#123)` with the issue you created for it;
    a bare TODO fails detekt (`ForbiddenComment`). `STOPSHIP` never merges.
11. **Accessible by default.** Every image or icon either has a real `contentDescription` from a string
    resource or is a `UiKitDecorativeImage`; `contentDescription = null` is banned elsewhere. Touch
    targets ≥ 48 dp, text rows use `heightIn`, layouts branch on window size class.
12. **The backend is archived** (decision 0005): no new code in `core:network`, the auth interceptor or
    token storage; new features work on local data; `remoteBack` only has to compile.

## Done means

- Build, unit tests, `:konsist:test`, `assertModuleGraph` and `detekt` pass locally.
- New logic has a test that fails without it; new UI was looked at on a device or emulator in both themes.
- The architect agent (`.claude/agents/architect.md`) reviewed the change and its findings are
  fixed or recorded in `docs/decisions.md`.

## When the architecture is in the way

Do not work around it and do not change it silently. Add a **Proposed** entry to
`docs/decisions.md` (context with evidence, options, recommendation, revisit condition), say so in your
final message and stop. Signs: a rule blocks a reasonable fix, the same change touches many modules,
you are about to add a suppression or an allowlist entry.
