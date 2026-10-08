plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.features.bottom_navigation.statistic"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":uikit"))
    implementation(project(":api"))
    implementation(project(":currency"))
}
