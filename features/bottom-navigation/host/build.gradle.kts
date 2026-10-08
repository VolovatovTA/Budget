plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.features.bottom_navigation.host"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.viewmodel.compose)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.kotlinx.json)
    
    implementation(project(":uikit"))
    implementation(project(":features:bottom-navigation:home"))
    implementation(project(":features:bottom-navigation:statistic"))
    implementation(project(":features:create-update-delete-transactions"))
    implementation(project(":common"))
}
