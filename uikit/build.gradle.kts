plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.uikit"
}

dependencies {
    implementation(libs.bundles.compose.icons)
    implementation(libs.viewmodel.compose)
    implementation(libs.compose.shimmer)
    implementation(libs.charts.compose)
    
    implementation(project(":common"))
    implementation(project(":currency"))
}
