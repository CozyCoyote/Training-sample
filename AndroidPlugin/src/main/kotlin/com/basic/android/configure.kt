package com.basic.android

import com.android.build.gradle.TestedExtension
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun TestedExtension.configure() {
    compileSdkVersion(33)
    defaultConfig.apply {
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    (this as ExtensionAware).extensions.configure(KotlinJvmOptions::class.java) {
        jvmTarget = "17"
    }
}
