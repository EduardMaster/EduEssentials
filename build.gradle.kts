plugins {
    java
    kotlin("jvm") version "1.4.21"
}

group = "net.eduard.plugins"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io/")
}
dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("net.eduard:eduardapi:1.0-SNAPSHOT")
}
java.targetCompatibility = JavaVersion.VERSION_1_8
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks {
    jar{
        destinationDirectory.set(file("E:\\Tudo\\Minecraft - Server\\Servidor Teste\\plugins\\"))
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileJava{
        options.encoding = "UTF-8"
    }
}
