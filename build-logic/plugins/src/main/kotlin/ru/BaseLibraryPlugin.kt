package ru

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.bysoft.libs

class BaseLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply("com.android.library")
            apply("com.autonomousapps.dependency-analysis")
        }

        target.extensions.getByType(LibraryExtension::class.java).apply {
            compileSdk = 36

            defaultConfig {
                minSdk = 26
                lint.targetSdk = 36

                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        with(target) {
            dependencies {
                "api"(libs.findLibrary("koin-core").get())
            }
        }
    }
}