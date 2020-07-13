package org.astroman.base.gradle.reporting.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcReporter {

  private final SimpleJdbcInsert simpleJdbcInsert;

  public void insertCallAudit(Map<String, Object> jdbcRecord) {
    simpleJdbcInsert.execute(jdbcRecord);
  }

}
