val cacheUrl = System.getProperty("BUILD_CACHE_URL")
val isCiServer = System.getProperty("CI") == "true"
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
