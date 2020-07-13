package org.astroman.base.gradle.api.report;

import static java.time.Instant.now;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.astroman.base.gradle.model.message.MessageWrapper;
import org.springframework.jms.core.JmsTemplate;

@Slf4j
@AllArgsConstructor
public class JmsMessageReporter implements MessageReporter {

  private final JmsTemplate jmsTemplate;

  private final String queueName;

  /**
   * Send message to reporting application for logging.
   *
   * @param message - actual message returned by external API.
   */

  public void reportMessage(Map<String, String> message) {
    log.info("Sending message {} to queue {}", message, queueName);
    jmsTemplate.convertAndSend(queueName,
        MessageWrapper.builder()
            .xkcdMessage(message)
            .callTime(now().toEpochMilli())
            .build()
    );
  }

}
