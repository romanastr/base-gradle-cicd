package org.astroman.base.gradle.usage.api.selector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.astroman.base.gradle.model.jdbc.RecordFields.REQUEST_TIME;

import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CountSelectorTest {

  private static final int SAMPLE_COUNT = 5141;
  private static final SearchParams SAMPLE_SEARCH_PARAMS = SearchParams.builder()
      .count(SAMPLE_COUNT)
      .build();
  private static final SearchParams NULL_COUNT_SEARCH_PARAMS = SearchParams.builder().build();

  @InjectMocks
  private CountSelector countSelector;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testAppliesSuccess() {
    boolean applies = countSelector.applies(SAMPLE_SEARCH_PARAMS);
    assertThat(applies).isTrue();
  }

  @Test
  public void testAppliesNullCount() {
    boolean applies = countSelector.applies(NULL_COUNT_SEARCH_PARAMS);
    assertThat(applies).isFalse();
  }

  @Test
  public void testGetSql() {
    String sql = countSelector.getSql(SAMPLE_SEARCH_PARAMS);
    assertThat(sql).contains(REQUEST_TIME);
    assertThat(sql).contains(SAMPLE_SEARCH_PARAMS.getCount().toString());
  }

  @Test
  public void testGetOrder() {
    assertThat(countSelector.getOrder()).isEqualTo(Integer.MAX_VALUE);
  }
}