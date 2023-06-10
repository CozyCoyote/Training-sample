plugins {
    id("android-lib-basic-plugin")
    id("standard-dependencies-plugin")
    id("com.google.devtools.ksp")
}

applyAndroidPlugin.settings {
    namespace = "com.cosy.coyote.training.data"
}

applyStandardDependencies.add {
    listOf(APP_COMPAT, COMPOSE, TEST)
}

dependencies {
    implementation(":InternalLibrary")
//    ksp(":SessionProcessor")
}
