package org.astroman.base.gradle.usage.api.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.astroman.base.gradle.usage.api.model.RequestFields.COUNT;
import static org.astroman.base.gradle.usage.api.model.RequestFields.END_TIME;
import static org.astroman.base.gradle.usage.api.model.RequestFields.START_TIME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import org.astroman.base.gradle.usage.api.exception.InvalidRequestException;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestValidatorTest {

  private static final String COUNT_STR = "5";
  private static final String START_TIME_STR = "2000-10-31T01:30:00.000";
  private static final String END_TIME_STR = "2010-11-01T01:30:00.212";
  private static final String INVALID_COUNT_STR = "5x";
  private static final String INVALID_START_STR = "2000-10-31T1:30:00.000";

  private static final int SAMPLE_COUNT = Integer.parseInt(COUNT_STR);
  private static final LocalDateTime SAMPLE_START_TIME = LocalDateTime.parse(START_TIME_STR);
  private static final LocalDateTime SAMPLE_END_TIME = LocalDateTime.parse(END_TIME_STR);
  private static final Map<String, String> SAMPLE_REQUEST = Map.of(
      COUNT, COUNT_STR,
      START_TIME, START_TIME_STR,
      END_TIME, END_TIME_STR
  );
  private static final Map<String, String> REQUEST_ONLY_COUNT = Map.of(
      COUNT, COUNT_STR
  );

  private static final Map<String, String> REQUEST_ONLY_DATE = Map.of(
      START_TIME, START_TIME_STR,
      END_TIME, END_TIME_STR
  );

  private static final Map<String, String> REQUEST_INVALID_COUNT = Map.of(
      COUNT, INVALID_COUNT_STR
  );
  private static final Map<String, String> REQUEST_INVALID_START_DATE = Map.of(
      START_TIME, INVALID_START_STR,
      END_TIME, END_TIME_STR
  );
  private static final SearchParams SAMPLE_SEARCH_PARAMS = SearchParams.builder()
      .count(SAMPLE_COUNT)
      .startTime(SAMPLE_START_TIME)
      .endTime(SAMPLE_END_TIME)
      .build();

  private RequestValidator requestValidator;
  private RequestValidationRule ruleNotApplicable;
  private RequestValidationRule ruleNotValid;
  private RequestValidationRule ruleGood;

  @BeforeEach
  public void init() {
    ruleNotApplicable = mock(RequestValidationRule.class);
    ruleNotValid = mock(RequestValidationRule.class);
    ruleGood = mock(RequestValidationRule.class);
    when(ruleNotApplicable.applies(any(SearchParams.class))).thenReturn(false);
    when(ruleNotValid.applies(any(SearchParams.class))).thenReturn(true);
    when(ruleGood.applies(any(SearchParams.class))).thenReturn(true);
    when(ruleNotValid.validate(any(SearchParams.class))).thenThrow(new RuntimeException());
    requestValidator = new RequestValidator(Set.of(ruleNotApplicable, ruleNotValid, ruleGood));
  }

  @Test
  public void testConvertAllFieldsSuccess() {
    SearchParams searchParams = requestValidator.convert(SAMPLE_REQUEST);
    assertThat(searchParams.getCount()).isEqualTo(SAMPLE_COUNT);
    assertThat(searchParams.getStartTime()).isEqualTo(SAMPLE_START_TIME);
    assertThat(searchParams.getEndTime()).isEqualTo(SAMPLE_END_TIME);
  }

  @Test
  public void testConvertOnlyCountSuccess() {
    SearchParams searchParams = requestValidator.convert(REQUEST_ONLY_COUNT);
    assertThat(searchParams.getCount()).isEqualTo(SAMPLE_COUNT);
    assertThat(searchParams.getStartTime()).isNull();
    assertThat(searchParams.getEndTime()).isNull();
  }

  @Test
  public void testConvertOnlyDateSuccess() {
    SearchParams searchParams = requestValidator.convert(REQUEST_ONLY_DATE);
    assertThat(searchParams.getCount()).isNull();
    assertThat(searchParams.getStartTime()).isEqualTo(SAMPLE_START_TIME);
    assertThat(searchParams.getEndTime()).isEqualTo(SAMPLE_END_TIME);
  }

  @Test
  public void testConvertInvalidCount() {
    assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(
        () -> requestValidator.convert(REQUEST_INVALID_COUNT)
    );
  }

  @Test
  public void testConvertInvalidDate() {
    assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(
        () -> requestValidator.convert(REQUEST_INVALID_START_DATE)
    );
  }

  @Test
  public void testValidateNoRules() {
    requestValidator = new RequestValidator(Set.of());
    assertValidationFailure(requestValidator);
  }

  @Test
  public void testValidateNoApplicableRules() {
    requestValidator = new RequestValidator(Set.of(ruleNotApplicable));
    assertValidationFailure(requestValidator);
  }

  @Test
  public void testValidateInvalidRule() {
    requestValidator = new RequestValidator(Set.of(ruleNotApplicable, ruleNotValid));
    assertValidationFailure(requestValidator);
  }

  @Test
  public void testValidateSuccess() {
    requestValidator = new RequestValidator(Set.of(ruleNotApplicable, ruleGood));
    requestValidator.validate(SAMPLE_SEARCH_PARAMS);
  }


  private void assertValidationFailure(final RequestValidator requestValidator) {
    assertThatExceptionOfType(RuntimeException.class).isThrownBy(
        () -> requestValidator.validate(SAMPLE_SEARCH_PARAMS)
    );
  }
}