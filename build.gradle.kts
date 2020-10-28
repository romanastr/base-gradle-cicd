import com.google.cloud.tools.jib.gradle.JibExtension
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import java.io.ByteArrayOutputStream

plugins {
    `java-library`
    jacoco
    checkstyle
    id("org.springframework.boot") version "2.3.4.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.10.RELEASE" apply false
    id("org.flywaydb.flyway") version "6.5.0" apply false
    id("com.google.cloud.tools.jib") version "2.6.0" apply false
}

allprojects {
    group = "org.astroman.base.gradle"
    version = System.getenv("BUILD_TAG") ?: "1.0"
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

subprojects {
    apply(plugin = "java-library")
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        compileOnly("org.projectlombok", "lombok")
        annotationProcessor("org.projectlombok", "lombok")
        testImplementation("org.springframework.boot", "spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    apply(plugin = "io.spring.dependency-management")
    configure<DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    apply(plugin = "checkstyle")
    configure<CheckstyleExtension> {
        isShowViolations = true
        isIgnoreFailures = false
        maxWarnings = 0
        configFile = rootProject.file("config/google_checks.xml")
    }

    tasks.processResources {
        filesMatching("**/version.properties") {
            expand(Pair("BUILD_TAG", project.version))
        }
    }

    apply(plugin = "jacoco")
    tasks.test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed", "standardOut", "standardError")
        }
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }
    tasks.jacocoTestReport {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it) {
                exclude("**/*Application.class", "**/*Configuration.class")
            }
        }))
        reports {
            html.destination = file("${buildDir}/jacoco")
        }
        dependsOn(tasks.test) // tests are required to run before generating the report
        finalizedBy(tasks.jacocoTestCoverageVerification)
    }
    tasks.jacocoTestCoverageVerification {
        violationRules {
            rule {
                element = "CLASS"
                excludes = listOf("*Application", "*Configuration")
                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    minimum = "1.0".toBigDecimal()
                }
                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = "1.0".toBigDecimal()
                }
            }
        }
        dependsOn(tasks.jacocoTestReport)
    }
}


// Configuration for all docker operations

fun getValue(exec: String, params: List<String>): String =
        ByteArrayOutputStream().use { outputStream ->
            project.exec {
                executable = exec
                args = params
                standardOutput = outputStream
            }
            outputStream.toString().trim()
        }

val accountId = System.getProperty("accountId") ?: getValue("aws", listOf("sts", "get-caller-identity", "--query=\"Account\"", "--output", "text"))
val awsRegion = "us-east-1"
println("AWS account ID = $accountId; region = $awsRegion")

tasks.register("docker-login") {
    //login to ECR docker repo
    println("Logging in to ECR docker repo")
    val dockerToken = getValue("aws", listOf("ecr", "get-login-password", "--region", awsRegion))
    getValue("docker", listOf("login", "--username", "AWS", "--password", dockerToken, "${accountId}.dkr.ecr.us-east-1.amazonaws.com"))
}

configure(subprojects.filter { it.name in listOf("api", "flyway", "stats-api", "stats-reporting") }) {

    apply(plugin = "com.google.cloud.tools.jib")
    configure<JibExtension> {

        // only for flyway
        if(project.name == "flyway") {
            from {
                image = "flyway/flyway:6.0.8-alpine"
                //image = "flyway@sha256:d73e9d8d2a8eb9a1ff7967afcdaa491ee1853930ea93befa18a2d8e991d509d1"
            }
            container {
                entrypoint = listOf("/flyway/flyway")
            }

            extraDirectories {
                paths {
                    path {
                        setFrom("build/resources/main/db/migration")
                        into = "/flyway/sql"
                    }
                }
            }
        } else {

        // only java applications
            from {
                //  openjdk:11.0.7-jre-slim
                image = "openjdk@sha256:1fb56466022f61c64b1fb5f15450619626fd4dd4c63d81c29f1142120df374cf"
            }
            container {
                ports = listOf("8080")
            }
        }

        // common part
        to {
            image = "${accountId}.dkr.ecr.us-east-1.amazonaws.com/base-gradle-${project.name}:${project.version}"
        }
    }

    tasks.named("jib") {
        dependsOn(rootProject.tasks.named("docker-login"))
    }
}