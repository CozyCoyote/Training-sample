package com.basic.android

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import javax.inject.Inject

@Suppress("Unused")
class AndroidAppBasicPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("applyAndroidPlugin", ApplyAndroidPlugin::class.java, target)
    }
}

@Suppress("Unused")
open class ApplyAndroidPlugin @Inject constructor(
    private val target: Project
) {

    init {
        println("basic android app plugin apply logs")
        target.apply {
            plugin("com.android.application")
            plugin("org.jetbrains.kotlin.android")
        }
    }

    fun settings(block: BaseAppModuleExtension.() -> Unit) {
        target.extensions.configure(BaseAppModuleExtension::class.java) {
            this.configure()
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
            block()
        }
    }

}
