package org.astroman.base.gradle.usage.api.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.astroman.base.gradle.usage.api.exception.InvalidDateRangeException;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.selector.DateRangeSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DateRangeValidationRuleTest {

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
  private static final SearchParams INVERSE_ORDER_SEARCH_PARAMS = SearchParams.builder()
      .startTime(SAMPLE_START_TIME.plusDays(1))
      .endTime(SAMPLE_START_TIME)
      .build();

  private static final SearchParams FUTURE_SEARCH_PARAMS = SearchParams.builder()
      .startTime(SAMPLE_END_TIME)
      .endTime(SAMPLE_END_TIME.plusDays(1))
      .build();


  @InjectMocks
  private DateRangeValidationRule dateRangeValidationRule;

  @Mock
  private DateRangeSelector dateRangeSelector;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  public void testApplies(boolean isApplicable) {
    when(dateRangeSelector.applies(VALID_SEARCH_PARAMS)).thenReturn(isApplicable);
    assertThat(dateRangeValidationRule.applies(VALID_SEARCH_PARAMS)).isEqualTo(isApplicable);
  }

  @Test
  public void testValidateSuccess() {
    boolean result = dateRangeValidationRule.validate(VALID_SEARCH_PARAMS);
    assertThat(result).isTrue();
  }

  @Test
  public void testValidateNullStartDate() {
    assertThatExceptionOfType(InvalidDateRangeException.class).isThrownBy(
        () -> dateRangeValidationRule.validate(NULL_START_DATE_SEARCH_PARAMS)
    );
  }

  @Test
  public void testValidateNullEndDate() {
    assertThatExceptionOfType(InvalidDateRangeException.class).isThrownBy(
        () -> dateRangeValidationRule.validate(NULL_END_DATE_SEARCH_PARAMS)
    );
  }

  @Test
  public void testValidateInverserOrder() {
    assertThatExceptionOfType(InvalidDateRangeException.class).isThrownBy(
        () -> dateRangeValidationRule.validate(INVERSE_ORDER_SEARCH_PARAMS)
    );
  }

  @Test
  public void testValidateFutureStartDate() {
    assertThatExceptionOfType(InvalidDateRangeException.class).isThrownBy(
        () -> dateRangeValidationRule.validate(FUTURE_SEARCH_PARAMS)
    );
  }

}