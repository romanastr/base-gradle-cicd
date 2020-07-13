package org.astroman.base.gradle.model.message;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class MessageWrapperTest {

  private static final Map<String, String> SAMPLE_MESSAGE = Map.of(
      "key_1", "10",
      "key_2", "value_2"
  );
  private static final long SAMPLE_CALL_TIME = Instant.now().toEpochMilli();
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testDeserializeMessageWrapper() throws JsonProcessingException {
    MessageWrapper originalObject = MessageWrapper.builder()
        .xkcdMessage(SAMPLE_MESSAGE)
        .callTime(SAMPLE_CALL_TIME)
        .build();

    String serializedString = objectMapper.writeValueAsString(originalObject);
    MessageWrapper cycledObject = objectMapper.readValue(serializedString, MessageWrapper.class);
    assertThat(cycledObject.getXkcdMessage()).isEqualTo(originalObject.getXkcdMessage());
    assertThat(cycledObject.getCallTime()).isEqualTo(originalObject.getCallTime());
  }

}