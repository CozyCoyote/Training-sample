repositories {
    gradlePluginPortal()
}

plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "4.0.7"
    id("java-gradle-plugin")
}

gradlePlugin {
    plugins.register("java-basic-plugin") {
        id = "java-basic-plugin"
        implementationClass = "com.basic.android.JavaBasicPlugin"
    }
}
