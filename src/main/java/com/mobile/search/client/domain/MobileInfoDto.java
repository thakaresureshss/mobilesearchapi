package com.mobile.search.client.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MobileInfoDto implements Serializable {
  private static final long serialVersionUID = -6290303410105219681L;
  public int id;
  public String brand;
  public String phone;
  public String picture;
  @JsonProperty("release")
  public ReleaseDto release;
  public String sim;
  public String resolution;
  @JsonProperty("hardware")
  public HardwareDto hardware;
}
