package com.basic.android

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import javax.inject.Inject

@Suppress("Unused")
class AndroidLibBasicPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("applyAndroidPlugin", ApplyAndroidLibraryPlugin::class.java, target)
    }
}

@Suppress("Unused")
open class ApplyAndroidLibraryPlugin @Inject constructor(
    private val target: Project
) {

    init {
        println("basic android lib plugin logs")
        target.apply {
            plugin("com.android.library")
            plugin("org.jetbrains.kotlin.android")
        }
    }

    fun settings(block: LibraryExtension.() -> Unit) {
        target.extensions.configure(LibraryExtension::class.java) {
            this.configure()
            block()
        }
    }

}
