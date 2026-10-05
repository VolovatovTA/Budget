plugins {
    alias(libs.plugins.bysoft.library)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "ru.bysoft.android.budget.features.statistic_by_month"
}

dependencies {
    implementation(libs.core.ktx)

    implementation(libs.viewmodel.compose)

    //compose
    implementation(libs.bundles.compose)
//    implementation "androidx.compose.animation:animation:$compose_version"
//    implementation "androidx.compose.material:material:$compose_version"

    // probuem
    // Includes the core logic for charts and other elements.
    implementation(libs.bundles.charts)

    // retrofit
    implementation(libs.bundles.retrofit)

    implementation(project(":uikit"))
    implementation(project(":common"))
    implementation(project(":api"))
}