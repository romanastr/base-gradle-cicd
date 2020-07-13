package org.astroman.base.gradle.usage.api.validator;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static java.util.stream.Collectors.toList;
import static org.astroman.base.gradle.usage.api.model.RequestFields.COUNT;
import static org.astroman.base.gradle.usage.api.model.RequestFields.END_TIME;
import static org.astroman.base.gradle.usage.api.model.RequestFields.START_TIME;
import static org.astroman.base.gradle.usage.api.utils.StringFormatUtils.format;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.astroman.base.gradle.usage.api.exception.InvalidRequestException;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.model.SearchParams.SearchParamsBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestValidator {

  private static final String NOT_MATCHED_ERROR =
      "Provided search parameters do not constitute a valid request";
  private static final String INVALID_NUMERICAL_FORMAT = "Count cannot be parsed {}";
  private static final String TIME_FORMAT_MISMATCHED =
      "Could not parse datetime string {}. Try yyyy-mm-ddTHH:MM:SS.sss-TZ:00 format";

  private final Set<RequestValidationRule> validationRules;

  /**
   * Converts raw {requestParams} map into parsed {searchParams}.
   *
   * @param requestParams - raw single-valued map of request parameters
   * @return - parsed search parameters
   */

  public SearchParams convert(Map<String, String> requestParams) {
    SearchParamsBuilder searchParamsBuilder = SearchParams.builder();
    setIfNotNull(parseInt(COUNT, requestParams), searchParamsBuilder::count);
    setIfNotNull(parseDateTime(START_TIME, requestParams), searchParamsBuilder::startTime);
    setIfNotNull(parseDateTime(END_TIME, requestParams), searchParamsBuilder::endTime);
    return searchParamsBuilder.build();
  }


  /**
   * Validates {searchParams} by validating against a set of validation rules, which match the
   * request. The method either throws an InvalidRequestException or return without an Exception.
   *
   * @param searchParams - parsed search parameters.
   */

  public void validate(SearchParams searchParams) {
    var applicableRules = validationRules.stream()
        .filter(rule -> rule.applies(searchParams))
        .collect(toList());
    if (applicableRules.size() == 0) {
      throw new InvalidRequestException(NOT_MATCHED_ERROR);
    }
    applicableRules.forEach(rule -> rule.validate(searchParams));
  }

  private Integer parseInt(String fieldName, Map<String, String> params) {
    String countString = params.get(fieldName);
    try {
      return countString != null ? Integer.valueOf(countString) : null;
    } catch (NumberFormatException nfe) {
      throw new InvalidRequestException(format(INVALID_NUMERICAL_FORMAT, countString));
    }
  }

  private static <T> void setIfNotNull(T obj, Consumer<T> supplier) {
    if (obj != null) {
      supplier.accept(obj);
    }
  }

  private LocalDateTime parseDateTime(String fieldName, Map<String, String> params) {
    String dateTimeStr = params.get(fieldName);
    try {
      return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, ISO_LOCAL_DATE_TIME) : null;
    } catch (DateTimeParseException dtre) {
      throw new InvalidRequestException(format(TIME_FORMAT_MISMATCHED, dateTimeStr));
    }
  }

}
