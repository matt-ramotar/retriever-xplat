plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
}

kotlin {
    android()
    iosArm64()
    iosX64()
    jvm("desktop")
    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                api(libs.kotlinx.coroutines.core)
                implementation("io.socket:socket.io-client:2.1.0") {
                    exclude(group = "org.json", module = "json")
                }
            }
        }
    }
}

android {
    namespace = "ai.wandering.retriever.common.socket"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = 33
        targetSdk = 33
    }
}