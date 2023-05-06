plugins {
    id("android-app-basic-plugin")
    id("java-basic-plugin")
    id("standard-dependencies-plugin")
}

android {
    namespace = "com.cosy.coyote.training.sample"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

applyStandardDependencies {
    add { listOf(APP_COMPAT, COMPOSE, TEST) }
}
