package org.astroman.base.gradle.reporting.listener;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.astroman.base.gradle.model.message.MessageWrapper;
import org.astroman.base.gradle.reporting.converter.MessageWrapperConverter;
import org.astroman.base.gradle.reporting.service.JdbcReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JmsMessageListenerTest {

  private static final Map<String, String> SAMPLE_XKCD_MAP = Map.of("sampleKey", "10");
  private static final Map<String, Object> SAMPLE_JDBC_MAP = Map.of("sampleKey", 10);
  private static final MessageWrapper MESSAGE_WRAPPER = MessageWrapper.builder()
      .xkcdMessage(SAMPLE_XKCD_MAP)
      .callTime(now().toEpochMilli())
      .build();

  @InjectMocks
  private JmsMessageListener jmsMessageListener;

  @Mock
  private JdbcReporter jdbcReporter;

  @Mock
  private MessageWrapperConverter messageWrapperConverter;

  @Captor
  private ArgumentCaptor<Map<String, Object>> callAuditCaptor;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(messageWrapperConverter.toJdbcRecord(MESSAGE_WRAPPER)).thenReturn(SAMPLE_JDBC_MAP);
  }

  @Test
  public void testOnMessage() {
    jmsMessageListener.onMessage(MESSAGE_WRAPPER);
    verify(jdbcReporter, times(1)).insertCallAudit(callAuditCaptor.capture());
    assertThat(callAuditCaptor.getValue()).isEqualTo(SAMPLE_JDBC_MAP);
  }
}