import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

plugins {
    kotlin("jvm")
    `kotlin-dsl`
    `maven-publish`
    id("com.vanniktech.maven.publish")
}

gradlePlugin {
    plugins {
        create("kotlinDataClassNoCopy") {
            id = "land.sungbin.kotlin.dataclass.nocopy.plugin"
            implementationClass = "KotlinDataClassNoCopyPlugin"
        }
    }
}

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin-api:1.8.20")
}