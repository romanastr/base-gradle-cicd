package org.astroman.base.gradle.usage.api.selector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.astroman.base.gradle.model.jdbc.RecordFields.REQUEST_TIME;

import java.time.LocalDateTime;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class DateRangeSelectorTest {

  private static final String START_TIME_STR = "2000-10-31T01:30:00.000";
  private static final LocalDateTime SAMPLE_START_TIME = LocalDateTime.parse(START_TIME_STR);
  private static final LocalDateTime SAMPLE_END_TIME = LocalDateTime.now().plusDays(1);
  private static final SearchParams VALID_SEARCH_PARAMS = SearchParams.builder()
      .startTime(SAMPLE_START_TIME)
      .endTime(SAMPLE_END_TIME)
      .build();
  private static final SearchParams NULL_END_DATE_SEARCH_PARAMS = SearchParams.builder()
      .startTime(SAMPLE_START_TIME)
      .build();
  private static final SearchParams NULL_START_DATE_SEARCH_PARAMS = SearchParams.builder().build();

  @InjectMocks
  private DateRangeSelector dateRangeSelector;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testAppliesSuccess() {
    boolean applies = dateRangeSelector.applies(VALID_SEARCH_PARAMS);
    assertThat(applies).isTrue();
  }

  @Test
  public void testAppliesNullStartDate() {
    boolean applies = dateRangeSelector.applies(NULL_START_DATE_SEARCH_PARAMS);
    assertThat(applies).isFalse();
  }

  @Test
  public void testAppliesNullEndDate() {
    boolean applies = dateRangeSelector.applies(NULL_END_DATE_SEARCH_PARAMS);
    assertThat(applies).isFalse();
  }

  @Test
  public void testGetSql() {
    String sql = dateRangeSelector.getSql(VALID_SEARCH_PARAMS);
    assertThat(sql).contains(REQUEST_TIME);
    assertThat(sql).contains(VALID_SEARCH_PARAMS.getStartTime().toString());
    assertThat(sql).contains(VALID_SEARCH_PARAMS.getEndTime().toString());
  }

  @Test
  public void testGetOrder() {
    assertThat(dateRangeSelector.getOrder()).isEqualTo(0);
  }
}