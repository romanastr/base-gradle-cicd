val cacheUrl = "http://" + System.getenv("BUILD_CACHE_HOST") + "/cache/"
val isCiBuild = System.getenv("BUILD") == "CI"
val isPrBuild = System.getenv("BUILD") == "PR"
print("cacheUrl = $cacheUrl\n")
print("CI = $isCiBuild\n")
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
