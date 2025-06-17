plugins {
  // this is necessary to avoid the plugins to be loaded multiple times
  // in each subproject's classloader
  alias(libs.plugins.composeMultiplatform) apply false
  alias(libs.plugins.composeCompiler) apply false
  alias(libs.plugins.kotlinMultiplatform) apply false
  alias(libs.plugins.kover)
  alias(libs.plugins.detekt)

  kotlin("jvm") version "2.1.20"
  kotlin("plugin.serialization") version "2.1.20"
}

repositories {
  mavenCentral() // Required for Kotlin libraries
  google() // For Android-related dependencies
  gradlePluginPortal() // For Kotlin Multiplatform and plugins
}


detekt {
  toolVersion = "1.23.8" // Ensure you're using the same version as the plugin
  config = files("detekt.yml") // Optional: Specify a custom config file
  buildUponDefaultConfig = true // Use Detekt's default configuration as a base
  allRules = false // Disable all rules unless explicitly enabled
}
