allprojects {
    group = "club.tesseract"
    version = "1.0.0"
}

repositories {
    mavenCentral()

    maven("https://jitpack.io")
}


subprojects {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://repo.papermc.io/repository/maven-public/")
        flatDir {
            dirs("$rootDir/libs")
        }
    }
}

