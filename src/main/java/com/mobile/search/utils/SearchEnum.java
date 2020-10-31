package com.mobile.search.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SearchEnum {

  BRAND("brand"), PHONE("phone"), PICTURE("picture"), SIM("sim"), RESOLUTION(
      "resolution"), ANNOUNCE_DATE("announceDate"), PRICE_EUR("priceEur"), AUDIO_JACK(
          "audioJack"), GPS("gps"), BATTERY("battery");

  private String value;

  SearchEnum(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static SearchEnum fromValue(String value) {
    for (SearchEnum b : SearchEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
