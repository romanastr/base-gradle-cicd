package org.astroman.base.gradle.reporting.converter;

import static org.astroman.base.gradle.model.jdbc.RecordFields.LENGTH;
import static org.astroman.base.gradle.model.jdbc.RecordFields.NUM;
import static org.astroman.base.gradle.model.jdbc.RecordFields.PUBLISH_DATE;
import static org.astroman.base.gradle.model.jdbc.RecordFields.REQUEST_TIME;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import org.astroman.base.gradle.model.message.MessageWrapper;
import org.astroman.base.gradle.reporting.model.XkcdFields;
import org.springframework.stereotype.Component;

@Component
public class MessageWrapperConverter {

  /**
   * Converts a wrapped message into a Map insertable into the database.
   *
   * @param messageWrapper - wrapped message.
   * @return Map&gt;String, Object&lt;, which can be readily inserted in the database.
   */

  public Map<String, Object> toJdbcRecord(MessageWrapper messageWrapper) {
    Map<String, String> xkcd = messageWrapper.getXkcdMessage();
    Function<String, Integer> getInt = fieldName -> Integer.parseInt(xkcd.get(fieldName));
    return Map.of(
        NUM, getInt.apply(XkcdFields.NUM),
        REQUEST_TIME, new Timestamp(messageWrapper.getCallTime()),
        PUBLISH_DATE, Date.valueOf(
            LocalDate.of(getInt.apply(XkcdFields.YEAR), getInt.apply(XkcdFields.MONTH),
                getInt.apply(XkcdFields.DAY)
            )),
        LENGTH, xkcd.get(XkcdFields.TRANSCRIPT).length()
    );
  }

}
