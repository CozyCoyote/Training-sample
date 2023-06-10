package com.basic.android

import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaBasicPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        println("basic plugin logs")
    }
}

object JavaDependencies {
    const val Junit = "junit dependenciey, tbd"
}
