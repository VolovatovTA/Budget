plugins {
    alias(libs.plugins.bysoft.library)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "ru.bysoft.android.budget.uikit"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.bundles.compose)
    // Material design icons
    implementation(libs.bundles.compose.icons)
//    // Integration with activities
//    implementation "androidx.activity:activity-compose:$activity_compose_version"
//    // Integration with ViewModels
    implementation (libs.viewmodel.compose)
//    // Integration with observables
//    implementation "com.google.accompanist:accompanist-systemuicontroller:$accompanist_systemui_controller_version"
    implementation(libs.compose.shimmer)
//
//    implementation "androidx.appcompat:appcompat:$appcompat_version"
    implementation(libs.compose.material)
    // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.
    implementation(libs.charts.compose.m3)
    // For Jetpack Compose.
    implementation(libs.charts.compose)
//
//    implementation("io.coil-kt:coil-svg:2.4.0")
//
    implementation(project(":common"))
    implementation(project(":currency"))

}