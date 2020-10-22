import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class ApiSimulation extends EnvSimulation {
  private val maxNum = 2330
  private val apiHostname = getEnv("API_HOSTNAME", "localhost")
  private val apiPort = getEnv("API_PORT", "8080")
  private val totalRepeats = getEnv("API_REPEATS", "12").toInt
  private val testThreads = getEnv("API_THREADS", "3").toInt

  private val httpConf = http.baseUrl("http://" + apiHostname + ":" + apiPort)

  def nextNum(): Integer = {
    Random.nextInt(maxNum) + 1
  }

  private val feeder = Iterator.continually(Map("num" -> nextNum()))

  private val scn = scenario("API simulation")
    .repeat(totalRepeats / testThreads) {
      feed(feeder)
        .exec(http("API call")
          .get("/api")
          .queryParam("busywait", true)
          .queryParam("number", "${num}")
          .check(status.is(200)))
    }

  setUp(scn.inject(atOnceUsers(testThreads))
    .protocols(httpConf))
    .assertions(
      global.successfulRequests.percent.is(100),
      global.requestsPerSec.gt(6)
    )

}
