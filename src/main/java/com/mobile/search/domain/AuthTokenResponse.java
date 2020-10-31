package com.mobile.search.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class act as response for JWT tokens.
 * 
 * @author Suresh Thakare
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthTokenResponse {
  private String accessToken;
  private String tokenType = "Bearer";

  public AuthTokenResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
