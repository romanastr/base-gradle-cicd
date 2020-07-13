package org.astroman.base.gradle.usage.api.selector;

import static org.astroman.base.gradle.model.jdbc.RecordFields.REQUEST_TIME;
import static org.astroman.base.gradle.usage.api.utils.StringFormatUtils.format;

import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.springframework.stereotype.Component;

@Component
public class CountSelector extends Selector {

  @Override
  public boolean applies(SearchParams searchParams) {
    return searchParams.getCount() != null;
  }

  @Override
  public String getSql(SearchParams searchParams) {
    return format(" ORDER BY {} DESC LIMIT {}", REQUEST_TIME, searchParams.getCount());
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE;
  }

}
