plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
    alias(libs.plugins.serialization)
}

android {
    namespace = "ru.bysoft.android.budget.features.create_update_delete_transactions"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.kotlinx.json)
    
    implementation(project(":features:currency-rates"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(project(":core:model"))
}
