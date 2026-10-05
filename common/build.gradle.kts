plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.serialization)
}

android {
    namespace = "ru.bysoft.android.budget.common"
}


dependencies {
    implementation(libs.viewmodel.compose)

    //auth
    implementation(libs.retrofit)
    implementation(libs.retrofit.logging)

    implementation(libs.datastore.prefs)

    implementation(libs.kotlinx.json)


//    implementation "androidx.appcompat:appcompat:$appcompat_version"
//    implementation "com.google.android.material:material:$material_version"
//    implementation "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
//
    implementation(project(":currency"))
}