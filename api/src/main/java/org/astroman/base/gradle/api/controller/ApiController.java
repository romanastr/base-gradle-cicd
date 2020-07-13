package org.astroman.base.gradle.api.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.astroman.base.gradle.api.service.BusyWaitService;
import org.astroman.base.gradle.api.service.XkcdCaller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {

  private static final String REQUEST_LOG = "Received request for Xkcd with number {}";
  private final XkcdCaller xkcdCaller;
  private final BusyWaitService busyWaitService;

  @GetMapping("/api")
  public Map<String, String> getXkcd(
      @RequestParam(name = "number", defaultValue = "0") int number,
      @RequestParam(name = "busywait", defaultValue = "false") boolean isBusyWait) {
    log.info(REQUEST_LOG, number);
    if (isBusyWait) {
      busyWaitService.busyWait();
    }
    return xkcdCaller.getResponse(number);
  }

}
