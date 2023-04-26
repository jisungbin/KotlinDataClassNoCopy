/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)
@file:Suppress("UnusedDataClassCopyResult")

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.spec.tempdir
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class NoCopyTest : StringSpec() {
    private val temporaryFolder = tempdir()

    init {
        "@NoCopy는 오직 data class에서만 사용할 수 있음" {
            val result = compile(
                source = """
import land.sungbin.kotlin.dataclass.nocopy.NoCopy

@NoCopy
class Error
                         """,
            )

            expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.INTERNAL_ERROR)
            expectThat(result.messages).contains(Errors.noCopyAppliedWrongTarget(target = "Error"))
        }

        "@NoCopy가 없는 data class는 정상적으로 copy를 사용할 수 있음" {
            val result = compile(
                source = """
data class TestClass(val value: Any) {
    init {
        copy(value = "SUCCESS")
    }
}
                        """,
            )

            expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
        }

        "@NoCopy가 있는 data class는 copy 함수가 생성되지 않음" {
            val result = compile(
                source = """
import land.sungbin.kotlin.dataclass.nocopy.NoCopy

@NoCopy
data class TestClass(val value: Any) {
    init {
        copy(value = "ERROR")
    }
}
                         """,
            )

            expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.COMPILATION_ERROR)
            expectThat(result.messages).contains("Cannot access 'copy': it is internal")
        }

        "enabled가 false라면 @NoCopy가 작동하지 않음" {
            val result = compile(
                noCopyEnabled = false,
                source = """
import land.sungbin.kotlin.dataclass.nocopy.NoCopy

@NoCopy
data class TestClass(val value: Any) {
    init {
        copy(value = "SUCCESS")
    }
}
                         """,
            )

            expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
        }
    }

    private fun prepareCompilation(
        noCopyEnabled: Boolean = true,
        vararg sourceFiles: SourceFile,
    ): KotlinCompilation {
        return KotlinCompilation().apply {
            workingDir = temporaryFolder
            sources = sourceFiles.asList() + material
            jvmTarget = JvmTarget.JVM_11.toString()
            supportsK2 = false
            pluginOptions = listOf(
                PluginOption(
                    pluginId = CompilerPluginId,
                    optionName = OPTION_ENABLED.optionName,
                    optionValue = noCopyEnabled.toString(),
                ),
                PluginOption(
                    pluginId = CompilerPluginId,
                    optionName = OPTION_VERBOSE.optionName,
                    optionValue = "true",
                ),
            )
            verbose = false
            inheritClassPath = true
            compilerPluginRegistrars = listOf(NoCopyCompilerPluginRegistrar())
            commandLineProcessors = listOf(NoCopyCommandLineProcessor())
            useK2 = false
        }
    }

    private fun compile(
        noCopyEnabled: Boolean = true,
        @Language("kotlin") source: String,
    ): KotlinCompilation.Result {
        return prepareCompilation(
            noCopyEnabled = noCopyEnabled,
            kotlin("test.kt", source),
        ).compile()
    }
}

private val material = kotlin(
    "NoCopy.kt",
    """
package land.sungbin.kotlin.dataclass.nocopy

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class NoCopy
    """,
)