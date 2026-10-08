plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.features.settings"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.coil.compose)
    
    implementation(project(":uikit"))
    implementation(project(":common"))
    implementation(project(":api"))
    implementation(project(":currency"))
}
