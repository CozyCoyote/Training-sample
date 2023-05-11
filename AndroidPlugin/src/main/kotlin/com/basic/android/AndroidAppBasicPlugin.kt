package com.basic.android

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("Unused")
class AndroidAppBasicPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        println("basic android app plugin apply logs")
        target.apply {
            plugin("com.android.application")
            plugin("org.jetbrains.kotlin.android")
        }

        target.extensions.configure(AppExtension::class.java) {
            this.configure()
        }
    }
}
