package ru

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.bysoft.libs

/**
 * detekt for one module: project conventions from `config/detekt/detekt.yml`, pre-existing findings
 * frozen in the module's `detekt-baseline.xml` (it only shrinks), ktlint formatting and Compose rules.
 * Applied by [BaseLibraryPlugin] and directly by the modules that are not Android libraries.
 */
class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("io.gitlab.arturbosch.detekt")
        extensions.configure(DetektExtension::class.java) {
            buildUponDefaultConfig = true
            config.setFrom(rootProject.layout.projectDirectory.file("config/detekt/detekt.yml"))
            baseline = layout.projectDirectory.file("detekt-baseline.xml").asFile
            parallel = true
        }
        dependencies {
            "detektPlugins"(libs.findLibrary("detekt-formatting").get())
            "detektPlugins"(libs.findLibrary("compose-rules-detekt").get())
        }
    }
}
