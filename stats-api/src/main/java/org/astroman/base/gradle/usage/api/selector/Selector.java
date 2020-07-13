package org.astroman.base.gradle.usage.api.selector;

import org.astroman.base.gradle.usage.api.model.SearchParams;

public abstract class Selector implements Comparable<Selector> {

  public abstract boolean applies(SearchParams searchParams);

  public abstract String getSql(SearchParams searchParams);

  public abstract int getOrder();

  @Override
  public int compareTo(Selector that) {
    return Integer.compare(this.getOrder(), that.getOrder());
  }

}
