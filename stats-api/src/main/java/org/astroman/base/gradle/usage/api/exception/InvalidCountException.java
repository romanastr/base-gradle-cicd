package org.astroman.base.gradle.usage.api.exception;

import static org.astroman.base.gradle.usage.api.utils.StringFormatUtils.format;

public class InvalidCountException extends InvalidRequestException {

  private static final String INVALID_COUNT_FORMAT = "Invalid count {}";

  public InvalidCountException(Integer num) {
    super(format(INVALID_COUNT_FORMAT, num));
  }

}
