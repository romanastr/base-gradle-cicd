package org.astroman.base.gradle.reporting.config;

import static org.astroman.base.gradle.model.jdbc.RecordFields.AUDIT_RECORDS_TABLE;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Configuration
public class JdbcConfiguration {

  @Bean
  public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public SimpleJdbcInsert getSimpleJdbcInsert(JdbcTemplate jdbcTemplate) {
    return new SimpleJdbcInsert(jdbcTemplate).withTableName(AUDIT_RECORDS_TABLE);
  }

}