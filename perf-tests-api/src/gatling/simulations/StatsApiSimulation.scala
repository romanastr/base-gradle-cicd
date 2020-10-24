import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class StatsApiSimulation extends EnvSimulation {
  private val maxNum = 200
  private val apiHostname = getEnv("STATS_API_HOSTNAME", "localhost")
  private val apiPort = getEnv("STATS_API_PORT", "8081")
  private val totalRepeats = getEnv("STATS_API_REPEATS", "12").toInt
  private val testThreads = getEnv("STATS_API_THREADS", "3").toInt
  private val threshold = getEnv("STATS_API_THRESHOLD", "200").toInt

  private val httpConf = http.baseUrl("http://" + apiHostname + ":" + apiPort)

  def nextNum(): Integer = {
    Random.nextInt(maxNum) + 1
  }

  private val feeder = Iterator.continually(Map("count" -> nextNum()))

  private val scn = scenario("Stats API simulation")
    .repeat(totalRepeats / testThreads) {
      feed(feeder)
        .exec(http("Stats API call")
          .get("/stats")
          .queryParam("count", "${count}")
          .check(status.is(200)))
    }

  setUp(scn.inject(atOnceUsers(testThreads))
    .protocols(httpConf))
    .assertions(
      global.successfulRequests.percent.is(100),
      global.requestsPerSec.gt(threshold)
    )

}
