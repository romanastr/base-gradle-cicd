package org.astroman.base.gradle.usage.api.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AggregateStats {

  private final int totalCount;

  private final int avgNum;

  private final LocalDateTime firstDateTime;

  private final LocalDateTime lastDateTime;

  private final int avgTranscriptLength;
}
