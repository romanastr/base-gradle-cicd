package org.astroman.base.gradle.api.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.astroman.base.gradle.api.report.JmsMessageReporter;
import org.astroman.base.gradle.api.report.MessageReporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
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
  public ActiveMQConnectionFactory getActiveMqConnectionFactory(
      @Value("${spring.activemq.user}") String userName,
      @Value("${spring.activemq.password}") String password,
      @Value("${spring.activemq.broker-url}") String brokerUrl
  ) {
    return new ActiveMQConnectionFactory(userName, password, brokerUrl);
  }


  @Bean
  public JmsTemplate getJmsTemplate(ActiveMQConnectionFactory connectionFactory,
      MessageConverter messageConverter) {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setMessageConverter(messageConverter);
    return jmsTemplate;
  }

  @Bean
  public MessageReporter getMessageReporter(JmsTemplate jmsTemplate,
      @Value("${reporting.queue.name}") String queueName) {
    return new JmsMessageReporter(jmsTemplate, queueName);
  }
}
