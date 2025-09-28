plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}
android {
    namespace = "ru.bysoft.android.budget.features.transaction_detail"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.viewmodel.compose)

//
//    // probuem
//
//    // Includes the core logic for charts and other elements.
//    implementation ("com.patrykandpatrick.vico:core:1.6.5")
//
//    // For `compose`. Creates a `ChartStyle` based on an M3 Material Theme.
//    implementation ("com.patrykandpatrick.vico:compose-m3:1.6.5")
//
//    // For Jetpack Compose.
//    implementation ("com.patrykandpatrick.vico:compose:1.6.5")

    // retrofit
    implementation(libs.bundles.retrofit)

    implementation(project(":uikit"))
    implementation(project(":common"))
    implementation(project(":api"))
}