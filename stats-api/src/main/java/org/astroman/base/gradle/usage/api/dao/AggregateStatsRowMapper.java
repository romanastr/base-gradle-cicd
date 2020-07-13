package org.astroman.base.gradle.usage.api.dao;

import static org.astroman.base.gradle.usage.api.model.ResponseFields.AVG_LENGTH;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.AVG_NUM;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.COUNT_NUM;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.MAX_DATE;
import static org.astroman.base.gradle.usage.api.model.ResponseFields.MIN_DATE;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.astroman.base.gradle.usage.api.model.AggregateStats;
import org.springframework.jdbc.core.RowMapper;

public class AggregateStatsRowMapper implements RowMapper<AggregateStats> {

  @Override
  public AggregateStats mapRow(ResultSet rs, int rowNum) throws SQLException {
    return AggregateStats.builder()
        .totalCount(rs.getInt(COUNT_NUM))
        .avgNum(rs.getInt(AVG_NUM))
        .firstDateTime(rs.getTimestamp(MIN_DATE).toLocalDateTime())
        .lastDateTime(rs.getTimestamp(MAX_DATE).toLocalDateTime())
        .avgTranscriptLength(rs.getInt(AVG_LENGTH))
        .build();
  }
}
