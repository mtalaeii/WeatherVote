plugins {
    kotlin("jvm") version "2.1.0-Beta1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.github.ben-manes.versions") version "0.39.0" // Versioning plugin
}

group = "me.mahdi"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}
// Define the versions you want to support
val spigotVersions = listOf("1.18.2-R0.1-SNAPSHOT", "1.19.4-R0.1-SNAPSHOT", "1.20.1-R0.1-SNAPSHOT","1.21.1-R0.1-SNAPSHOT")

dependencies {


    // Loop through the versions and add dependencies
    spigotVersions.forEach { version ->
        compileOnly("org.spigotmc:spigot-api:$version")
    }
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
