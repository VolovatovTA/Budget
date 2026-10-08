plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.ksp.compose) apply false
    alias(libs.plugins.room.android) apply false
    alias(libs.plugins.crashlitycs) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.module.graph.assert)
}

// Module dependency rules, checked by `./gradlew assertModuleGraph` (part of `check`).
// Every project(...) dependency must match one of `allowed`; `restricted` are explicit bans.
moduleGraphAssert {
    maxHeight = 6
    allowed = arrayOf(
        ":androidApp -> :.*",
        // features and the data module use the core only
        ":features:.* -> :core:.*",
        ":features:.* -> :features:currency-rates",
        // the tab host is the one feature allowed to depend on other features: it hosts their screens
        ":features:bottom-navigation:host -> :features:.*",
        // inside the core: network -> common -> model
        ":core:network -> :core:(common|model)",
        ":core:common -> :core:model",
    )
    restricted = arrayOf(
        ":.* -X> :androidApp",
        ":core:.* -X> :features:.*",
        // the design system is a leaf
        ":core:designsystem -X> :.*",
    )
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}