package org.astroman.base.gradle.reporting.converter;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;
import org.astroman.base.gradle.model.jdbc.RecordFields;
import org.astroman.base.gradle.model.message.MessageWrapper;
import org.astroman.base.gradle.reporting.model.XkcdFields;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageWrapperConverterTest {

  private static final String SAMPLE_NUM = "11313";
  private static final String SAMPLE_TRANSCRIPT = "Go hang a salami, I'm a lasagna hog";
  private static final String SAMPLE_YEAR = "2017";
  private static final String SAMPLE_MONTH = "02";
  private static final String SAMPLE_DAY = "05";
  private static final Date SAMPLE_DATE = Date
      .valueOf(SAMPLE_YEAR + "-" + SAMPLE_MONTH + "-" + SAMPLE_DAY);

  private static final Map<String, String> SAMPLE_MESSAGE = Map.of(
      XkcdFields.NUM, SAMPLE_NUM,
      XkcdFields.TRANSCRIPT, SAMPLE_TRANSCRIPT,
      XkcdFields.YEAR, SAMPLE_YEAR,
      XkcdFields.MONTH, SAMPLE_MONTH,
      XkcdFields.DAY, SAMPLE_DAY
  );
  private static final Long SAMPLE_TIME = now().toEpochMilli();
  private static final MessageWrapper MESSAGE_WRAPPER = MessageWrapper.builder()
      .xkcdMessage(SAMPLE_MESSAGE)
      .callTime(SAMPLE_TIME)
      .build();

  private MessageWrapperConverter messageWrapperConverter;


  @BeforeEach
  public void init() {
    messageWrapperConverter = new MessageWrapperConverter();
  }

  @Test
  public void testToJdbcRecord() {
    Map<String, Object> jdbcRecord = messageWrapperConverter.toJdbcRecord(MESSAGE_WRAPPER);
    assertThat(jdbcRecord.get(RecordFields.NUM)).isEqualTo(Integer.parseInt(SAMPLE_NUM));
    assertThat(jdbcRecord.get(RecordFields.PUBLISH_DATE)).isEqualTo(SAMPLE_DATE);
    assertThat(jdbcRecord.get(RecordFields.REQUEST_TIME)).isEqualTo(new Timestamp(SAMPLE_TIME));
    assertThat(jdbcRecord.get(RecordFields.LENGTH)).isEqualTo(SAMPLE_TRANSCRIPT.length());
  }
}