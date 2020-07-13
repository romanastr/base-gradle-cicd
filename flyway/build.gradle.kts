import org.flywaydb.gradle.FlywayExtension

apply(plugin = "org.flywaydb.flyway")

configure<FlywayExtension> {
    dependencies {
        implementation("org.flywaydb", "flyway-core", "6.5.0")
        implementation("org.mariadb.jdbc", "mariadb-java-client", "2.6.1")
    }
    url = "jdbc:mariadb://localhost:3306/xkcd"
    driver = "org.mariadb.jdbc.Driver"
    user = "root"
    password = "password"
}