package com.basic.android

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileNotFoundException
import java.math.BigInteger
import java.security.MessageDigest

@Suppress("Unused")
class SecretsCheckPlugin : Plugin<Project> {

    companion object {
        const val SECRET_FILES_ARGUMENT = "secretFiles"
        const val SECRET_PASSWORD_ARGUMENT = "secretPass"
    }

    override fun apply(target: Project) {
        target.readListProperty(SECRET_FILES_ARGUMENT).forEach {
            val md5 = File("$it.properties").readBytes().toMd5()
            try {
                val expectedHash = String(File("$it.hash").readBytes())
                if (expectedHash != md5) {
                    println("File: $it.properties\nmd5 hash: $md5\nexpected hash: $expectedHash")
                    throw SecretsOutOfSync(it)
                }
            } catch (e: FileNotFoundException) {
                println("hash not found for file: $it.hash")
            }
        }

        target.tasks.register("createSecretHashFiles", CreateSecretHashFiles::class.java)
    }
}

open class CreateSecretHashFiles : DefaultTask() {
    @TaskAction
    fun generateHashes() {
        println("generating hashes")
        project.readListProperty(SecretsCheckPlugin.SECRET_FILES_ARGUMENT).forEach {
            val md5 = File("$it.properties").readBytes().toMd5()
            File("$it.hash").writeBytes(md5.toByteArray())
        }
    }
}

class SecretsOutOfSync(fileName: String) : Throwable(
    "Secrets $fileName out of sync\n" +
            "run either ./gradlew encryptFiles to keep your local configuration," +
            " or ./gradlew decryptFiles to unpack the encrypted files"
)

fun Project.readListProperty(name: String) = (this.findProperty(name) as String).split("|")

fun ByteArray.toMd5(): String {
    val md = MessageDigest.getInstance("MD5").digest(this)
    return BigInteger(1, md).toString(16).padStart(32, '0')
}
