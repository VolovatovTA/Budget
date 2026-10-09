// Architecture rules as unit tests (Konsist). Run with `./gradlew :konsist:test`, part of the CI test job.
plugins {
    // version comes from the root plugins block, like every other Kotlin plugin here
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    testImplementation(libs.konsist)
    testImplementation(libs.junit)
}
