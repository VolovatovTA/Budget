# Goals

Budget is a free personal-finance app for Android: wallets in several currencies, income and expense
transactions, categories with limits, spending statistics. It is one developer's training ground as an
architect and engineer — a place to learn the Android platform and its APIs properly, with coding
agents doing much of the typing — that happens to be a useful product for the people who use it.
Nothing here is for sale: no paid tier, no ads, no monetisation.

## Goals

1. **Grow the author as an architect and engineer.** Every change is an occasion to do the thing the
   right way: explicit architecture, checked rules, honest tests, a decision log. Trying a new Android
   API or library the proper way is worth more than the shortest patch.
2. **Daily use in under ten seconds.** Adding a transaction is the main flow: open, amount, category,
   wallet, done. Everything else is secondary to that path staying short and never losing data.
3. **Local first, no account.** Wallets, categories and transactions live on the device (Room) and
   balances, limits and statistics are computed locally. The backend is **archived** (decision 0005):
   its server is a separate project that is not coming back soon, so the app is built, tested and
   shipped as a local app; the remote code stays in the tree, frozen, until that project is revived.
4. **The data is the user's.** Two-way sync of transactions, wallets and categories as a spreadsheet
   (.xlsx) on Google Drive or Yandex Disk, chosen in settings: edits in the sheet come back into the app,
   app edits go into the sheet; conflicts are latest-wins with a confirmation on same-field conflicts.
5. **A 2026-grade look, fast and animated.** One typeface with Cyrillic (Wix Madefor Display), own
   design tokens for light and dark, components in `core:designsystem` only; every string localised
   (ru, en). Scrolling and transitions stay at the display's frame rate on a mid-range phone (no jank
   on the Redmi Note 8 Pro used for checks); state changes are animated, not snapped.
6. **Less typing: voice and receipts.** A transaction can be dictated (on-device speech recognition,
   amount + category + wallet parsed from the phrase) or scanned from a receipt QR code (the fiscal
   receipt formats of the markets below), with the user confirming before it is saved.
7. **Bank data in, for free.** Spending synced from the banks the author's users actually have —
   Kaspi, Poštanska štedionica, BBVA, T-Bank, Sber or other banks not under sanctions — using whatever
   each bank gives away without a paid API contract: statement exports, open-banking sandboxes,
   notification parsing. Integration depth follows what is free; one bank at a time, behind one
   `BankConnector` interface.
8. **Kotlin Multiplatform where it pays.** Domain logic, repositories and sync move into `shared`
   so an iOS client becomes possible; UI stays native per platform.
9. **Checked, not promised.** Module graph, layering, coroutine discipline, static analysis, coverage
   gate and the instrumented flow test run on every PR; a rule that cannot be checked is written down
   in [architecture.md](architecture.md) with its reason.

## Non-goals

Agents: do not build these and do not add extension points for them.

- **Monetisation of any kind**: paid tiers, in-app purchases, subscriptions, ads, analytics for sale.
- **Backend work.** No new endpoints, DTOs, auth flows or server-dependent features while the backend
  is archived; a screen that only makes sense with a server (sign-in, sign-up, account deletion) is
  bypassed in the local mode, not improved.
- **A second UI toolkit or a second theme language.** Material 3 is reachable only through the kit;
  features never import `androidx.compose.material*` widgets directly.
- **Tests written for the coverage number.** A test pins behaviour that can break; constants and data
  holders are excluded from the measurement instead of being tested.

Anything else the maintainer has not named is open: propose it in [decisions.md](decisions.md).

## Directions (what the architecture must keep possible)

These are the moves planned next. A change that makes one of them harder is an architecture finding
even when the change itself is correct.

| Direction | What it needs from today's code |
|---|---|
| `data:*` modules, repositories as the single source of truth (`Flow` over a cache) | Features talk to repository interfaces only; nothing reads `core:network` DTOs from presentation; no screen reloads on every entry |
| Local implementation of every repository is *the* implementation | Repository interfaces return `core:model` types, not DTOs; balances, totals, limits and statistics are computed on the device; the remote implementations compile but are not wired by default |
| Two-way spreadsheet sync | Every entity has a stable id and a last-modified timestamp; no entity identity by list position |
| Navigation by events from ViewModels | No new code reaches the `NavHostController` singleton; new screens expose events, not navigation calls |
| One `onEvent(UiEvent)` per ViewModel | New ViewModels take a sealed event, not one method per tap |
| Flat feature modules, `bottom-navigation:host` into `androidApp` | No new feature-to-feature dependency |
| Konsist for structure, detekt for code | Body-level rules go to detekt rules with type resolution, not to regex tests |
| Bank connectors, voice and QR input | Transactions have one creation path (a `NewTransaction` through the repository) that every input source — form, voice, QR, bank import — feeds; no second write path, source recorded on the entity |
| Kotlin Multiplatform | New domain logic and repositories avoid Android types (`Context`, `Uri`, Android `Log`); platform access goes through small interfaces |
| Performance and animations | No work on the main thread that is not UI; lists are keyed and stable; state types are `@Immutable`/`@Stable` so Compose can skip; cold start and list scroll are measured by Macrobenchmark against `docs/performance.md`, leaks fail the flow test (LeakCanary) |

## Priorities when they conflict

1. Never lose or corrupt the user's transactions.
2. The add-transaction path stays short and works offline.
3. Keep the module graph and layering as drawn; a direction above must stay reachable.
4. When two solutions cost the same, take the one that teaches something about the platform.
5. Then looks, then speed, then new features.
