package ru.bysoft

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    libraryExtension: LibraryExtension,
) {
    libraryExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            "implementation"(libs.findLibrary("compose-ui-tooling-preview").get())
            "implementation"(libs.findBundle("compose").get())
            "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            "implementation"(libs.findLibrary("lifecycle-viewmodel").get())
            "implementation"(libs.findLibrary("koin-core-viewmodel").get())
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
//        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
//        fun Provider<*>.relativeToRootProject(dir: String) = map {
//            isolated.rootProject.projectDirectory
//                .dir("build")
//                .dir(projectDir.toRelativeString(rootDir))
//        }.map { it.dir(dir) }
//
//        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
//            .relativeToRootProject("compose-metrics")
//            .let(metricsDestination::set)
//
//        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
//            .relativeToRootProject("compose-reports")
//            .let(reportsDestination::set)
//
//        stabilityConfigurationFiles
//            .add(isolated.rootProject.projectDirectory.file("compose_compiler_config.conf"))
    }
}