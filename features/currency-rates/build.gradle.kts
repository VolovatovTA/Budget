plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.ksp.compose)
    alias(libs.plugins.room.android)
}

android {
    namespace = "ru.bysoft.android.budget.features.currency_rates"
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    
    implementation(project(":api"))
    implementation(project(":common"))
    implementation(project(":currency"))

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
}

room {
    schemaDirectory("$projectDir/schemas")
}
