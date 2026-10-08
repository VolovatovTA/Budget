plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
    alias(libs.plugins.serialization)
}

android {
    namespace = "ru.bysoft.android.budget.features.create_udate_category"
}

dependencies {
    implementation(libs.retrofit)
    
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(project(":core:model"))
}
