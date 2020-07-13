package org.astroman.base.gradle.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusyWaitService {

  @Value("${busy.wait.millis:1000}")
  private final int busyWaitPeriodMillis;

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
