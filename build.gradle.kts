plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.10" // only if using Kotlin
    kotlin("plugin.spring") version "1.8.10"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30") // or latest
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.h2database:h2") // Optional: For in-memory DB testing
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // or 17 instead of 21/22
    }
}

