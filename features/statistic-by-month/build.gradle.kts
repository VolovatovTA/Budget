plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.features.statistic_by_month"
}

dependencies {
    implementation(libs.charts.compose)
    
    implementation(project(":uikit"))
    implementation(project(":common"))
    implementation(project(":api"))
}
