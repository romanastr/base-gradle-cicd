apply(plugin = "org.springframework.boot")
dependencies {
    implementation(project(":data-model"))
    implementation("org.springframework.boot","spring-boot-starter-actuator")
    implementation("org.springframework.boot","spring-boot-starter-web")
    implementation("org.springframework.boot","spring-boot-starter-activemq")
}
