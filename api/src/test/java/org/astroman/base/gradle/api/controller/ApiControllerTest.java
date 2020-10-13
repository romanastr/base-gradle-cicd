package org.astroman.base.gradle.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.astroman.base.gradle.api.service.BusyWaitService;
import org.astroman.base.gradle.api.service.XkcdCaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ApiControllerTest {

  private static final int SAMPLE_NUM = 100;
  private static final Map<String, String> SAMPLE_MAP = Map.of("sampleKey", "sampleValue");

  @InjectMocks
  private ApiController apiController;

  @Mock
  private XkcdCaller xkcdCaller;

  @Mock
  private BusyWaitService busyWaitService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(xkcdCaller.getAndReportResponse(SAMPLE_NUM)).thenReturn(SAMPLE_MAP);
    doNothing().when(busyWaitService).busyWait();
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  public void testGetXkcd(boolean isBusyWait) {
    assertThat(apiController.getXkcd(SAMPLE_NUM, isBusyWait)).isEqualTo(SAMPLE_MAP);
  }

}