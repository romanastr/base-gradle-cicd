package org.astroman.base.gradle.usage.api.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchParams {

  private final LocalDateTime startTime;

  private final LocalDateTime endTime;

  private final Integer count;

}
