package org.astroman.base.gradle.usage.api.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import org.astroman.base.gradle.usage.api.exception.InvalidCountException;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.selector.CountSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CountValidationRuleTest {

  private static final int SAMPLE_COUNT = 5;
  private static final int INVALID_COUNT = -1;
  private static final SearchParams SAMPLE_SEARCH_PARAMS = SearchParams.builder()
      .count(SAMPLE_COUNT)
      .build();
  private static final SearchParams INVALID_SEARCH_PARAMS = SearchParams.builder()
      .count(INVALID_COUNT)
      .build();
  private static final SearchParams NULL_COUNT_SEARCH_PARAMS = SearchParams.builder().build();

  @InjectMocks
  private CountValidationRule countValidationRule;

  @Mock
  private CountSelector countSelector;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  public void testApplies(boolean isApplicable) {
    when(countSelector.applies(SAMPLE_SEARCH_PARAMS)).thenReturn(isApplicable);
    assertThat(countValidationRule.applies(SAMPLE_SEARCH_PARAMS)).isEqualTo(isApplicable);
  }

  @Test
  public void testValidateSuccess() {
    boolean result = countValidationRule.validate(SAMPLE_SEARCH_PARAMS);
    assertThat(result).isTrue();
  }

  @Test
  public void testValidateInvalid() {
    assertThatExceptionOfType(InvalidCountException.class).isThrownBy(
        () -> countValidationRule.validate(INVALID_SEARCH_PARAMS)
    );
  }

  @Test
  public void testValidateNull() {
    assertThatExceptionOfType(InvalidCountException.class).isThrownBy(
        () -> countValidationRule.validate(NULL_COUNT_SEARCH_PARAMS)
    );
  }

}