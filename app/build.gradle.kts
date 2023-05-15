import com.basic.android.SecretsCheckPlugin.Companion.SECRET_FILES_ARGUMENT
import com.basic.android.SecretsCheckPlugin.Companion.SECRET_PASSWORD_ARGUMENT
import com.basic.android.readListProperty

plugins {
    id("android-app-basic-plugin")
    id("java-basic-plugin")
    id("standard-dependencies-plugin")
//    id("com.cherryperry.gradle-file-encrypt")
//    id("secrets-check-plugin")
}

android {
    namespace = "com.cosy.coyote.training.sample"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// encryption does not work at this time
//gradleFileEncrypt {
//    val files = readListProperty(SECRET_FILES_ARGUMENT).map { "$it.properties" }
//    println(files)
//    plainFiles.from(*files.toTypedArray())
//    passwordProvider.set {
//        return@set (findProperty(SECRET_PASSWORD_ARGUMENT) as String).toCharArray()
//    }
//}
//
//tasks.findByPath("encryptFiles")?.setFinalizedBy(listOf("createSecretHashFiles"))

applyStandardDependencies {
    add { listOf(APP_COMPAT, COMPOSE, TEST) }
}
