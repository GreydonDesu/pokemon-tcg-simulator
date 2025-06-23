plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  id("org.springframework.boot") version "3.4.5"
  id("io.spring.dependency-management") version "1.1.7"
  id("jacoco")
}

group = "de.thro"
version = "0.0.1-SNAPSHOT"

val javaVersion = project.property("javaVersion").toString().toInt()
java { toolchain { languageVersion = JavaLanguageVersion.of(javaVersion) } }

repositories { mavenCentral() }

dependencies {
  // Spring Boot dependencies
  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-logging")

  // Kotlin dependencies
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  // JWT dependencies
  implementation("io.jsonwebtoken:jjwt-api:0.12.6")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

  // Development tools
  developmentOnly("org.springframework.boot:spring-boot-devtools")

  // Test dependencies
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("com.github.stefanbirkner:system-lambda:1.2.1") // Used for JwtUtil tests
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

tasks.withType<Test> {
  useJUnitPlatform {
    excludeTags("integration")
  }
  jvmArgs("--add-opens", "java.base/java.util=ALL-UNNAMED")
  jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}

jacoco {
  toolVersion = "0.8.10" // Use the latest JaCoCo version
}

tasks.jacocoTestReport {
  dependsOn(tasks.test) // Ensure tests run before generating the report

  reports {
    xml.required.set(true) // Enable XML report (required for Codacy)
    html.required.set(true) // Enable HTML report (optional, for local viewing)
    csv.required.set(false) // Disable CSV report (not needed for Codacy)
  }
}

tasks.jacocoTestCoverageVerification {
  violationRules {
    rule {
      limit {
        minimum = "0.8".toBigDecimal() // Require at least 80% coverage
      }
    }
  }
}

tasks.check {
  dependsOn(
      tasks.jacocoTestCoverageVerification) // Fail the build if coverage is below the threshold
}

tasks.register<Test>("integrationTest") {
  description = "Runs the integration tests."
  group = "verification"

  useJUnitPlatform {
    includeTags("integration")
  }

  shouldRunAfter(tasks.test)

  environment("SPRING_DATA_MONGODB_URI", "mongodb://localhost:27017/testdb")
  environment("SPRING_DATA_MONGODB_DATABASE", "testdb")
  environment("JWT_SECRET", "NyQGY9Q9WfRbyuPaLd1ut08WOdFKDpJOasb7PXECMKE=")
}


tasks.named("check") {
  dependsOn("integrationTest")
}

