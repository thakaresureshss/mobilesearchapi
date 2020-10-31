package com.mobile.search.domain;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * This is request class which will take request values.
 * 
 * @author Suresh Thakare
 *
 */
@Setter
@Getter
@Data
public class AuthTokenRequest {
  @NotBlank(message = "Username must not be blank")
  private String username;

  @NotBlank(message = "Password must not be blank")
  private String password;
}
