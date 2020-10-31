package com.mobile.search.exception.security;

import com.mobile.search.exception.dto.MobileBusinessError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class will be used to handled business related exceptions.
 * 
 * @author Suresh Thakare
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MobileBusinessException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private String language;
  private MobileBusinessError error;

  public MobileBusinessException(MobileBusinessError error) {
    super();
    this.error = error;
  }

}
