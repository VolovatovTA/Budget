plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.serialization)
}

android {
    namespace = "ru.budget.android.api"
}

dependencies {
    implementation(libs.retrofit)

    implementation(libs.coroutines.android)

    implementation(project(":common"))
    implementation(project(":currency"))

    testImplementation(libs.junit)
}