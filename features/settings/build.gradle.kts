plugins {
    alias(libs.plugins.bysoft.library)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "ru.bysoft.android.budget.features.settings"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.bundles.compose)
//    implementation ("androidx.compose.material:material-icons-core:$compose_version")
//    implementation ("androidx.compose.material:material-icons-extended:$compose_version")
//    implementation ("androidx.activity:activity-compose:$activity_compose_version")
    implementation(libs.viewmodel.compose)
//    implementation ("com.google.accompanist:accompanist-systemuicontroller:$accompanist_systemui_controller_version")
//    implementation ("androidx.navigation:navigation-compose:$nav_version")
//    implementation ("androidx.compose.material:material:$compose_version")


    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.coil.compose)


    implementation(project(":uikit"))
    implementation(project(":common"))
    implementation(project(":api"))
    implementation(project(":currency"))
}