plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
    alias(libs.plugins.serialization)
}


android {
    namespace = "ru.bysoft.android.budget.features.create_update_delete_transactions"
}

dependencies {
    implementation(libs.viewmodel.compose)
//
//    implementation ("androidx.core:core-ktx:$core_ktx_version")
//    implementation ("androidx.compose.ui:ui:$compose_version")
//    implementation ("androidx.compose.ui:ui-tooling:$compose_version")
//    implementation ("androidx.compose.foundation:foundation:$compose_version")
//    implementation ("androidx.compose.material:material:$compose_version")
//    implementation ("androidx.compose.material:material-icons-core:$compose_version")
//    implementation ("androidx.compose.material:material-icons-extended:$compose_version")

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

//    implementation ("androidx.core:core-ktx:$core_ktx_version")
//    implementation ("androidx.appcompat:appcompat:$appcompat_version")
//    implementation ("com.google.android.material:material:$material_version")
//    implementation ("androidx.constraintlayout:constraintlayout:$constraintlayout_version")

    implementation(project(":features:currency-rates"))
    implementation(project(":common"))
    implementation(project(":uikit"))
    implementation(project(":api"))
    implementation(project(":currency"))
}