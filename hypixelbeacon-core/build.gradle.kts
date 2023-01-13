import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories{
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://ci.frostcast.net/plugin/repository/everything")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.2")
    compileOnly("com.github.Blackixx:BossShopPro:b8ebbfc58c")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.charleskorn.kaml:kaml:0.49.0")

    implementation("org.jetbrains.exposed", "exposed-core", "0.40.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.40.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.40.1")

    implementation(project(":hypixelbeacon-api"))

    implementation("org.xerial:sqlite-jdbc:3.40.0.0")
    implementation("mysql:mysql-connector-java:8.0.30")
}


tasks {
    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        destinationDirectory.set(file("$rootDir/build/libs"))

        relocate("kotlinx", "club.tesseract.hypixelbeacon.relocated.org.jetbrains.kotlinx")
        relocate("com.charleskorn", "club.tesseract.hypixelbeacon.relocated.com.charleskorn")
        exclude("DebugProbesKt.bin")

    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        filter<org.apache.tools.ant.filters.ReplaceTokens>("tokens" to mapOf("version" to project.version))
    }
}


val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()

compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes")
}