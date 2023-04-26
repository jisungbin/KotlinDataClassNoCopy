/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id(libs.plugins.maven.publish.get().pluginId)
    id(libs.plugins.kotlin.ksp.get().pluginId)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    compileOnly(libs.kotlin.embeddable.compiler)
    implementation(libs.autoservice.annotation)
    ksp(libs.autoservice.processor)

    testImplementation(libs.test.kotest.framework)
    testImplementation(libs.test.strikt)
    testImplementation(libs.test.kotlin.compilation)
}