plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}


android {
    namespace = "ru.bysoft.android.budget.features.create_udate_category"
}

dependencies {
    implementation(libs.bundles.compose)

//    implementation "androidx.compose.ui:ui:$compose_version"
//    implementation "androidx.compose.ui:ui-tooling:$compose_version"
//    implementation "androidx.compose.foundation:foundation:$compose_version"
//    implementation "androidx.compose.material:material:$compose_version"
//    implementation "androidx.compose.material:material-icons-core:$compose_version"
//    implementation "androidx.compose.material:material-icons-extended:$compose_version"
//    implementation "androidx.activity:activity-compose:$activity_compose_version"
    implementation(libs.viewmodel.compose)
//    implementation "androidx.navigation:navigation-compose:$nav_version"
//    implementation "androidx.compose.material:material:$compose_version"

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
//
//    //hilt
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiller)
//    kapt(libs.hilt.compiller.androidx)
//    implementation(libs.hilt.navigation)
//    annotationProcessor(libs.hilt.compiller.androidx)

//    implementation "androidx.compose.runtime:runtime-livedata:$compose_version"
//    implementation "androidx.compose.runtime:runtime-rxjava2:$compose_version"
//    implementation "androidx.appcompat:appcompat:$appcompat_version"
//    implementation "com.google.android.material:material:$material_version"

    implementation(project(":common"))
    implementation(project(":uikit"))
    implementation(project(":api"))
    implementation(project(":currency"))
}