package org.astroman.base.gradle.usage.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SearchParamsTest {

  @Test
  public void testToString() {
    String str = SearchParams.builder().toString();
    assertThat(str).contains(SearchParams.class.getSimpleName());
    assertThat(str).contains("null");
  }
}
