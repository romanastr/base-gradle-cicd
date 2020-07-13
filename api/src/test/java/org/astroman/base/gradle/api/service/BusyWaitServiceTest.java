package org.astroman.base.gradle.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusyWaitServiceTest {

  private static final int WAIT_MILLIS = 1000;

  private BusyWaitService busyWaitService;

  @BeforeEach
  public void init() {
    busyWaitService = new BusyWaitService(WAIT_MILLIS);
  }

  @Test
  public void testBusyWait() {
    busyWaitService.busyWait();
  }
}