package com.mobile.search.service;

import java.util.List;

import org.springframework.util.MultiValueMap;

import com.mobile.search.client.domain.MobileInfoDto;

/**
 * @author Suresh Thakare
 */

public interface MobileSearchService {
  public List<MobileInfoDto> searchMobiles(MultiValueMap<String, String> queryParamMap);

}
