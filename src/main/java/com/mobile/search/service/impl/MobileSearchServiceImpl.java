package com.mobile.search.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import com.mobile.search.client.MobileApiClient;
import com.mobile.search.client.domain.MobileInfoDto;
import com.mobile.search.service.MobileSearchService;
import com.mobile.search.utils.SearchEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Suresh Thakare
 *
 */

@Slf4j
@Service
public class MobileSearchServiceImpl implements MobileSearchService {
  @Autowired
  MobileApiClient mobileApiClient;

  @Override
  public List<MobileInfoDto> searchMobiles(MultiValueMap<String, String> requestParameters) {
    List<MobileInfoDto> mobiles = mobileApiClient.getMobiles();
    if (CollectionUtils.isEmpty(requestParameters)) {
      return mobiles;
    } else {
      validateFilterCriteria(requestParameters);
      Predicate<MobileInfoDto> filterPredicate = buildFilterCriteria(requestParameters, mobiles);
      return mobiles.stream()
          .filter(
              filterPredicate).collect(Collectors.toList());
    }
  }

  private Predicate<MobileInfoDto> buildFilterCriteria(
      MultiValueMap<String, String> requestParameters,
      List<MobileInfoDto> mobiles) {
    Predicate<MobileInfoDto> filterPredicate = new Predicate<MobileInfoDto>() {
      public boolean test(MobileInfoDto t) {
        return true;
      }
    };
    if (!CollectionUtils.isEmpty(mobiles) && !CollectionUtils.isEmpty(requestParameters)) {
      Iterator<Entry<String, List<String>>> entries = requestParameters.entrySet().iterator();
      while (entries.hasNext()) {
        Entry<String, List<String>> entry = entries.next();
        SearchEnum queryParameter = SearchEnum.fromValue(entry.getKey());
        List<String> value = entry.getValue();
        if (!CollectionUtils.isEmpty(value)) {
          switch (queryParameter) {
            case BRAND:
              filterPredicate = buildBrandSearch(filterPredicate, value);
              break;
            case PHONE:
              filterPredicate = buildPhoneSearch(filterPredicate, value);
              break;
            case PICTURE:
              filterPredicate = BuildPictureFilterCriteria(filterPredicate, value);
              break;
            case SIM:
              filterPredicate = buildSimFilterCriteria(filterPredicate, value);
              break;
            case RESOLUTION:
              filterPredicate = buildResolutionFilterCriteria(filterPredicate, value);
              break;
            case ANNOUNCE_DATE:
              filterPredicate = buildAnnounceDateFilterCriteria(filterPredicate, value);
              break;
            case PRICE_EUR:
              filterPredicate = buildPriceFilterCriteria(filterPredicate, value);
              break;
            case AUDIO_JACK:
              filterPredicate = buildAudioJackFilterCriteria(filterPredicate, value);
              break;
            case GPS:
              filterPredicate = buildGpsFilterCriteria(filterPredicate, value);
              break;
            case BATTERY:
              filterPredicate = buildBatteryFilterCriteria(filterPredicate, value);
              break;
            default:
              throw new IllegalArgumentException("Unexpected value '" + value + "'");
          }
        }
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> buildBatteryFilterCriteria(
      Predicate<MobileInfoDto> filterPredicate, List<String> value) {
    List<String> values = value;
    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingbattery(v));
      } else {
        filterPredicate = filterPredicate.or(isMatchingbattery(v));
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> buildGpsFilterCriteria(Predicate<MobileInfoDto> filterPredicate,
      List<String> value) {
    List<String> values = value;
    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingGps(v));
      } else {
        filterPredicate = filterPredicate.or(isMatchingGps(v));
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> buildAudioJackFilterCriteria(
      Predicate<MobileInfoDto> filterPredicate, List<String> value) {
    List<String> values = value;
    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingAudioJack(v));
      } else {
        filterPredicate = filterPredicate.or(isMatchingAudioJack(v));
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> buildPriceFilterCriteria(
      Predicate<MobileInfoDto> filterPredicate, List<String> value) {
    if (!CollectionUtils.isEmpty(value)) {
      List<String> values = value;
      int index = 0;
      Iterator<String> iterator = values.iterator();
      while (iterator.hasNext()) {
        String v = (String) iterator.next();
        if (index == 0) {
          filterPredicate = filterPredicate.and(isMatchinPrice(Integer.valueOf(v)));
        } else {
          filterPredicate = filterPredicate.or(isMatchinPrice(Integer.valueOf(v)));
        }
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> buildAnnounceDateFilterCriteria(
      Predicate<MobileInfoDto> filterPredicate, List<String> value) {
    List<String> values = value;
    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingAnounceDate(v));

      } else {
        filterPredicate = filterPredicate.or(isMatchingAnounceDate(v));
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> buildResolutionFilterCriteria(
      Predicate<MobileInfoDto> filterPredicate, List<String> value) {
    List<String> values = value;


    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingResolution(v));

      } else {
        filterPredicate = filterPredicate.or(isMatchingResolution(v));
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> buildSimFilterCriteria(Predicate<MobileInfoDto> filterPredicate,
      List<String> value) {
    List<String> values = value;
    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingSim(v));

      } else {
        filterPredicate = filterPredicate.or(isMatchingSim(v));
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> BuildPictureFilterCriteria(
      Predicate<MobileInfoDto> filterPredicate, List<String> value) {
    List<String> values = value;
    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingPicture(v));

      } else {
        filterPredicate = filterPredicate.or(isMatchingPicture(v));
      }
    }
    return filterPredicate;
  }

  private void validateFilterCriteria(MultiValueMap<String, String> requestParameters) {
    requestParameters.forEach((key, value) -> {
      SearchEnum.fromValue(key);
    });
  }

  private Predicate<MobileInfoDto> buildBrandSearch(Predicate<MobileInfoDto> filterPredicate,
      List<String> value) {
    List<String> values = value;
    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingBrand(v));
      } else {
        filterPredicate = filterPredicate.or(isMatchingBrand(v));
      }
    }
    return filterPredicate;
  }

  private Predicate<MobileInfoDto> buildPhoneSearch(Predicate<MobileInfoDto> filterPredicate,
      List<String> value) {
    List<String> values = value;

    int index = 0;
    Iterator<String> iterator = values.iterator();
    while (iterator.hasNext()) {
      String v = (String) iterator.next();
      if (index == 0) {
        filterPredicate = filterPredicate.and(isMatchingPhone(v));

      } else {
        filterPredicate = filterPredicate.or(isMatchingPhone(v));
      }
    }
    return filterPredicate;
  }

  // Mobile Info predicates


  private static Predicate<MobileInfoDto> isMatchingBrand(String brand) {
    return mobileInfo -> mobileInfo.getBrand().toLowerCase().contains(
        brand.toLowerCase());
  }

  private static Predicate<MobileInfoDto> isMatchingPhone(String phone) {
    return mobileInfo -> mobileInfo.getPhone().toLowerCase().contains(
        phone.toLowerCase());
  }

  private static Predicate<MobileInfoDto> isMatchingSim(String sim) {
    return mobileInfo -> mobileInfo.getSim().toLowerCase().contains(
        sim.toLowerCase());
  }

  private static Predicate<MobileInfoDto> isMatchingPicture(String picture) {
    return mobileInfo -> mobileInfo.getPicture().toLowerCase().equals(
        picture.toLowerCase());
  }

  private static Predicate<MobileInfoDto> isMatchingResolution(String resolution) {
    return mobileInfo -> mobileInfo.getResolution().toLowerCase().contains(
        resolution.toLowerCase());
  }

  // Hardware Predicates
  private static Predicate<MobileInfoDto> isMatchinPrice(Integer price) {
    return mobileInfo -> price
        .compareTo(mobileInfo.getRelease().getPriceEur()) == 0;
  }

  private static Predicate<MobileInfoDto> isMatchingAnounceDate(String announceDate) {
    return mobileInfo -> mobileInfo.getRelease().getAnnounceDate().toLowerCase().contains(
        announceDate.toLowerCase());
  }

  // Hardware Predicates
  private static Predicate<MobileInfoDto> isMatchingAudioJack(String audioJack) {
    return mobileInfo -> mobileInfo.getHardware().getAudioJack().toLowerCase().equals(
        audioJack.toLowerCase());
  }

  private static Predicate<MobileInfoDto> isMatchingGps(String gps) {
    return mobileInfo -> mobileInfo.getHardware().getGps().toLowerCase().contains(
        gps.toLowerCase());
  }

  private static Predicate<MobileInfoDto> isMatchingbattery(String battery) {
    return mobileInfo -> mobileInfo.getHardware().getBattery().toLowerCase().contains(
        battery.toLowerCase());
  }

  private static <MobileInfoDto> Predicate<MobileInfoDto> isNull() {
    return Objects::isNull;
  }
}
