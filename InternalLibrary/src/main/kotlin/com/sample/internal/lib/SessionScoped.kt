package com.sample.internal.lib

import kotlin.reflect.KClass

@Suppress("Unused")
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SessionScoped<T: Any>(
    val api: KClass<T>
)
