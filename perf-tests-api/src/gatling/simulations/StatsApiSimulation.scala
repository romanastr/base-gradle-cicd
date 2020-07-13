import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class StatsApiSimulation extends Simulation {
  private val maxNum = 200
  private val apiHostname = System.getProperty("stats.api.hostname", "localhost")
  private val apiPort = System.getProperty("stats.api.port", "8081")
  private val totalRepeats = System.getProperty("stats.api.repeats", "12").toInt
  private val testThreads = System.getProperty("stats.api.threads", "3").toInt

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
    .assertions(forAll.failedRequests.count.is(0))

}
