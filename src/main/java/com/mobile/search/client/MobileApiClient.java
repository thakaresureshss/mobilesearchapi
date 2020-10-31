package com.mobile.search.client;

import java.util.List;

import com.mobile.search.client.domain.MobileInfoDto;

public interface MobileApiClient {
  public List<MobileInfoDto> getMobiles();
}
