plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.features.create_update_wallet"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.kotlinx.json)
    
    implementation(project(":common"))
    implementation(project(":uikit"))
    implementation(project(":api"))
    implementation(project(":currency"))
}
