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


    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

    implementation("io.coil-kt:coil-compose:2.3.0")


    implementation(project(":uikit"))
    implementation(project(":common"))
    implementation(project(":api"))
    implementation(project(":currency"))
}