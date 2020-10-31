package com.mobile.search.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.search.client.domain.MobileInfoDto;
import com.mobile.search.service.MobileSearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Suresh
 */

@Slf4j
@Api(tags = {"Search Mobiles"})
@RestController
@RequestMapping("/mobile")
public class SearchController {

  @Autowired
  MobileSearchService mobileSearchService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "Search Mobiles", response = List.class)
  @GetMapping(
    value = "/search",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> searchMobile(@RequestParam(
    required = false) MultiValueMap<String, String> params, @ApiParam(
      value = "pagination - page number (starts from 0)",
      defaultValue = "0") @Valid @RequestParam(
        value = "page",
        required = false,
        defaultValue = "0") Integer page, @Min(1) @Max(100) @ApiParam(
          value = "pagination - maximum number of results per page",
          defaultValue = "20") @Valid @RequestParam(
            value = "size",
            required = false,
            defaultValue = "20") Integer size) {
    log.info("Searching for Parameters : {}, Page:{}, Size: {}", params, page, size);
    long startIndex = page * size;
    long endIndex = size;
    List<MobileInfoDto> searchMobiles = mobileSearchService.searchMobiles(params);
    if (!CollectionUtils.isEmpty(searchMobiles)) {
      searchMobiles = searchMobiles.stream().skip(
          startIndex).limit(
              endIndex)
          .collect(
              Collectors.toList());
    }
    return new ResponseEntity<>(searchMobiles, HttpStatus.OK);
  }
}

