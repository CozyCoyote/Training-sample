plugins {
    id("android-lib-basic-plugin")
    id("standard-dependencies-plugin")
}

android {
    namespace = "com.cosy.coyote.training.data"
}

applyStandardDependencies {
    add { listOf(APP_COMPAT, COMPOSE, TEST) }
}
