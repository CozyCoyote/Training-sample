package com.sample.internal.lib

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import java.io.OutputStreamWriter

// The visitor pattern is needed to get access to the actual class implementation
class SessionDataWrapperCreator(
    private val impl: KSAnnotated,
    private val codegen: CodeGenerator,
    private val logger: KSPLogger,
) : KSVisitorVoid() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        super.visitClassDeclaration(classDeclaration, data)
        if (classDeclaration.classKind != ClassKind.INTERFACE) {
            logger.error("Classes are not supported as API. ${classDeclaration.simpleName.asString()} needs to be an interface.")
        }
        generateWrapper(classDeclaration)
    }

    private fun generateWrapper(classDeclaration: KSClassDeclaration) {
        val apiName = classDeclaration.simpleName.asString()
        codegen.createNewFile(
            Dependencies(
                false,
                classDeclaration.containingFile!!
            ),
            classDeclaration.packageName.asString(),
            "${apiName}Wrapper"
        ).use { stream ->

            OutputStreamWriter(stream).use { writer ->
                writer.write("package ${classDeclaration.packageName.asString()}\n\n")
                writer.write("import ${impl.containingFile?.packageName?.asString()}.${impl}\n")
                writer.write(parseImports(classDeclaration))
                writer.write("\nimport com.sample.internal.lib.UsingExpiredSessionClass\n")
                writer.write("import com.sample.internal.lib.SessionLifecycleAware\n")
                writer.write("\nclass ${apiName}Wrapper: ${apiName}, SessionLifecycleAware {\n\n")
                writer.write("    private val impl = $impl()\n")
                writer.write("    private var isDirty = false\n\n")

                writer.write("    override fun clear() { isDirty = true }\n\n")

                classDeclaration.getDeclaredFunctions().forEach {
                    overrideFunction(it, apiName, writer)
                }

                writer.write("}\n")
            }

        }
    }

    private fun parseImports(classDeclaration: KSClassDeclaration): String {
        val imports = mutableSetOf<String>()
        classDeclaration.getDeclaredFunctions().forEach {
            it.returnType?.canonicalName()?.let(imports::add)
            it.returnType?.resolve()?.arguments?.mapToNames()?.forEach(imports::add)
            it.parameters.forEach { param ->
                imports.add(param.type.canonicalName()!!)
                param.type.resolve().arguments.mapToNames().forEach(imports::add)
            }
        }
        return imports.joinToString("\n") {
            "import $it"
        }
    }

    private fun overrideFunction(
        it: KSFunctionDeclaration,
        className: String,
        writer: OutputStreamWriter
    ) {
        val arguments = it.parameters.joinToString(", ") { "${it.name?.asString()}" }
        writer.write(functionSignature(it))
        writer.write("        if (isDirty) throw UsingExpiredSessionClass(\"$className used after it has been invalidated\")\n")
        writer.write("        return impl.$it($arguments)\n")
        writer.write("    }\n\n")
    }

    private fun functionSignature(it: KSFunctionDeclaration): String {
        val modifiers = it.modifiers.joinToString(" ").lowercase()
        val params = it.parameters.joinToString(", ") {
            "${it.name?.asString()}: ${it.type.resolve()}"
        }
        val output = it.returnType?.resolve()?.let { output -> ": $output"}
        return "    override $modifiers fun $it($params)$output {\n"
    }

}
