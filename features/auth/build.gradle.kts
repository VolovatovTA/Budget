plugins {
    alias(libs.plugins.bysoft.library)
    alias(libs.plugins.bysoft.compose)
}

android {
    namespace = "ru.bysoft.android.budget.auth"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.viewmodel.compose)
    implementation(libs.compose.activity)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.kotlinx.json)
    
    implementation(libs.play.services.auth)
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
    
    implementation(project(":common"))
    implementation(project(":uikit"))
    implementation(project(":api"))
}
