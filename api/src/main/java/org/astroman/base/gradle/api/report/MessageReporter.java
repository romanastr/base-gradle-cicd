package org.astroman.base.gradle.api.report;

import java.util.Map;

public interface MessageReporter {

  public void reportMessage(Map<String, String> message);

}
