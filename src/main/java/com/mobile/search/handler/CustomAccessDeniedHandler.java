package com.mobile.search.handler;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.mobile.search.exception.dto.ErrorMessage;
import com.mobile.search.utils.JsonUtils;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

  @Autowired
  JsonUtils jsonUtils;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException ex) throws IOException, ServletException {
    LOGGER.error("Username: {}, Exception: {}", request.getParameter("username"), ex);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    ErrorMessage errorResp =
        new ErrorMessage(new Date(), ex.getMessage(), null);
    response.getOutputStream().write(jsonUtils.toJsonString(errorResp).getBytes());
  }
}
