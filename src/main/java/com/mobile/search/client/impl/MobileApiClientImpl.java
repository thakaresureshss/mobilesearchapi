package com.mobile.search.client.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.mobile.search.client.MobileApiClient;
import com.mobile.search.client.domain.MobileInfoDto;
import com.mobile.search.constants.ErrorConstants;
import com.mobile.search.exception.dto.MobileBusinessError;
import com.mobile.search.exception.security.MobileBusinessException;
import com.mobile.search.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MobileApiClientImpl implements MobileApiClient {


  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private JsonUtils jsonUtils;

  private String getUrl() {
    return "https://a511e938-a640-4868-939e-6eef06127ca1.mock.pstmn.io/handsets/list";
  }

  @Override
  @Cacheable("mobiles")
  public List<MobileInfoDto> getMobiles() {
    HttpHeaders headers = new HttpHeaders();
    /** Token can be passed here */
    try {
      HttpEntity<String> request = new HttpEntity<>(headers);
      ResponseEntity<List<MobileInfoDto>> tenants = restTemplate.exchange(getUrl(),
          HttpMethod.GET, request,
          new ParameterizedTypeReference<List<MobileInfoDto>>() {});
      return tenants.getBody();
    } catch (HttpStatusCodeException ex) {
      log.error("[** ERROR **  Getting error response from mobile Database API !!! ]", ex);
      String responseStr = ex.getResponseBodyAsString();
      try {
        MobileBusinessError elaaError = jsonUtils.fromJsonStringToMobileError(
            responseStr, MobileBusinessError.class);
        throw new MobileBusinessException(elaaError);
      } catch (IOException e1) {
        log.error(
            "[** ERROR **  Getting error while converting Mobile Database API error resonse to Mobile Search API reponse!!! ]",
            ex);
        throw new MobileBusinessException(
            new MobileBusinessError(HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorConstants.MOBILE_REFULT_NOT_FOUND));
      }

    } catch (Exception e) {
      log.error("[** ERROR **  Getting error to get respose from mobile API!!! ]", e);
      throw new MobileBusinessException(
          new MobileBusinessError(HttpStatus.INTERNAL_SERVER_ERROR,
              ErrorConstants.MOBILE_REFULT_NOT_FOUND));
    }
  }
}
