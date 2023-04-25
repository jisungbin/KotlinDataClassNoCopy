/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension

@AutoService(CompilerPluginRegistrar::class)
class NoCopyCompilerPluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2 = false

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        if (configuration[KEY_ENABLED] != false) {
            SyntheticResolveExtension.registerExtension(
                extension = NoCopySyntheticResolveExtension(
                    logger = configuration.getLogger(
                        verbose = configuration[KEY_VERBOSE] == true,
                    ),
                ),
            )
        }
    }
}
