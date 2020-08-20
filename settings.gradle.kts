buildCache {
    remote<HttpBuildCache> {
        url = uri("http://localhost/cache/")
        isPush = true
    }
}

rootProject.name = "CICD-gradle"
include("data-model")
include("api")
include("stats-api")
include("stats-reporting")
include("perf-tests-api")
include("flyway")
