/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "KotlinDataClassNoCopy"

include(
    ":annotation",
    ":compiler",
    ":gradle-plugin",
    ":sample",
)