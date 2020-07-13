package org.astroman.base.gradle.api.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Map;
import org.astroman.base.gradle.model.message.MessageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

public class JmsMessageReporterTest {

  private static final Map<String, String> SAMPLE_MAP = Map.of("sampleKey", "sampleValue");
  private static final String SAMPLE_QUEUE_NAME = "sampleQueueName";

  private JmsMessageReporter jmsMessageReporter;

  @Mock
  private JmsTemplate jmsTemplate;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    jmsMessageReporter = new JmsMessageReporter(jmsTemplate, SAMPLE_QUEUE_NAME);
  }

  @Test
  public void testReportMessage() {
    jmsMessageReporter.reportMessage(SAMPLE_MAP);
    ArgumentCaptor<MessageWrapper> wrapperCaptor = ArgumentCaptor.forClass(MessageWrapper.class);
    ArgumentCaptor<String> queueCaptor = ArgumentCaptor.forClass(String.class);
    verify(jmsTemplate, times(1)).convertAndSend(queueCaptor.capture(), wrapperCaptor.capture());
    MessageWrapper actualWrapper = wrapperCaptor.getValue();
    String actualQueue = queueCaptor.getValue();
    assertThat(actualQueue).isEqualTo(SAMPLE_QUEUE_NAME);
    assertThat(actualWrapper.getXkcdMessage()).isEqualTo(SAMPLE_MAP);
    assertThat(actualWrapper.getCallTime()).isNotNull();
  }
}