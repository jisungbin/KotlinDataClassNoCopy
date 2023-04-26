/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    `java-gradle-plugin`
    id(libs.plugins.maven.publish.get().pluginId)
}

gradlePlugin {
    plugins {
        create("kotlinDataClassNoCopy") {
            id = "land.sungbin.kotlin.dataclass.nocopy.plugin"
            implementationClass = "KotlinDataClassNoCopyPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin.api)
}