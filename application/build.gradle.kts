plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    alias(libs.plugins.kover)

    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
}