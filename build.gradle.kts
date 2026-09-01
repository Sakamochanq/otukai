plugins {
    kotlin("jvm") version "2.4.10"
}

group = "jp.example"
version = "0.1.0"

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)

    compilerOptions {
        jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
        )
    }
}

tasks {
    jar {
        archiveBaseName.set("otukai")
    }
}