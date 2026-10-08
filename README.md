# Budget

[![Build](https://github.com/VolovatovTA/Budget/actions/workflows/build.yaml/badge.svg)](https://github.com/VolovatovTA/Budget/actions/workflows/build.yaml)
[![Tests](https://github.com/VolovatovTA/Budget/actions/workflows/test.yaml/badge.svg)](https://github.com/VolovatovTA/Budget/actions/workflows/test.yaml)
![Kotlin](https://img.shields.io/badge/Kotlin-2.4.20-7F52FF?logo=kotlin&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-9.8.1-02303A?logo=gradle&logoColor=white)
![AGP](https://img.shields.io/badge/AGP-9.4.1-3DDC84?logo=android&logoColor=white)
![Compose](https://img.shields.io/badge/Compose-1.9.2-4285F4?logo=jetpackcompose&logoColor=white)
![minSdk](https://img.shields.io/badge/minSdk-26-3DDC84?logo=android&logoColor=white)
![compileSdk](https://img.shields.io/badge/compileSdk-36-3DDC84?logo=android&logoColor=white)

Android app for tracking personal finances: wallets in different currencies, income and
expense transactions, categories and spending statistics.

## Tech stack

- Kotlin, Coroutines, Kotlin Multiplatform `shared` module
- Jetpack Compose, Navigation Compose
- Koin for dependency injection
- Retrofit + kotlinx.serialization, Room
- Multi-module Gradle build with convention plugins (`build-logic`) and a version catalog

## Project structure

| Module | Purpose |
|---|---|
| `androidApp` | Application module: DI wiring, navigation, build flavors |
| `features/*` | Feature modules: auth, home, statistics, wallets, categories, transactions, settings |
| `api` | Network API interfaces, DTOs, mappers and mock implementations |
| `common`, `currency` | Shared utilities, error handling, currency formatting |
| `uikit` | Design system: Compose components, theme, icons |
| `shared` | Kotlin Multiplatform module |

## Module graph

Four layers, dependencies only point downwards. Every arrow is a real `project(...)`
dependency from a build script; `scripts/module-graph.sh` regenerates the diagram.

```mermaid
flowchart TB
    androidApp
    subgraph features
        auth[auth]
        bn_home[bottom-navigation:home]
        bn_host[bottom-navigation:host]
        bn_statistic[bottom-navigation:statistic]
        create_udate_category[create-udate-category]
        create_update_delete_transactions[create-update-delete-transactions]
        create_update_wallet[create-update-wallet]
        settings[settings]
        splash[splash]
        statistic_by_month[statistic-by-month]
        transaction_detail[transaction-detail]
    end
    subgraph data
        currency_rates[currency-rates]
    end
    subgraph core
        api
        common
        currency
        uikit
    end
    shared[shared · KMP]

    androidApp --> shared
    androidApp --> uikit
    androidApp --> common
    androidApp --> api
    androidApp --> splash
    androidApp --> auth
    androidApp --> create_udate_category
    androidApp --> create_update_delete_transactions
    androidApp --> create_update_wallet
    androidApp --> bn_host
    androidApp --> bn_home
    androidApp --> bn_statistic
    androidApp --> statistic_by_month
    androidApp --> settings
    androidApp --> currency_rates
    api --> common
    api --> currency
    common --> currency
    auth --> common
    auth --> uikit
    auth --> api
    bn_home --> currency_rates
    bn_home --> common
    bn_home --> uikit
    bn_home --> api
    bn_home --> currency
    bn_host --> uikit
    bn_host --> bn_home
    bn_host --> bn_statistic
    bn_host --> create_update_delete_transactions
    bn_host --> common
    bn_statistic --> common
    bn_statistic --> uikit
    bn_statistic --> api
    bn_statistic --> currency
    create_udate_category --> common
    create_udate_category --> uikit
    create_udate_category --> api
    create_udate_category --> currency
    create_update_delete_transactions --> currency_rates
    create_update_delete_transactions --> common
    create_update_delete_transactions --> uikit
    create_update_delete_transactions --> api
    create_update_delete_transactions --> currency
    create_update_wallet --> common
    create_update_wallet --> uikit
    create_update_wallet --> api
    create_update_wallet --> currency
    currency_rates --> api
    currency_rates --> common
    currency_rates --> currency
    settings --> uikit
    settings --> common
    settings --> api
    settings --> currency
    splash --> currency_rates
    splash --> common
    splash --> uikit
    statistic_by_month --> uikit
    statistic_by_month --> common
    statistic_by_month --> api
```

Things the graph makes visible:

- `bottom-navigation:host` is the only feature that depends on other features: it hosts
  `home`, `statistic` and the transactions screen.
- `currency-rates` is a data module (Room, no UI) that three features read from. It sits
  between the features and the core because it needs `api`.
- `uikit` is a leaf: it depends on nothing in the project, so it can change without
  touching business code and the other way round.
- `transaction-detail` has no incoming edges: `androidApp` does not include it.
- `shared` (Kotlin Multiplatform) has no dependencies on the rest of the project yet.

## Build

The app has two flavors: `localMock` works offline on bundled mock responses, `remoteBack`
talks to the real backend. Build types and flavors contribute their own Koin modules
(`buildTypeModules`, `flavorModules`), so the DI graph is verified for every variant.

```
./gradlew :androidApp:assembleLocalMockDebug
./gradlew :androidApp:testLocalMockDebugUnitTest
```

The `remoteBack` flavor reads the exchangerate-api key from the `exchangeRateApiKey` Gradle property or the
`EXCHANGE_RATE_API_KEY` environment variable.

Unit tests cover two things that used to break silently: a Koin `verify()` check fails if any
class in the DI graph has an unregistered dependency, and a mock-assets test fails if a bundled
mock response no longer matches its response class. CI runs the tests and a debug build on every
pull request.

## Screenshots

Taken from the `localMock` build.

| Sign in | Sign up | Home | Statistics |
|---|---|---|---|
| <img src="docs/screenshots/sign-in.png" width="200"> | <img src="docs/screenshots/sign-up.png" width="200"> | <img src="docs/screenshots/home.png" width="200"> | <img src="docs/screenshots/statistics.png" width="200"> |

| New transaction | New category | Edit wallet | Settings |
|---|---|---|---|
| <img src="docs/screenshots/transaction.png" width="200"> | <img src="docs/screenshots/category.png" width="200"> | <img src="docs/screenshots/wallet.png" width="200"> | <img src="docs/screenshots/settings.png" width="200"> |
