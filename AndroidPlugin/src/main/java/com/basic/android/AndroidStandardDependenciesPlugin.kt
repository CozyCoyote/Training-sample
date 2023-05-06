package com.basic.android

import org.gradle.api.Plugin
import org.gradle.api.Project
import javax.inject.Inject

@Suppress("Unused")
class AndroidStandardDependenciesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.extensions.create("applyStandardDependencies", ApplyStandardDependencies::class.java, target)
    }
}

open class ApplyStandardDependencies @Inject constructor(
    private val project: Project
) {

    fun add(deps: Deps.() -> List<String>) {
        project.applyDependencies(Deps.deps())
    }

}
