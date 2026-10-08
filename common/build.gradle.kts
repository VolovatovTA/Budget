plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.serialization)
}

android {
    namespace = "ru.bysoft.android.budget.common"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.datastore.prefs)
    implementation(libs.kotlinx.json)
    
    implementation(project(":currency"))
    
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.coroutines.test)
}
