repositories {
    google()
    gradlePluginPortal()
}

plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "4.0.7"
    id("java-gradle-plugin")
}

dependencies {
    implementation("com.android.tools.build:gradle:8.0.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
}

gradlePlugin {
    plugins.register("android-app-basic-plugin") {
        id = "android-app-basic-plugin"
        implementationClass = "com.basic.android.AndroidAppBasicPlugin"
    }
    plugins.register("android-lib-basic-plugin") {
        id = "android-lib-basic-plugin"
        implementationClass = "com.basic.android.AndroidLibBasicPlugin"
    }
}
