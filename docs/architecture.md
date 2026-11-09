# Architecture

How the app is split and which rules hold it together. Goals and the planned directions are in
[goals.md](goals.md); decisions with their reasons are in [decisions.md](decisions.md). Rules that
can be checked are tests (`konsist/`), graph assertions (root `build.gradle.kts`) or detekt config
(`config/detekt/detekt.yml`); everything else is listed here with the reason it is not automated yet.

## Layers

```
androidApp ──▶ features/* ──▶ data (currency-rates) ──▶ core:network ──▶ core:common ──▶ core:model
                   │                                                            ▲
                   └──────────────▶ core:designsystem (leaf, UI only)           │
                                                                     core:common ─┘
```

| Layer | Modules | Owns | Must not |
|---|---|---|---|
| App | `androidApp` | DI wiring (Koin, per flavor and build type), navigation graph, `MainActivity` | Hold business logic |
| Features | `features/*` | One screen or flow each: `*Screen` composables, `*ViewModel`, presentation state and mappers, the repository interfaces the feature needs | Depend on another feature (`bottom-navigation:host` is the one exception, it hosts the tabs), import Material widgets directly |
| Data | `features/currency-rates` today, `data/*` tomorrow | Repositories: cache (Room) + remote, exposed as `Flow` | Know about screens |
| Core | `core:network` (Retrofit API, DTOs, mock sources), `core:common` (auth interceptor, token storage, error logging), `core:model` (currencies, shared entities), `core:designsystem` (tokens, theme, components, icons) | Shared code with one reason to change each | Depend on features; `designsystem` depends on nothing in the project |
| Shared | `shared` (KMP) | Reserved for logic that will run on both platforms | Pull Android into it |

Enforced: `./gradlew assertModuleGraph` (allowed edges, max path 6), `:konsist:test`
(ViewModel coroutine rules, test shape), detekt on every module, Kover 50 % on changed logic files.

## Unidirectional data flow (UDF)

Every screen follows one shape; a new screen that deviates is a finding.

```
 user action ──▶ ViewModel method (→ UiEvent, direction) ──▶ repository ──▶ state (StateFlow) ──▶ Composable
```

- **State down.** A `*ViewModel` exposes immutable state (`StateFlow` of a sealed `*State`: loading /
  success / error) and one-shot effects (`SharedFlow` for toasts, dialogs). Composables read state and
  call back; they hold no business state of their own beyond text-field drafts.
- **Events up.** Today: one method per action on an `I*ViewModel` interface. Target: a sealed `UiEvent`
  and one `onEvent(event)` per ViewModel (goals.md, directions).
- **Repositories are the source of truth.** Target: a repository exposes `Flow`; create/update/delete
  write into the same cache so every collector updates. Today several features call `loadData()` on
  every entry; do not add more of that.
- **Mappers are pure.** `*PresentationMapper` turns model into display data and is unit-tested; no
  `Context`, no resources inside; strings come from the composable via resource ids.
- **Coroutines**: every `viewModelScope.launch` carries `errorLogger.handler(...)`; no root `async`;
  a `catch (Throwable)` rethrows `CancellationException` (detekt coroutines rules).

## Design system

`core:designsystem` is the only place with colours, radii, spacing and Material imports. Features
use kit components (`UiKitButton`, …) and tokens (`UiKitColors.colors.tokens`, `BudgetShapes`,
`BudgetSpacing`); literal `Color(0xFF…)` or `dp` radii in `features/*` are a finding. Material 3
lives inside the kit (`BudgetTheme` provides both the M3 `ColorScheme` and the legacy M2 colours);
features never import `androidx.compose.material*` widgets. Every user-visible string is in
`res/values` (en) and `res/values-ru`.

## Known gaps against the target

Recorded so nobody copies them as patterns; each is a direction in goals.md.

- `NavHostControllerWrapper` is a singleton holding the last `NavHostController`; ViewModels reach it
  through `I*Navigation` singletons. Target: navigation events from ViewModels, observed by the Activity.
- `ITransactionState` is a sealed class with eight `copyWith*` functions. Target: one editor state plus a
  `kind` sealed field.
- Repository interfaces live inside the features that use them (several `ITransactionsRepo`s). Target:
  `data:*` modules.
- `localMock` flavor is JSON mocks in `core:network`. Target: the local repositories *are* the free tier.
- The two Konsist coroutine rules match function bodies with regexes. Target: detekt rules with type
  resolution; Konsist keeps structure only.
- `AuthenticationInterceptorRefreshToken` can read token status before the first collection
  (`AuthentificationTest` is `@Ignore`d for that reason).

## Readability rules (not automatable, reviewed)

- A function does one thing its name says; a composable is either layout or logic, not both.
- Names say what, comments say why; no commented-out code.
- No `!!` outside tests; nullability is modelled in state types.
- Magic numbers are tokens or named constants, except dp/sp in layouts.
- Prefer a small data class over a `Pair`/`Triple`; prefer a sealed type over a `String` tag.
