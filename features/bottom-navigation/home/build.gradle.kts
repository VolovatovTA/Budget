plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.features.bottom_navigation.home"
}

dependencies {
    implementation(libs.coil.compose)
    
    implementation(project(":features:currency-rates"))
    implementation(project(":common"))
    implementation(project(":uikit"))
    implementation(project(":api"))
    implementation(project(":currency"))
}
