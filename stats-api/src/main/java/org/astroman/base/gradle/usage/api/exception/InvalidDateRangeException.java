package org.astroman.base.gradle.usage.api.exception;

import static org.astroman.base.gradle.usage.api.utils.StringFormatUtils.format;

import java.time.LocalDateTime;

public class InvalidDateRangeException extends InvalidRequestException {

  private static final String INVALID_DATE_RANGE = "Invalid date range from {} to {}";

  public InvalidDateRangeException(LocalDateTime startTime, LocalDateTime endTime) {
    super(format(INVALID_DATE_RANGE, startTime, endTime));
  }

}
