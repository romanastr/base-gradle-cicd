package org.astroman.base.gradle.usage.api.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.astroman.base.gradle.usage.api.model.AggregateStats;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.service.JdbcStatsService;
import org.astroman.base.gradle.usage.api.validator.RequestValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/stats")
public class StatsApiController {

  private final RequestValidator requestValidator;
  private final JdbcStatsService jdbcStatsService;

  /**
   * Retrieve aggregate stats for the data between the {startTime} and {endTime} Accepted string
   * format is like "2000-10-31T01:30:00.000".
   *
   * @return aggregate stats
   */

  @GetMapping
  public AggregateStats getStats(@RequestParam Map<String, String> requestParams) {
    log.info("Request for the aggregate stats with parameters {}", requestParams);
    SearchParams searchParams = requestValidator.convert(requestParams);
    requestValidator.validate(searchParams);
    AggregateStats aggregateStats = jdbcStatsService.getAggregateBySearchParams(searchParams);
    log.info("Returned aggregate stats {}", aggregateStats);
    return aggregateStats;
  }

}
