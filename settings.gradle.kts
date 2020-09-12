val cacheUrl = "http://" + System.getenv("BUILD_CACHE_HOST") + "/cache/"
val isCiBuild = System.getenv("CI") == "true"
val isPrBuild = System.getenv("PR") == "true"
print("cacheUrl = " + cacheUrl + "\n")
print("CI = " + isCiBuild + "\n")
buildCache {
    remote<HttpBuildCache> {
        url = uri(cacheUrl)
        isPush = isCiBuild || isPrBuild
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
