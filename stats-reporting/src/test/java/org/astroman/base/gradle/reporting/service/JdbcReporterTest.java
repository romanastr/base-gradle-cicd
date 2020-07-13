package org.astroman.base.gradle.reporting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcReporterTest {

  private static final Map<String, Object> SAMPLE_JDBC_MAP = Map.of("sampleKey", 10);

  @InjectMocks
  private JdbcReporter jdbcReporter;

  @Mock
  private SimpleJdbcInsert simpleJdbcInsert;

  @Captor
  private ArgumentCaptor<Map<String, Object>> callAuditCaptor;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testInsertCallAudit() {
    jdbcReporter.insertCallAudit(SAMPLE_JDBC_MAP);
    verify(simpleJdbcInsert, times(1)).execute(callAuditCaptor.capture());
    assertThat(callAuditCaptor.getValue()).isEqualTo(SAMPLE_JDBC_MAP);
  }
}