package com.basic.android

import com.android.build.gradle.TestedExtension
import org.gradle.api.JavaVersion
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure(KotlinJvmOptions::class.java) {
        jvmTarget = "1.8"
    }
}
