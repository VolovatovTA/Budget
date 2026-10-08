plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.serialization)
}

android {
    namespace = "ru.budget.android.api"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.kotlinx.json)
    implementation(libs.coroutines.android)
    
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    
    testImplementation(libs.junit)
}
