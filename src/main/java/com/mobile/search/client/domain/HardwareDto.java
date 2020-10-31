package com.mobile.search.client.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HardwareDto implements Serializable {
  public String audioJack;
  public String gps;
  public String battery;
}
