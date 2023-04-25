import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish")
    id("com.google.devtools.ksp") version "1.8.20-1.0.11"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.20")
    implementation("com.google.auto.service:auto-service-annotations:1.0.1")
    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.0.0")

    testImplementation("io.kotest:kotest-runner-junit5:5.6.1")
    testImplementation("io.strikt:strikt-core:0.34.1")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.5.0")
}