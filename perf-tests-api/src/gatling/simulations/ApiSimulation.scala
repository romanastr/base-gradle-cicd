import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class ApiSimulation extends Simulation {
  private val maxNum = 2330
  private val apiHostname = System.getProperty("api.hostname", "localhost")
  private val apiPort = System.getProperty("api.port", "8080")
  private val totalRepeats = System.getProperty("api.repeats", "12").toInt
  private val testThreads = System.getProperty("api.threads", "3").toInt

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
    .assertions(forAll.failedRequests.count.is(0))

}
