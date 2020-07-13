package org.astroman.base.gradle.usage.api.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.AVG_LENGTH;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.AVG_NUM;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.COUNT_NUM;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.MAX_DATE;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.MIN_DATE;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.astroman.base.gradle.usage.api.model.AggregateStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AggregateStatsRowMapperTest {

  private static final int SAMPLE_COUNT = 5;
  private static final int SAMPLE_AVG_NUM = 100;
  private static final int SAMPLE_AVG_LEN = 600;
  private static final String START_TIME_STR = "2000-10-31T01:30:00.000";
  private static final String END_TIME_STR = "2010-11-01T01:30:00.212";
  private static final LocalDateTime SAMPLE_START_TIME = LocalDateTime.parse(START_TIME_STR);
  private static final LocalDateTime SAMPLE_END_TIME = LocalDateTime.parse(END_TIME_STR);

  @InjectMocks
  private AggregateStatsRowMapper aggregateStatsRowMapper;

  @Mock
  private ResultSet resultSet;

  @BeforeEach
  public void init() throws SQLException {
    MockitoAnnotations.initMocks(this);
    when(resultSet.getInt(COUNT_NUM)).thenReturn(SAMPLE_COUNT);
    when(resultSet.getInt(AVG_NUM)).thenReturn(SAMPLE_AVG_NUM);
    when(resultSet.getInt(AVG_LENGTH)).thenReturn(SAMPLE_AVG_LEN);
    when(resultSet.getTimestamp(MIN_DATE)).thenReturn(Timestamp.valueOf(SAMPLE_START_TIME));
    when(resultSet.getTimestamp(MAX_DATE)).thenReturn(Timestamp.valueOf(SAMPLE_END_TIME));
  }

  @Test
  public void testMapRow() throws SQLException {
    AggregateStats aggregateStats = aggregateStatsRowMapper.mapRow(resultSet, 1);
    assertThat(aggregateStats).isNotNull();
    assertThat(aggregateStats.getTotalCount()).isEqualTo(SAMPLE_COUNT);
    assertThat(aggregateStats.getAvgNum()).isEqualTo(SAMPLE_AVG_NUM);
    assertThat(aggregateStats.getAvgTranscriptLength()).isEqualTo(SAMPLE_AVG_LEN);
    assertThat(aggregateStats.getFirstDateTime()).isEqualTo(SAMPLE_START_TIME);
    assertThat(aggregateStats.getLastDateTime()).isEqualTo(SAMPLE_END_TIME);
  }
}