package org.astroman.base.gradle.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.astroman.base.gradle.api.model.MapResponse;
import org.astroman.base.gradle.api.report.JmsMessageReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class XkcdCallerTest {

  private static final int SAMPLE_NUM = 200;
  private static final Map<String, Object> SAMPLE_MAP = Map.of("sampleKey", "sampleValue");

  @InjectMocks
  private XkcdCaller xkcdCaller;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private JmsMessageReporter messageReporter;

  @Mock
  private ResponseEntity<Map<String, Object>> responseEntity;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(restTemplate.exchange(any(RequestEntity.class), any(MapResponse.class)))
        .thenReturn(responseEntity);
    when(responseEntity.getBody()).thenReturn(SAMPLE_MAP);
  }

  @Test
  public void testGetResponse() {
    assertThat(xkcdCaller.getResponse(SAMPLE_NUM)).isEqualTo(SAMPLE_MAP);
  }
}