import Hilt.hiltVersion

buildscript {
    extra.apply {
        set("compose_ui_version", "1.2.0")
    }
    dependencies {
        classpath(Build.sqlDelightGradlePlugin)
        classpath(Build.hiltAndroid)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val buildToolsVersion = "7.4.1"
    val kotlinVersion = "1.7.20"
    val hiltDaggerVersion = "2.44"

    id("com.android.application") version buildToolsVersion apply false
    id("com.android.library") version buildToolsVersion apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("org.jetbrains.kotlin.jvm") version kotlinVersion apply false
    id("com.google.dagger.hilt.android") version hiltDaggerVersion apply false

}

