package org.astroman.base.gradle.usage.api.service;

import lombok.RequiredArgsConstructor;
import org.astroman.base.gradle.usage.api.dao.AggregateStatsRowMapper;
import org.astroman.base.gradle.usage.api.dao.QueryBuilder;
import org.astroman.base.gradle.usage.api.model.AggregateStats;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcStatsService {

  private final QueryBuilder queryBuilder;
  private final JdbcTemplate jdbcTemplate;

  /**
   * Retrieve {aggregateStats} by creating a query applied to raw data stored in a datastore and
   * executing it.
   *
   * @param searchParams - parsed and validated search parameters
   * @return aggregate stats
   */

  public AggregateStats getAggregateBySearchParams(SearchParams searchParams) {
    String finalSql = queryBuilder.getSql(searchParams);
    return jdbcTemplate.queryForObject(finalSql, new AggregateStatsRowMapper());
  }

}
