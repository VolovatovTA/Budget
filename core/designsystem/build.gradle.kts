plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.uikit"
}

dependencies {
    api(libs.compose.material3)
    api(libs.kotlinx.collections.immutable)
    implementation(libs.bundles.compose.icons)
    implementation(libs.compose.shimmer)
    implementation(libs.charts.compose)

    testImplementation(libs.junit)
}
