package com.mobile.search.exception.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mobile.search.exception.dto.ErrorDetails;
import com.mobile.search.exception.dto.ErrorMessage;
import com.mobile.search.exception.dto.MobileBusinessError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestControllerAdvice
public class MobileExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());

    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, errorList);
    return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
  }

  @ExceptionHandler(MobileBusinessException.class)
  public ResponseEntity<?> handleGlobalException(MobileBusinessException ex,
      WebRequest request) {
    log.error("Handling MobileBusinessException '{}'" + ex);
    MobileBusinessError error = ex.getError();
    return new ResponseEntity<Object>(error, error.getStatus());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleResourceNotFound(IllegalArgumentException ex,
      WebRequest request) {
    log.debug("handling IllegalArgumentException...");
    ErrorMessage exception =
        new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
    ex.printStackTrace();
    log.debug("handling Exception...");
    log.error("Handling Exception '{}'" + ex);
    ErrorMessage exception =
        new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<Object>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
