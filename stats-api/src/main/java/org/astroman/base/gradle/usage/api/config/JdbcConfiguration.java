package org.astroman.base.gradle.usage.api.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcConfiguration {

  @Bean
  public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
