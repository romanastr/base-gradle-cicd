import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    `java-library`
    jacoco
    checkstyle
    id("org.springframework.boot") version "2.2.6.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.8.RELEASE" apply false
    id("org.flywaydb.flyway") version "6.5.0" apply false
}

allprojects {
    group = "org.astroman.base.gradle"
    version = "1.0-SNAPSHOT"
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

subprojects {
    val springBootVersion = "2.3.1.RELEASE"
    apply(plugin = "java-library")
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        compileOnly("org.projectlombok", "lombok")
        annotationProcessor("org.projectlombok", "lombok")
        compileOnly("org.immutables", "value", "2.8.8")
        annotationProcessor("org.immutables", "value", "2.8.8")
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
        configFile = rootProject.file("/config/google_checks.xml")
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