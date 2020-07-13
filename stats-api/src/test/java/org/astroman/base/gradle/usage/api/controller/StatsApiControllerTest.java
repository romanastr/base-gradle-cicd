package org.astroman.base.gradle.usage.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.astroman.base.gradle.usage.api.model.RequestFields.COUNT;
import static org.astroman.base.gradle.usage.api.model.RequestFields.END_TIME;
import static org.astroman.base.gradle.usage.api.model.RequestFields.START_TIME;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Map;
import org.astroman.base.gradle.usage.api.model.AggregateStats;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.service.JdbcStatsService;
import org.astroman.base.gradle.usage.api.validator.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StatsApiControllerTest {

  private static final String COUNT_STR = "5";
  private static final String START_TIME_STR = "2000-10-31T01:30:00.000";
  private static final String END_TIME_STR = "2010-11-01T01:30:00.212";
  private static final int SAMPLE_COUNT = Integer.parseInt(COUNT_STR);
  private static final LocalDateTime SAMPLE_START_TIME = LocalDateTime.parse(START_TIME_STR);
  private static final LocalDateTime SAMPLE_END_TIME = LocalDateTime.parse(END_TIME_STR);
  private static final Map<String, String> SAMPLE_REQUEST = Map.of(
      COUNT, COUNT_STR,
      START_TIME, START_TIME_STR,
      END_TIME, END_TIME_STR
  );
  private static final SearchParams SAMPLE_SEARCH_PARAMS = SearchParams.builder()
      .count(SAMPLE_COUNT)
      .startTime(SAMPLE_START_TIME)
      .endTime(SAMPLE_END_TIME)
      .build();
  private static final AggregateStats SAMPLE_AGG_STATS = AggregateStats.builder()
      .totalCount(SAMPLE_COUNT)
      .avgNum(10)
      .avgTranscriptLength(120)
      .firstDateTime(SAMPLE_START_TIME)
      .lastDateTime(SAMPLE_END_TIME)
      .build();

  @InjectMocks
  private StatsApiController statsApiController;

  @Mock
  private RequestValidator requestValidator;

  @Mock
  private JdbcStatsService jdbcStatsService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(requestValidator.convert(SAMPLE_REQUEST)).thenReturn(SAMPLE_SEARCH_PARAMS);
    when(jdbcStatsService.getAggregateBySearchParams(SAMPLE_SEARCH_PARAMS))
        .thenReturn(SAMPLE_AGG_STATS);
  }

  @Test
  public void testGetStats() {
    AggregateStats actualAggregateStats = statsApiController.getStats(SAMPLE_REQUEST);
    verify(requestValidator, times(1)).validate(SAMPLE_SEARCH_PARAMS);
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