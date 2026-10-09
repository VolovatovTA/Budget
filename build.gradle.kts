// Raise this when coverage grows; a PR that lowers it needs a reason in its description.
val COVERAGE_MIN_PERCENT = 12

plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.ksp.compose) apply false
    alias(libs.plugins.room.android) apply false
    alias(libs.plugins.crashlitycs) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.module.graph.assert)
    alias(libs.plugins.kover)
}

// Merged unit-test coverage: `./gradlew koverHtmlReportCoverage` (build/reports/kover/htmlCoverage),
// `koverXmlReportCoverage` for CI and `koverVerifyCoverage` for the minimum below.
dependencies {
    kover(project(":androidApp"))
    kover(project(":core:common"))
    kover(project(":core:model"))
    kover(project(":core:network"))
    kover(project(":core:designsystem"))
    rootProject.subprojects
        .filter { it.path.startsWith(":features:") && it.buildFile.exists() }
        .forEach { kover(it) }
}

kover {
    currentProject {
        createVariant("coverage") { }
    }
    reports {
        // measure logic, not pixels: composables, previews, generated classes and the icon catalogs
        // are excluded, so the number reflects ViewModels, repositories, mappers and utilities
        filters {
            excludes {
                annotatedBy("androidx.compose.runtime.Composable", "androidx.compose.ui.tooling.preview.Preview")
                classes("*ComposableSingletons*", "*.BuildConfig", "*.R", "*.R$*", "*Preview*", "*PreviewKt*")
                packages("ru.bysoft.android.budget.uikit.icons")
            }
        }
        verify {
            rule("line coverage must not fall below the baseline") {
                minBound(COVERAGE_MIN_PERCENT)
            }
        }
    }
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