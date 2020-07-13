package org.astroman.base.gradle.model.message;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageWrapper {

  private final Map<String, String> xkcdMessage;
  private final Long callTime;

}
