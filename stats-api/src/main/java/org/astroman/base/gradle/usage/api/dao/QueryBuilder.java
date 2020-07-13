package org.astroman.base.gradle.usage.api.dao;

import static org.astroman.base.gradle.model.jdbc.RecordFields.AUDIT_RECORDS_TABLE;
import static org.astroman.base.gradle.model.jdbc.RecordFields.LENGTH;
import static org.astroman.base.gradle.model.jdbc.RecordFields.NUM;
import static org.astroman.base.gradle.model.jdbc.RecordFields.REQUEST_TIME;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.AVG_LENGTH;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.AVG_NUM;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.COUNT_NUM;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.MAX_DATE;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.MIN_DATE;
import static org.astroman.base.gradle.usage.api.utils.StringFormatUtils.format;

import java.util.SortedSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.selector.Selector;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryBuilder {

  private final SortedSet<Selector> selectors;

  public String getSql(SearchParams searchParams) {
    return getAggregateSql() + getTableSql(searchParams);
  }

  private String getAggregateSql() {
    return format("SELECT COUNT({}) as {}, ", NUM, COUNT_NUM)
        + format(" AVG({}) as {}, ", NUM, AVG_NUM)
        + format(" MIN({}) as {}, ", REQUEST_TIME, MIN_DATE)
        + format(" MAX({}) as {}, ", REQUEST_TIME, MAX_DATE)
        + format(" AVG({}) as {} ", LENGTH, AVG_LENGTH);
  }

  private String getSelectorSql(SearchParams searchParams) {
    return selectors.stream()
        .filter(selector -> selector.applies(searchParams))
        .map(selector -> selector.getSql(searchParams))
        .collect(Collectors.joining());
  }

  private String getTableSql(SearchParams searchParams) {
    return format(" from (SELECT * FROM {} {}) as temp",
        AUDIT_RECORDS_TABLE, getSelectorSql(searchParams));
  }
}
