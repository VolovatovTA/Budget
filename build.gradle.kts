plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.ksp.compose) apply false
    alias(libs.plugins.room.android) apply false
    alias(libs.plugins.crashlitycs) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.dependency.analysis)
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}