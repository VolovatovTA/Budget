# Budget

Android app for tracking personal finances: wallets in different currencies, income and
expense transactions, categories and spending statistics.

## Tech stack

- Kotlin 2.2, Coroutines, Kotlin Multiplatform `shared` module
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
talks to the real backend.

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

## Status

The project is in the middle of a migration from Hilt to Koin. On the `localMock` build sign-in,
sign-up, the home screen and statistics work; the create/edit screens, settings and the
`remoteBack` flavor are not migrated yet.

## Screenshots

Taken from the `localMock` build.

| Sign in | Sign up | Home | Statistics |
|---|---|---|---|
| <img src="docs/screenshots/sign-in.png" width="200"> | <img src="docs/screenshots/sign-up.png" width="200"> | <img src="docs/screenshots/home.png" width="200"> | <img src="docs/screenshots/statistics.png" width="200"> |
