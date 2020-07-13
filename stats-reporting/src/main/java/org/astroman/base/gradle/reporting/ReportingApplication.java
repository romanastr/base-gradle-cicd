package org.astroman.base.gradle.reporting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ReportingApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReportingApplication.class, args);
  }

}
