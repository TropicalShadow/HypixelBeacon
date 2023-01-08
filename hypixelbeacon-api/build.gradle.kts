plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:23.1.0")
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
}


tasks {
    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        destinationDirectory.set(file("$rootDir/build/libs"))
    }
}

//set jvm compatibility to 17
tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
}