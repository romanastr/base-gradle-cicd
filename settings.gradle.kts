val cacheUrl = "http://" + System.getenv("BUILD_CACHE_HOST") + "/cache/"
val isCiServer = System.getenv("CI") == "true"
print("cacheUrl = " + cacheUrl + "\n")
print("CI = " + isCiServer + "\n")
buildCache {
    remote<HttpBuildCache> {
        url = uri(cacheUrl)
        isPush = isCiServer
        isAllowInsecureProtocol = true
    }
}

rootProject.name = "CICD-gradle"
include("data-model")
include("api")
include("stats-api")
include("stats-reporting")
include("perf-tests-api")
include("flyway")
