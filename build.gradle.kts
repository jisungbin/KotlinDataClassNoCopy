/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.maven.publish) apply false
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    }

    repositories {
        mavenCentral()
    }

    afterEvaluate {
        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        extensions.configure<KotlinProjectExtension> {
            jvmToolchain(11)
        }

        extensions.configure<SourceSetContainer> {
            getByName("main").java.srcDirs("src/main/kotlin/")
            getByName("test").java.srcDirs("src/test/kotlin/")
        }
    }
}