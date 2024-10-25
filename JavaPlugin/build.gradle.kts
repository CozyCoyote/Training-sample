repositories {
    gradlePluginPortal()
}

plugins {
    id("org.gradle.kotlin.kotlin-dsl") version "5.1.2"
    id("java-gradle-plugin")
}

gradlePlugin {
    plugins.register("java-basic-plugin") {
        id = "java-basic-plugin"
        implementationClass = "com.basic.android.JavaBasicPlugin"
    }
}
