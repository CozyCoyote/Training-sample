repositories {
    google()
    gradlePluginPortal()
}

plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "5.1.2"
    id("java-gradle-plugin")
}

dependencies {
    implementation("com.android.tools.build:gradle:8.6.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
//    implementation("com.cherryperry.gfe:gradle-file-encrypt:2.0.3")
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
    plugins.register("standard-dependencies-plugin") {
        id = "standard-dependencies-plugin"
        implementationClass = "com.basic.android.AndroidStandardDependenciesPlugin"
    }
    plugins.register("secrets-check-plugin") {
        id = "secrets-check-plugin"
        implementationClass = "com.basic.android.SecretsCheckPlugin"
    }
}
