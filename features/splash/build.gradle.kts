plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.features.splash"
}

dependencies {
//    implementation "androidx.core:core-ktx:$core_ktx_version"
//
//    //compose
//    implementation "androidx.compose.ui:ui:$compose_version"
//    implementation "androidx.compose.ui:ui-tooling:$compose_version"
//    implementation "androidx.compose.foundation:foundation:$compose_version"
//    implementation "androidx.compose.material:material:$compose_version"
//    implementation "androidx.compose.material:material-icons-core:$compose_version"
//    implementation "androidx.compose.material:material-icons-extended:$compose_version"
//    implementation "androidx.activity:activity-compose:$activity_compose_version"
    implementation(libs.viewmodel.compose)
//    implementation "com.google.accompanist:accompanist-systemuicontroller:$accompanist_systemui_controller_version"
//    implementation "androidx.navigation:navigation-compose:$nav_version"
//    implementation "androidx.compose.material:material:$compose_version"


    implementation(project(":features:currency-rates"))
    implementation(project(":common"))
    implementation(project(":uikit"))
}