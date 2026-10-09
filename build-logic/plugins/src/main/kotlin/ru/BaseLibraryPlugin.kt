package ru

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.kotlin.dsl.dependencies
import ru.bysoft.libs

class BaseLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply("com.android.library")
            apply("com.autonomousapps.dependency-analysis")
            apply("org.jetbrains.kotlinx.kover")
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

        // Every library contributes its debug variant to the merged "coverage" report (root build)
        target.extensions.configure(KoverProjectExtension::class.java) {
            currentProject.createVariant("coverage") { add("debug") }
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