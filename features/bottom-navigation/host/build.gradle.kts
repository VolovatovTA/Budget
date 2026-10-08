plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.features.bottom_navigation.host"
}

dependencies {

//    implementation ("androidx.compose.ui:ui:$compose_version")
//    implementation ("androidx.compose.ui:ui-tooling:$compose_version")
//    implementation ("androidx.compose.foundation:foundation:$compose_version")
//    implementation ("androidx.compose.material:material:$compose_version")
//    implementation ("androidx.compose.material:material-icons-core:$compose_version")
//    implementation ("androidx.compose.material:material-icons-extended:$compose_version")
//    implementation ("androidx.activity:activity-compose:$activity_compose_version")
    implementation(libs.viewmodel.compose)
    implementation(libs.compose.activity)
//    implementation ("com.google.accompanist:accompanist-systemuicontroller:$accompanist_systemui_controller_version")
    implementation(libs.compose.navigation)
//    implementation ("androidx.compose.material:material:$compose_version")

//    implementation ("ndroidx.core:core-ktx:1.7.0")
//    implementation ("ndroidx.appcompat:appcompat:1.5.1")
//    implementation ("om.google.android.material:material:1.7.0")
//    testImplementation ("unit:junit:4.13.2")
//    androidTestImplementation ("ndroidx.test.ext:junit:1.1.4")
//    androidTestImplementation ("ndroidx.test.espresso:espresso-core:3.5.0")
    implementation(project(":uikit"))
    implementation(project(":features:bottom-navigation:home"))
    implementation(project(":features:bottom-navigation:statistic"))
    implementation(project(":features:create-update-wallet"))
    implementation(project(":features:create-update-delete-transactions"))
    implementation(project(":common"))
    implementation(project(":currency"))
}