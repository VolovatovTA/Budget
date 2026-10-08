package ru

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
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
        }

        // One JDK for javac, the Kotlin compiler and the bytecode level, see gradle/libs.versions.toml
        target.extensions.configure(JavaPluginExtension::class.java) {
            toolchain.languageVersion.set(JavaLanguageVersion.of(target.libs.findVersion("jdk").get().requiredVersion))
        }

        with(target) {
            dependencies {
                "api"(libs.findLibrary("koin-core").get())
            }
        }
    }
}