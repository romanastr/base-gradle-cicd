package org.astroman.base.gradle.usage.api.validator;

import lombok.RequiredArgsConstructor;
import org.astroman.base.gradle.usage.api.exception.InvalidCountException;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.selector.CountSelector;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountValidationRule implements RequestValidationRule {

  private final CountSelector countSelectorRule;

  @Override
  public boolean applies(SearchParams searchParams) {
    return countSelectorRule.applies(searchParams);
  }

  @Override
  public boolean validate(SearchParams searchParams) {
    Integer count = searchParams.getCount();
    if (count != null
        && count > 0) {
      return true;
    }
    throw new InvalidCountException(count);
  }
}
