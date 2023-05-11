package com.basic.android

import org.gradle.api.Project

object Deps {
    const val APP_COMPAT = "app_compat"
    const val COMPOSE = "compose"
    const val TEST = "test"
}

private const val compose_version = "1.4.3"

fun Project.applyDependencies(deps: List<String>) = with(dependencies) {
    deps.forEach {
        when (it) {
            Deps.APP_COMPAT -> {
                add("implementation", "androidx.core:core-ktx:1.10.0")
                add("implementation", "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
            }
            Deps.COMPOSE -> {
                add("implementation", "androidx.activity:activity-compose:1.7.1")
                add("implementation", "androidx.lifecycle:lifecycle-runtime-compose:1.7.1")
                add("implementation", "androidx.compose.ui:ui:$compose_version")
                add("implementation", "androidx.compose.ui:ui-tooling-preview:$compose_version")
                add("implementation", "androidx.compose.material3:material3:1.1.0-rc01")
                add("implementation", "androidx.navigation:navigation-compose:2.5.3")
                add("debugImplementation", "androidx.compose.ui:ui-tooling:$compose_version")
                add("debugImplementation", "androidx.compose.ui:ui-test-manifest:$compose_version")
            }
            Deps.TEST -> {
                add("testImplementation", "junit:junit:4.13.2")
                add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
                add("androidTestImplementation", "androidx.test.ext:junit:1.1.3")
                add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.4.0")
                add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4:$compose_version")
            }
        }
    }
}
