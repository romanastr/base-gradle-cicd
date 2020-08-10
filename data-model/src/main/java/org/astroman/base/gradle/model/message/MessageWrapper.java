package org.astroman.base.gradle.model.message;

import java.util.Map;

@Builder
@Getter
public class MessageWrapper {

  private final Map<String, String> xkcdMessage;
  private final Long callTime;

}
