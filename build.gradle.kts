plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.serialization") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.5.32")//for logging
    implementation("io.ktor:ktor-server-core:3.0.0")
    implementation("io.ktor:ktor-server-netty:3.5.0")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")
    implementation("io.ktor:ktor-server-status-pages:3.0.0")

    implementation("org.postgresql:postgresql:42.7.3")

    implementation("org.jetbrains.exposed:exposed-core:0.53.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.53.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.53.0")

    implementation("io.github.jan-tennert.supabase:supabase-kt:3.5.0")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(19)
}

tasks.test {
    useJUnitPlatform()
}