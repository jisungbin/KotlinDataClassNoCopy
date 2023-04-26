/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

internal object Errors {
    fun noCopyAppliedWrongTarget(target: String): String {
        return "NoCopy is only supported for data classes. ($target)"
    }
    const val CopyFunctionNotFound = "The copy function was not found."
}