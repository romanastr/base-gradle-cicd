package org.astroman.base.gradle.usage.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.astroman.base.gradle.usage.api.dao.AggregateStatsRowMapper;
import org.astroman.base.gradle.usage.api.dao.QueryBuilder;
import org.astroman.base.gradle.usage.api.model.AggregateStats;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcStatsServiceTest {

  private static final String COUNT_STR = "5";
  private static final String START_TIME_STR = "2000-10-31T01:30:00.000";
  private static final String END_TIME_STR = "2010-11-01T01:30:00.212";
  private static final int SAMPLE_COUNT = Integer.parseInt(COUNT_STR);
  private static final LocalDateTime SAMPLE_START_TIME = LocalDateTime.parse(START_TIME_STR);
  private static final LocalDateTime SAMPLE_END_TIME = LocalDateTime.parse(END_TIME_STR);

  private static final AggregateStats SAMPLE_AGG_STATS = AggregateStats.builder()
      .totalCount(SAMPLE_COUNT)
      .avgNum(10)
      .avgTranscriptLength(120)
      .firstDateTime(SAMPLE_START_TIME)
      .lastDateTime(SAMPLE_END_TIME)
      .build();

  @InjectMocks
  private JdbcStatsService jdbcStatsService;

  @Mock
  private QueryBuilder queryBuilder;

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private SearchParams searchParams;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(queryBuilder.getSql(searchParams)).thenReturn("sampleSql");
    when(jdbcTemplate.queryForObject(any(String.class), any(AggregateStatsRowMapper.class)))
        .thenReturn(SAMPLE_AGG_STATS);
  }

  @Test
  public void getAggregateBySearchParams() {
    AggregateStats actualAggregateStats = jdbcStatsService.getAggregateBySearchParams(searchParams);
    assertThat(actualAggregateStats.getTotalCount()).isEqualTo(SAMPLE_AGG_STATS.getTotalCount());
    assertThat(actualAggregateStats.getAvgNum()).isEqualTo(SAMPLE_AGG_STATS.getAvgNum());
    assertThat(actualAggregateStats.getAvgTranscriptLength())
        .isEqualTo(SAMPLE_AGG_STATS.getAvgTranscriptLength());
    assertThat(actualAggregateStats.getFirstDateTime())
        .isEqualTo(SAMPLE_AGG_STATS.getFirstDateTime());
    assertThat(actualAggregateStats.getLastDateTime())
        .isEqualTo(SAMPLE_AGG_STATS.getLastDateTime());
  }
}