package com.basic.android

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("Unused")
class AndroidLibBasicPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        println("basic android lib plugin logs")

        target.apply {
            plugin("com.android.library")
            plugin("org.jetbrains.kotlin.android")
        }

        target.extensions.configure(LibraryExtension::class.java) {
            this.configure()
        }
    }
}
