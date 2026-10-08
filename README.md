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

## Build

The app has two flavors: `localMock` works offline on bundled mock responses, `remoteBack`
talks to the real backend. Build types and flavors contribute their own Koin modules
(`buildTypeModules`, `flavorModules`), so the DI graph is verified for every variant.

```
./gradlew :androidApp:assembleLocalMockDebug
./gradlew :androidApp:testLocalMockDebugUnitTest
```

Any JDK can launch `./gradlew`: the build itself runs on JDK 17. `gradle/gradle-daemon-jvm.properties`
pins the daemon JVM and the `jdk` entry of the version catalog pins the toolchain every module
compiles with; a missing JDK is downloaded by the foojay resolver. `./gradlew updateDaemonJvm
--jvm-version=<n>` plus the catalog entry is the whole procedure for moving to a newer JDK.

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
