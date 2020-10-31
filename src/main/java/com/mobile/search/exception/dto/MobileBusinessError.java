package com.mobile.search.exception.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Suresh Thakare
 */

@Setter
@Getter
public class MobileBusinessError {
  private static final long serialVersionUID = 1L;
  private HttpStatus status;
  private String code;
  private String message;
  private String debugMessage;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;
  private List<MobileValidationError> voilations;

  public MobileBusinessError() {
    timestamp = LocalDateTime.now();
  }

  public MobileBusinessError(HttpStatus status) {
    this();
    this.status = status;
  }

  public MobileBusinessError(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }


  public MobileBusinessError(HttpStatus status, String code, Throwable ex) {
    this();
    this.status = status;
    this.code = code;
    this.debugMessage = ex.getLocalizedMessage();
  }

  public MobileBusinessError(HttpStatus status, String code, String message) {
    this();
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public MobileBusinessError(HttpStatus status, String code) {
    this();
    this.status = status;
    this.code = code;
  }

  public MobileBusinessError(HttpStatus status, List<MobileValidationError> voilations) {
    super();
    this.status = status;
    this.voilations = voilations;
  }

}
