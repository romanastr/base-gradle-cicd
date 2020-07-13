package org.astroman.base.gradle.usage.api.utils;

import org.slf4j.helpers.MessageFormatter;

public final class StringFormatUtils {

  private StringFormatUtils() {
  }

  public static String format(String pattern, Object... params) {
    return MessageFormatter.arrayFormat(pattern, params).getMessage();
  }

}
