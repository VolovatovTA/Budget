plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.features.splash"
}

dependencies {
    implementation(libs.viewmodel.compose)
    implementation(libs.koin.compose.viewmodel)
    
    implementation(project(":features:currency-rates"))
    implementation(project(":common"))
    implementation(project(":uikit"))
}
