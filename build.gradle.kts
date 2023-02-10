buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.0")
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.serialization)
        classpath(libs.anvil.gradle.plugin)
        classpath(libs.sqldelight.gradle.plugin)
    }
}

plugins {
    id("org.jetbrains.kotlin.multiplatform") version libs.versions.kotlin.get() apply false
}

allprojects {
    group = "com.taaggg.retriever"

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://s01.oss.sonatype.org/content/groups/public/")
        maven("https://jitpack.io")
    }
}