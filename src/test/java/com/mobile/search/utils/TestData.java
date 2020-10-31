package com.mobile.search.utils;

import java.util.ArrayList;
import java.util.List;

import com.mobile.search.client.domain.HardwareDto;
import com.mobile.search.client.domain.MobileInfoDto;
import com.mobile.search.client.domain.ReleaseDto;

public class TestData {
  public static List<MobileInfoDto> getMobiles() {
    List<MobileInfoDto> mobiles = new ArrayList<>();
    MobileInfoDto dto = new MobileInfoDto();
    dto.setId(25846);
    dto.setBrand("Apple");
    dto.setPhone("Apple iPad Pro 12.9 (2018)");
    dto.setPicture("https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-129-2018.jpg");
    dto.setSim("Nano-SIM eSIM");
    dto.setResolution("2048 x 2732 pixels");
    ReleaseDto release = new ReleaseDto();
    release.setAnnounceDate("2018 October");
    release.setPriceEur(200);
    dto.setRelease(release);
    HardwareDto hardware = new HardwareDto();
    hardware.setAudioJack("No");
    hardware.setBattery("Li-Po 9720 mAh battery (36.71 Wh)");
    hardware.setGps("Yes with A-GPS");
    dto.setHardware(hardware);
    mobiles.add(dto);

    MobileInfoDto dto1 = new MobileInfoDto();
    dto1.setId(22895);
    dto1.setBrand("Apple");
    dto1.setPhone(" Apple iPad Pro 11");
    dto1.setPicture("https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-11-2018.jpg");
    dto1.setSim("Nano-SIM");
    dto1.setResolution("1242 x 2688 pixels");
    ReleaseDto release1 = new ReleaseDto();
    release1.setAnnounceDate("2018 October");
    release1.setPriceEur(880);
    dto1.setRelease(release1);
    HardwareDto hardware1 = new HardwareDto();
    hardware1.setAudioJack("No");
    hardware1.setBattery("Li-Ion 3174 mAh battery");
    hardware1.setGps("Yes with A-GPS");
    dto1.setHardware(hardware1);
    mobiles.add(dto1);

    MobileInfoDto dto2 = new MobileInfoDto();
    dto2.setId(28136);
    dto2.setBrand("Apple");
    dto2.setPhone("Apple iPad Pro 12.9 (2018)");
    dto2.setPicture("https://cdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-129-2018.jpg");
    dto2.setSim("eSIM");
    dto2.setResolution("2048 x 2732 pixels");
    ReleaseDto release2 = new ReleaseDto();
    release2.setAnnounceDate("1999 October");
    release2.setPriceEur(200);
    dto2.setRelease(release2);
    HardwareDto hardware2 = new HardwareDto();
    hardware2.setAudioJack("No");
    hardware2.setBattery("Li-Po 9720 mAh battery (36.71 Wh)");
    hardware2.setGps("Yes with A-GPS");
    dto2.setHardware(hardware2);
    mobiles.add(dto2);

    return mobiles;
  }
}
