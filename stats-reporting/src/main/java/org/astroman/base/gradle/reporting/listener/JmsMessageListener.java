package org.astroman.base.gradle.reporting.listener;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.astroman.base.gradle.model.message.MessageWrapper;
import org.astroman.base.gradle.reporting.converter.MessageWrapperConverter;
import org.astroman.base.gradle.reporting.service.JdbcReporter;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsMessageListener {

  private final JdbcReporter jdbcReporter;
  private final MessageWrapperConverter messageWrapperConverter;

  @JmsListener(destination = "${reporting.queue.name}")
  public void onMessage(MessageWrapper messageWrapper) throws JmsException {
    Map<String, Object> record = messageWrapperConverter.toJdbcRecord(messageWrapper);
    log.info("Parsed message record {}", record);
    jdbcReporter.insertCallAudit(record);
  }
}
