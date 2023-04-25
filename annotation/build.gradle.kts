/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()
}
