plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.auth"
}

dependencies {

    implementation(libs.viewmodel.compose)
    implementation(libs.compose.activity)
//    implementation ("androidx.compose.material:material-icons-core:$compose_version")
//    implementation ("androidx.compose.material:material-icons-extended:$compose_version")

//    implementation("androidx.appcompat:appcompat:1.5.1")
//    implementation ("com.google.android.material:material:$material_version")
//    implementation ("androidx.constraintlayout:constraintlayout:$constraintlayout_version")
//    testImplementation ("junit:junit:$junit_version")
//    androidTestImplementation ("androidx.test.ext:junit:$ext_junit_version")
//    androidTestImplementation ("androidx.test.espresso:espresso-core:$espresso_core_version")
//
    implementation(libs.play.services.auth)
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)

    implementation(project(":common"))
    implementation(project(":uikit"))
    implementation(project(":api"))
}