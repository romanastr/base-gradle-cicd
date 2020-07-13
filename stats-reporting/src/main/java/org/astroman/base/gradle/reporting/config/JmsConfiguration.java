package org.astroman.base.gradle.reporting.config;

import org.astroman.base.gradle.reporting.converter.MessageWrapperConverter;
import org.astroman.base.gradle.reporting.listener.JmsMessageListener;
import org.astroman.base.gradle.reporting.service.JdbcReporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@ConditionalOnProperty(value = "reporting.protocol", havingValue = "jms", matchIfMissing = true)
public class JmsConfiguration {

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

  @Bean
  public JmsMessageListener getMessageListener(JdbcReporter jdbcReporter,
      MessageWrapperConverter messageWrapperConverter) {
    return new JmsMessageListener(jdbcReporter, messageWrapperConverter);
  }
}
