package org.astroman.base.gradle.usage.api.validator;

import org.astroman.base.gradle.usage.api.model.SearchParams;

public interface RequestValidationRule {

  boolean applies(SearchParams searchParams);

  boolean validate(SearchParams searchParams);

}
