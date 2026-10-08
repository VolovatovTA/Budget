plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
    alias(libs.plugins.ksp.compose)
    alias(libs.plugins.room.android)
}

android {
    namespace = "ru.bysoft.android.budget.features.currency_rates"
}

dependencies {
    implementation(libs.core.ktx)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    implementation(project(":api"))
    implementation(project(":common"))
    implementation(project(":currency"))
}

room {
    schemaDirectory("$projectDir/schemas")
}