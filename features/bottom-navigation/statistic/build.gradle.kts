plugins {
    alias(libs.plugins.bysoft.library)
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "ru.bysoft.android.budget.features.bottom_navigation.statistic"
}

dependencies {

    implementation(libs.bundles.compose)
//    implementation "androidx.compose.material:material:$compose_version"
//    implementation "androidx.compose.material:material-icons-core:$compose_version"
//    implementation "androidx.compose.material:material-icons-extended:$compose_version"
//    implementation "androidx.activity:activity-compose:$activity_compose_version"
    implementation(libs.viewmodel.compose)
//    implementation "com.google.accompanist:accompanist-systemuicontroller:$accompanist_systemui_controller_version"
//    implementation "androidx.navigation:navigation-compose:$nav_version"
//    implementation "androidx.compose.material:material:$compose_version"

//    implementation "com.google.accompanist:accompanist-pager:$accompanist_version"
//    implementation "com.google.accompanist:accompanist-pager-indicators:$accompanist_version"
//    implementation "androidx.core:core-splashscreen:$splash_screen_version"
//    implementation(libs.retrofit.logging)
//
//    implementation "androidx.compose.runtime:runtime-livedata:$compose_version"
//    implementation "androidx.compose.runtime:runtime-rxjava2:$compose_version"
//    implementation "androidx.appcompat:appcompat:$appcompat_version"
//    implementation "com.google.android.material:material:$material_version"
//    implementation "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
//    testImplementation "junit:junit:$junit_version"
//    androidTestImplementation "androidx.test.ext:junit:$ext_junit_version"
//    androidTestImplementation "androidx.test.espresso:espresso-core:$espresso_core_version"

    implementation(project(":common"))
    implementation(project(":uikit"))
    implementation(project(":api"))
    implementation(project(":currency"))
}