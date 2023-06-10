package com.sample.internal.lib

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.KSTypeReference
import java.io.OutputStreamWriter

class SessionProcessor(
    private val codegen: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    var singleRunLock = false

    companion object {
        const val TRIGGER_ANNOTATION = "com.sample.internal.lib.SessionScoped"
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (singleRunLock) {
            return emptyList()
        }
        singleRunLock = true

        val annotatedFiles = resolver.getSymbolsWithAnnotation(TRIGGER_ANNOTATION)
        logger.warn(annotatedFiles.joinToString(", "))

        if (annotatedFiles.count() == 0) {
            return emptyList()
        }

        val packageName = annotatedFiles.first().containingFile!!.packageName.asString()

        saveFile(
            packageName = packageName,
            fileName = "SessionHolder",
            annotatedFiles = annotatedFiles,
        )

        return emptyList()
    }

    private fun saveFile(
        packageName: String,
        fileName: String,
        annotatedFiles: Sequence<KSAnnotated>
    ) {
        codegen.createNewFile(
            // for incremental processing aggregation NEEDS to be false, and you NEED a list of files to observe.
            // Newly annotated files will still trigger the procession, and it gets the full list of dependencies
            // not only the new additions
            Dependencies(
                false,
                *annotatedFiles.mapNotNull(KSAnnotated::containingFile).toList().toTypedArray()
            ),
            packageName,
            fileName
        ).use { stream ->

            val classAndInterfaces = annotatedFiles.map {
                val objectInterface = (it.annotations.find { annotation ->
                    annotation.shortName.getShortName() == "SessionScoped"
                }?.arguments?.first()?.value as KSType).declaration
                it to objectInterface
            }

            OutputStreamWriter(stream).use { writer ->
                // writer is way faster than string builder / concatenation (~10ms vs ~300ms)
                writer.write("package $packageName\n\n")

                classAndInterfaces.forEach {
                    it.second.accept(SessionDataWrapperCreator(it.first, codegen, logger), Unit)
                    writer.write("import ${(it.second.qualifiedName?.asString())}\n")
                    writer.write("import ${(it.second.qualifiedName?.asString())}Wrapper\n")
                    writer.write("import com.sample.internal.lib.SessionLifecycleAware\n")
                }
                writer.write("import kotlin.reflect.KClass\n")
                writer.write("\nobject $fileName {\n")

                writer.write(
                    """
    val cache = mutableMapOf<String, Any>()

    inline fun <reified T: Any> get(): T {
        val name = T::class.simpleName!!
        if (cache.containsKey(name)) {
            return cache[name] as T
        } else {
            val temp = init<T>(T::class)
            cache[name] = temp
            return temp
        }
    }

    fun clear() {
        cache.values.forEach {
            (it as SessionLifecycleAware).clear()
        }
        cache.clear()
    }

    inline fun <reified T : Any> init(name: KClass<T>): T {
        return when (name) {
"""
                )
                classAndInterfaces.forEach {
                    writer.write("            ${it.second}::class -> ${it.second}Wrapper() as T\n")
                }
                writer.write("            else -> TODO(\"\$name is not part of the session\")\n")
                writer.write("        }\n")
                writer.write("    }\n")
                writer.write("}\n")
            }
        }
    }
}

fun KSTypeReference?.canonicalName(): String? {
    return this?.resolve()?.declaration?.qualifiedName?.asString()
}

fun List<KSTypeArgument>.mapToNames(): List<String> {
    return mapNotNull { it.type.canonicalName() }
}
