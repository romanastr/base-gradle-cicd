import io.gatling.core.Predef.Simulation

class EnvSimulation extends Simulation {
  protected def getEnv(key: String, defaultValue: String): String = {
    val value = System.getenv(key)
    if(value != null) value else defaultValue
  }
}
