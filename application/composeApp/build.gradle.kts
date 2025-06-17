import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        nodejs {
            testTask {
                useMocha()
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            // Kotlin Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Android
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            // Coil Image Compose
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)

            // Kotlinx Serialization
            implementation(libs.kotlinx.serialization.json)

            // Ktor for HTTP requests
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            // Kotlinx Coroutines
            runtimeOnly(libs.kotlinx.coroutines.core)

            // Koin Depencency Injection
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            // Cryptography
            implementation(libs.cryptography.core)
        }

        commonTest.dependencies {
            // Testing
            implementation(libs.kotlin.test)
            implementation(libs.junit)
            implementation(libs.ktor.client.mock)
        }

        wasmJsMain.dependencies {
            // Compose
            implementation(compose.runtime)

            // Ktor for Js
            implementation(libs.ktor.client.js)

            implementation(libs.cryptography.provider.webcrypto)
        }
    }
}

tasks.named("check") {
    dependsOn("koverXmlReport") // Ensure XML report is generated during `check`
}
