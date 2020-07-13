package org.astroman.base.gradle.usage.api.validator;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.astroman.base.gradle.usage.api.exception.InvalidDateRangeException;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.selector.DateRangeSelector;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DateRangeValidationRule implements RequestValidationRule {

  private final DateRangeSelector dateRangeSelectorRule;

  @Override
  public boolean applies(SearchParams searchParams) {
    return dateRangeSelectorRule.applies(searchParams);
  }

  @Override
  public boolean validate(SearchParams searchParams) {
    LocalDateTime startTime = searchParams.getStartTime();
    LocalDateTime endTime = searchParams.getEndTime();
    if (startTime != null
        && endTime != null
        && startTime.isBefore(endTime)
        && startTime.isBefore(LocalDateTime.now())) {
      return true;
    }
    throw new InvalidDateRangeException(startTime, endTime);
  }
}
