/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME as kotlinCompilerPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<KotlinCompile> {
    val compilerPluginId = "land.sungbin.kotlin.dataclass.nocopy.compiler"
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:$compilerPluginId:enabled=true",
        )
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:$compilerPluginId:verbose=true",
        )
    }
}

dependencies {
    implementation(projects.annotation)
    kotlinCompilerPlugin(projects.compiler)
}
