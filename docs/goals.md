# Goals

Budget is a personal-finance app for Android: wallets in several currencies, income and expense
transactions, categories with limits, spending statistics. One developer plus coding agents; the
codebase is also the practice ground for agent-driven development, so rules are explicit and checked.

## Goals

1. **Daily use in under ten seconds.** Adding a transaction is the main flow: open, amount, category,
   wallet, done. Everything else is secondary to that path staying short and never losing data.
2. **Works without an account.** The free tier keeps wallets, categories and transactions on the
   device (Room) and computes balances, limits and statistics locally. The backend is an option, not a
   requirement.
3. **The data is the user's.** Two-way sync of transactions, wallets and categories as a spreadsheet
   (.xlsx) on Google Drive or Yandex Disk, chosen in settings: edits in the sheet come back into the app,
   app edits go into the sheet, conflicts are resolved latest-wins with a confirmation on same-field
   conflicts.
4. **One paid unlock, no ads.** Play Billing non-consumable "Pro" (RuStore billing for the Russian
   market later); what Pro unlocks is still open (account sync, cloud backup, limits).
5. **A 2026-grade look.** One typeface with Cyrillic (Wix Madefor Display), own design tokens for light
   and dark, components in `core:designsystem` only; every string localised (ru, en).
6. **Checked, not promised.** Module graph, layering, coroutine discipline, static analysis, coverage
   gate and the instrumented flow test run on every PR; a rule that cannot be checked is written down
   in [architecture.md](architecture.md) with its reason.

## Non-goals

Agents: do not build these and do not add extension points for them. Each needs a record in
[decisions.md](decisions.md) first.

- **A second UI toolkit or a second theme language.** Material 3 is reachable only through the kit;
  features never import `androidx.compose.material*` widgets directly.
- **Server-side features before the local mode exists**: shared budgets, multi-user, bank imports.
- **A custom backend protocol.** The backend stays the existing REST API; new needs go to the local
  layer first.
- **Pixel-exact charts or a charting framework of our own.** The existing chart library is drawn with
  tokens; replacing it is a decision, not a refactor.
- **Pluggable storage, pluggable sync providers beyond the two named**, generic "cloud" abstractions.
- **Tests written for the coverage number.** A test pins behaviour that can break; constants and data
  holders are excluded from the measurement instead of being tested.

## Directions (what the architecture must keep possible)

These are the moves planned next. A change that makes one of them harder is an architecture finding
even when the change itself is correct.

| Direction | What it needs from today's code |
|---|---|
| `data:*` modules, repositories as the single source of truth (`Flow` over a cache) | Features talk to repository interfaces only; nothing reads `core:network` DTOs from presentation; no screen reloads on every entry |
| Local (offline) implementation of every repository | Repository interfaces return `core:model` types, not DTOs; no backend-computed numbers assumed to be free |
| Two-way spreadsheet sync | Every entity has a stable id and a last-modified timestamp; no entity identity by list position |
| Navigation by events from ViewModels | No new code reaches the `NavHostController` singleton; new screens expose events, not navigation calls |
| One `onEvent(UiEvent)` per ViewModel | New ViewModels take a sealed event, not one method per tap |
| Flat feature modules, `bottom-navigation:host` into `androidApp` | No new feature-to-feature dependency |
| Konsist for structure, detekt for code | Body-level rules go to detekt rules with type resolution, not to regex tests |

## Priorities when they conflict

1. Never lose or corrupt the user's transactions.
2. The add-transaction path stays short and works offline.
3. Keep the module graph and layering as drawn; a direction above must stay reachable.
4. Then looks, then speed, then new features.
