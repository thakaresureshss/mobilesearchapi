package com.mobile.search.utils;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class JsonUtils {
  private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
  @Autowired
  private ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
  }

  public String toJsonString(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T fromJsonStringToMobileError(String errorString, Class<T> clazz) throws IOException {
    return objectMapper.readValue(errorString, clazz);
  }

  public String readMessageFrmJson(final String json) {
    try {
      ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
      if (node.has("message")) {
        return node.get("message").asText();
      }
    } catch (Exception e) {
      log.error("Error while reading JSON", e);
    }
    return null;
  }
}
