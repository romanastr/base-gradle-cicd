package org.astroman.base.gradle.usage.api.selector;

import static org.astroman.base.gradle.model.jdbc.RecordFields.REQUEST_TIME;
import static org.astroman.base.gradle.usage.api.utils.StringFormatUtils.format;

import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.springframework.stereotype.Component;

@Component
public class DateRangeSelector extends Selector {

  @Override
  public boolean applies(SearchParams searchParams) {
    return searchParams.getStartTime() != null
        && searchParams.getEndTime() != null;
  }

  @Override
  public String getSql(SearchParams searchParams) {
    return format(" WHERE {} BETWEEN '{}' AND '{}' ",
        REQUEST_TIME, searchParams.getStartTime(), searchParams.getEndTime());
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
