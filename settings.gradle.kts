import java.net.URI

include(":shared")
include(":konsist")


pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = URI("https://jitpack.io")
        }
    }
}
rootProject.name = "Budget"
include(":androidApp")
include(":core:common")
include(":core:designsystem")
include(":core:model")
include(":core:network")
include(":features")
include(":features:splash")
include(":features:auth")
include(":features:bottom-navigation")
include(":features:bottom-navigation:host")
include(":features:bottom-navigation:home")
include(":features:bottom-navigation:statistic")
include(":features:create-update-wallet")
include(":features:create-udate-category")
include(":features:create-update-delete-transactions")
include(":features:statistic-by-month")
include(":features:settings")
include(":features:currency-rates")
include(":features:transaction-detail")
