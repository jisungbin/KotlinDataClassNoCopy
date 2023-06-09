/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

@file:Suppress("unused")

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

private val Artifacts = object {
    val groupId = "land.sungbin.kotlin.dataclass.nocopy"
    val annotation = object {
        val artifactId = "kotlin-dataclass-nocopy-annotation"
        val version = "1.0.6"
    }
    val compiler = object {
        val artifactId = "kotlin-dataclass-nocopy-compiler"
        val version = "1.0.4"
    }
}
private const val CompilerPluginId = "land.sungbin.kotlin.dataclass.nocopy.compiler"

class KotlinDataClassNoCopyPlugin : KotlinCompilerPluginSupportPlugin {
    override fun apply(target: Project) {
        target.extensions.create("nocopy", KotlinDataClassNoCopyExtension::class.java)
    }

    override fun getCompilerPluginId() = CompilerPluginId

    override fun getPluginArtifact() = SubpluginArtifact(
        groupId = Artifacts.groupId,
        artifactId = Artifacts.compiler.artifactId,
        version = Artifacts.compiler.version
    )

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = true

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        val extension = project.extensions.getByType(KotlinDataClassNoCopyExtension::class.java)

        val enabled = extension.enabled.get()
        val verbose = extension.verbose.get()

        val annotationArtifact = buildString {
            append(Artifacts.groupId)
            append(":")
            append(Artifacts.annotation.artifactId)
            append(":")
            append(Artifacts.annotation.version)
        }
        project.dependencies.add("implementation", annotationArtifact)

        return project.provider {
            listOf(
                SubpluginOption(key = "enabled", value = enabled.toString()),
                SubpluginOption(key = "verbose", value = verbose.toString()),
            )
        }
    }
}