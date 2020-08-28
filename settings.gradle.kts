val isCiServer = System.getenv().containsKey("CI")
val cacheUrl = System.getenv("BUILD_CACHE_URL")
buildCache {
    remote<HttpBuildCache> {
        url = uri(cacheUrl)
        isPush = isCiServer
    }
}

rootProject.name = "CICD-gradle"
include("data-model")
include("api")
include("stats-api")
include("stats-reporting")
include("perf-tests-api")
include("flyway")
