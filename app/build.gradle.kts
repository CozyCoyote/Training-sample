plugins {
    id("android-app-basic-plugin")
//    id("java-basic-plugin")
    id("standard-dependencies-plugin")
    id("com.google.devtools.ksp")
//    id("com.cherryperry.gradle-file-encrypt")
//    id("secrets-check-plugin")
}

applyAndroidPlugin.settings {
    namespace = "com.cosy.coyote.training.sample"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
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

applyStandardDependencies.add { listOf(APP_COMPAT, COMPOSE, TEST) }

dependencies {
    implementation(":InternalLibrary")
    implementation(project(":data"))
    ksp(":SessionProcessor")
}
