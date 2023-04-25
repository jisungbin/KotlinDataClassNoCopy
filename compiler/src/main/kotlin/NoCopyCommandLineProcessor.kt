/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

internal const val PluginId = "land.sungbin.kotlin.dataclass.nocopy"

internal val KEY_ENABLED = CompilerConfigurationKey<Boolean>(
    "Whether to disable copy() generation - default is true",
)
internal val OPTION_ENABLED = CliOption(
    optionName = "enabled",
    valueDescription = "<true | false>",
    description = KEY_ENABLED.toString(),
    required = false,
    allowMultipleOccurrences = false,
)

internal val KEY_VERBOSE = CompilerConfigurationKey<Boolean>(
    "Whether to enabled verbose logging - default is false",
)
internal val OPTION_VERBOSE = CliOption(
    optionName = "verbose",
    valueDescription = "<true | false>",
    description = KEY_VERBOSE.toString(),
    required = false,
    allowMultipleOccurrences = false,
)

@AutoService(CommandLineProcessor::class)
class NoCopyCommandLineProcessor : CommandLineProcessor {
    override val pluginId = PluginId

    override val pluginOptions = listOf(OPTION_ENABLED)

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration,
    ) {
        when (val optionName = option.optionName) {
            OPTION_ENABLED.optionName -> configuration.put(KEY_ENABLED, value.toBooleanStrict())
            OPTION_VERBOSE.optionName -> configuration.put(KEY_VERBOSE, value.toBooleanStrict())
            else -> error("Unknown plugin option: $optionName")
        }
    }
}
