package org.astroman.base.gradle.api.service;

import static java.util.stream.Collectors.toMap;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.astroman.base.gradle.api.model.MapResponse;
import org.astroman.base.gradle.api.report.MessageReporter;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class XkcdCaller {

  private static final MapResponse RESPONSE_TYPE = new MapResponse();
  public static final String URL_FORMAT = "https://xkcd.com/%d/info.0.json";
  public static final Set<Number> EXCEPTIONAL_IDS = Set.of(404);
  public static final Map<String, String> EXCEPTIONAL_RESPONSE = Map.of(
      "title", "STORY BY THIS ID IS MISSING ON XKCD WEBSITE"
  );

  private final RestTemplate restTemplate;
  private final MessageReporter messageReporter;

  /**
   * Get XKCD comics metadata as a key-value Map.
   *
   * @param num - consecutive number of XKCD comics
   * @return key-value map metadata for XKCD comics.
   */

  public Map<String, String> getResponse(int num) {
    Map<String, String> flatMessage;
    if (EXCEPTIONAL_IDS.contains(num)) {
      flatMessage = EXCEPTIONAL_RESPONSE;
    } else {
      flatMessage = getXckdResponse(num);
      messageReporter.reportMessage(flatMessage);
    }
    return flatMessage;
  }

  private Map<String, String> getXckdResponse(int num) {
    String url = String.format(URL_FORMAT, num);
    RequestEntity<Void> request = RequestEntity.get(URI.create(url))
        .accept(MediaType.APPLICATION_JSON)
        .build();
    Map<String, Object> message = restTemplate.exchange(request, RESPONSE_TYPE).getBody();
    return convertToFlatMap(message);
  }

  private Map<String, String> convertToFlatMap(Map<String, Object> message) {
    return Objects.requireNonNull(message).entrySet().stream()
        .collect(
            toMap(Entry::getKey, e -> e.getValue().toString())
        );
  }

}
