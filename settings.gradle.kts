rootProject.name = "WangsMobileUIForm"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// --- load .env into System properties and settings.extra ---
val envFile = rootDir.resolve(".env")
if (envFile.exists()) {
    envFile.readLines().forEach { raw ->
        val line = raw.trim()
        if (line.isEmpty() || line.startsWith("#")) return@forEach
        if ("=" in line) {
            val (k, v) = line.split("=", limit = 2)
            val key = k.trim()
            val value = v.trim().removeSurrounding("\"").removeSurrounding("'")
            // expose as system property for later code
            System.setProperty(key, value)
            // also set a settings extra property (optional)
            settings.extra.set(key, value)
        }
    }
}
// --- end loader ---

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/fewangsit/wangs-mobile-ui")
            credentials(PasswordCredentials::class) {
                // read from System property (set by loader) or environment variables
                username = System.getProperty("GITHUB_PACKAGES_USERNAME")
                    ?: System.getenv("GITHUB_PACKAGES_USERNAME")
                password = System.getProperty("GITHUB_PACKAGES_PASSWORD")
                    ?: System.getenv("GITHUB_PACKAGES_PASSWORD")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")
