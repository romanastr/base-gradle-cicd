package org.astroman.base.gradle.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BusyWaitService {

  private final int busyWaitPeriodMillis;

  public BusyWaitService(@Value("${busy.wait.millis:1000}") int busyWaitPeriodMillis) {
    this.busyWaitPeriodMillis = busyWaitPeriodMillis;
  }

  /**
   * Implements busy wait for a specified duration.
   */

  public void busyWait() {
    long startMillis = System.currentTimeMillis();
    while (busyWaitPeriodMillis + startMillis > System.currentTimeMillis()) {
      for (int i = 1; i < 10000; i++) {
        Math.tanh(Math.random());
      }
    }
  }
}
