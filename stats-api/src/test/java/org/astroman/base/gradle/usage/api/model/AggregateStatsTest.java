package org.astroman.base.gradle.usage.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AggregateStatsTest {

  @Test
  public void testToString() {
    String str = AggregateStats.builder().toString();
    assertThat(str).contains(AggregateStats.class.getSimpleName());
    assertThat(str).contains("null");
  }
}