package org.astroman.base.gradle.usage.api.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.astroman.base.gradle.model.jdbc.RecordFields.AUDIT_RECORDS_TABLE;

import org.assertj.core.util.Sets;
import org.astroman.base.gradle.usage.api.model.SearchParams;
import org.astroman.base.gradle.usage.api.selector.Selector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueryBuilderTest {

  private static final String SAMPLE_SQL = "sampleMarkerSql";

  private QueryBuilder queryBuilder;

  private final Selector sampleSelector = new Selector() {
    @Override
    public boolean applies(SearchParams searchParams) {
      return true;
    }

    @Override
    public String getSql(SearchParams searchParams) {
      return SAMPLE_SQL;
    }

    @Override
    public int getOrder() {
      return 0;
    }
  };

  @BeforeEach
  public void init() {
    queryBuilder = new QueryBuilder(Sets.newTreeSet(sampleSelector));
  }

  @Test
  public void getSql() {
    String finalSql = queryBuilder.getSql(SearchParams.builder().build());
    assertThat(finalSql).contains("SELECT");
    assertThat(finalSql).contains(SAMPLE_SQL);
    assertThat(finalSql).contains(AUDIT_RECORDS_TABLE);
  }
}