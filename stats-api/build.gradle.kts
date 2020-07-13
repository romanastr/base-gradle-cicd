apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":data-model"))
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-jdbc")
    implementation("org.mariadb.jdbc", "mariadb-java-client", "2.6.1")
    testImplementation(project(":flyway"))
}

tasks.test{
        //dependsOn(tasks.migration)
}