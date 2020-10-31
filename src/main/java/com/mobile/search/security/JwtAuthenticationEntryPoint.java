package com.mobile.search.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.mobile.search.exception.dto.ErrorMessage;
import com.mobile.search.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Autowired
  JsonUtils jsonUtils;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    log.debug("Jwt authentication failed:" + authException);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    ErrorMessage errorResp =
        new ErrorMessage(new Date(), authException.getMessage(), null);
    response.getOutputStream().write(jsonUtils.toJsonString(errorResp).getBytes());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
  }

}
