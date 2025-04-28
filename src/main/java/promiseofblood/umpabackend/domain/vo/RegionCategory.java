package promiseofblood.umpabackend.domain.vo;


import java.util.List;
import lombok.Getter;

@Getter
public enum RegionCategory {
  SEOUL("서울특별시", Region.getRegionsByPrefix("SEOUL")),
  BUSAN("부산광역시", Region.getRegionsByPrefix("BUSAN")),
  DAEGU("대구광역시", Region.getRegionsByPrefix("DAEGU")),
  INCHEON("인천광역시", Region.getRegionsByPrefix("INCHEON")),
  GWANGJU("광주광역시", Region.getRegionsByPrefix("GWANGJU")),
  DAEJEON("대전광역시", Region.getRegionsByPrefix("DAEJEON")),
  ULSAN("울산광역시", Region.getRegionsByPrefix("ULSAN")),
  SEJONG("세종특별자치시", Region.getRegionsByPrefix("SEJONG")),
  GYEONGGI("경기도", Region.getRegionsByPrefix("GYEONGGI")),
  GANGWON("강원특별자치도", Region.getRegionsByPrefix("GANGWON")),
  CHUNGBUK("충청북도", Region.getRegionsByPrefix("CHUNGBUK")),
  CHUNGNAM("충청남도", Region.getRegionsByPrefix("CHUNGNAM")),
  JEONBUK("전북특별자치도", Region.getRegionsByPrefix("JEONBUK")),
  JEONNAM("전라남도", Region.getRegionsByPrefix("JEONNAM")),
  GYEONGBUK("경상북도", Region.getRegionsByPrefix("GYEONGBUK")),
  GYEONGNAM("경상남도", Region.getRegionsByPrefix("GYEONGNAM")),
  JEJU("제주특별자치도", Region.getRegionsByPrefix("JEJU"));

  private final String code;
  private final String koreanName;
  private final List<Region> regions;

  RegionCategory(String koreanName, List<Region> regions) {
    this.code = this.name();
    this.koreanName = koreanName;
    this.regions = regions;
  }
  
}

