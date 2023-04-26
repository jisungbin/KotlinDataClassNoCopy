/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

import org.jetbrains.kotlin.cli.common.messages.MessageUtil
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension

internal class NoCopySyntheticResolveExtension(private val logger: Logger) : SyntheticResolveExtension {
    override fun generateSyntheticMethods(
        thisDescriptor: ClassDescriptor,
        name: Name,
        bindingContext: BindingContext,
        fromSupertypes: List<SimpleFunctionDescriptor>,
        result: MutableCollection<SimpleFunctionDescriptor>,
    ) {
        val hasNoCopy = thisDescriptor.annotations.hasAnnotation(NoCopyFqn)
        val location = MessageUtil.psiElementToMessageLocation(thisDescriptor.findPsi())
        val superResult by lazy {
            super.generateSyntheticMethods(thisDescriptor, name, bindingContext, fromSupertypes, result)
        }

        logger("hasNoCopy: $hasNoCopy".prependLogPrefix())
        if (!hasNoCopy) return superResult

        if (!thisDescriptor.isData) {
            logger.throwError(
                value = Errors.noCopyAppliedWrongTarget(target = thisDescriptor.name.asString()),
                location = location,
            )
        }

        logger("name: ${name.asString()}".prependLogPrefix())
        if (name.asString() != copy) return superResult

        val generatedCopyFunctionIndex = result.findGeneratedCopyFunctionIndex(thisDescriptor) ?: logger.throwError(
            value = Errors.CopyFunctionNotFound,
            location = location,
        )
        logger("generatedCopyFunctionIndex: $generatedCopyFunctionIndex".prependLogPrefix())
        result.remove(result.elementAt(generatedCopyFunctionIndex))
    }
}

private fun Collection<SimpleFunctionDescriptor>.findGeneratedCopyFunctionIndex(classDescriptor: ClassDescriptor): Int? {
    val primaryConstructor = classDescriptor.constructors.firstOrNull { it.isPrimary } ?: return null
    val primaryConstructorParameters = primaryConstructor.valueParameters

    val index = indexOfLast { descriptor ->
        descriptor.name.asString() == copy &&
                descriptor.returnType == classDescriptor.defaultType &&
                descriptor.valueParameters.size == primaryConstructorParameters.size &&
                descriptor.valueParameters.indexedAll { index, parameter ->
                    primaryConstructorParameters[index].type == parameter.type &&
                            primaryConstructorParameters[index].name == parameter.name
                }
    }

    return index.takeIf { it >= 0 }
}