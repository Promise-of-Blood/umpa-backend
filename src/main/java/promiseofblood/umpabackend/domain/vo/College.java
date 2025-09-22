package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum College implements EnumVoType {
  // 강동대학교
  GANGDONG_COLLEGE("강동대학교"),
  // 경북과학대학교
  KYONGBUK_SCIENCE_COLLEGE("경북과학대학교"),
  // 계명문화대학교
  KEIMYUNG_CULTURE_COLLEGE("계명문화대학교"),
  // 국제예술대학교
  INTERNATIONAL_ART_COLLEGE("국제예술대학교"),
  // 대경대학교
  DAEKYUNG_COLLEGE("대경대학교"),
  // 대동대학교
  DAEDONG_COLLEGE("대동대학교"),
  // 동아보건대학교
  DONGA_HEALTH_COLLEGE("동아보건대학교"),
  // 동원대학교
  DONGWON_COLLEGE("동원대학교"),
  // 두원공과대학교
  DOOWON_ENGINEERING_COLLEGE("두원공과대학교"),
  // 명지전문대학
  MYONGJI_COLLEGE("명지전문대학"),
  // 목포과학대학교
  MOKPO_SCIENCE_COLLEGE("목포과학대학교"),
  // 백석문화대학교
  BAEKSEOK_CULTURE_COLLEGE("백석문화대학교"),
  // 백석예술대학교
  BAEKSEOK_ART_COLLEGE("백석예술대학교"),
  // 백제예술대학교
  BAEKJE_ART_COLLEGE("백제예술대학교"),
  // 부산예술대학교
  BUSAN_ART_COLLEGE("부산예술대학교"),
  // 수원여자대학교
  SUWON_WOMEN_COLLEGE("수원여자대학교"),
  // 우송정보대학
  WOOSONG_INFORMATION_COLLEGE("우송정보대학"),
  // 인천재능대학교
  INCHEON_JAENEUNG_COLLEGE("인천재능대학교"),
  // 장안대학교
  JANGAN_COLLEGE("장안대학교"),
  // 정화예술대학교
  JEONGHWA_ART_COLLEGE("정화예술대학교"),
  // 제주한라대학교
  JEJU_HALLA_COLLEGE("제주한라대학교"),
  // 충청대학교
  CHUNGCHEONG_COLLEGE("충청대학교"),
  // 한국복지대학교
  KOREA_WELFARE_COLLEGE("한국복지대학교"),
  // 경복대학교
  KYUNGBOK_COLLEGE("경복대학교"),
  // 김포대학교
  GIMPO_COLLEGE("김포대학교"),
  // 동서울대학교
  DONGSEOUL_COLLEGE("동서울대학교"),
  // 동주대학교
  DONGJU_COLLEGE("동주대학교"),
  // 동아방송예술대학교
  DONGA_BROADCASTING_ART_COLLEGE("동아방송예술대학교"),
  // 서울예술대학교
  SEOUL_ART_COLLEGE("서울예술대학교"),
  // 신안산대학교
  SHINANSAN_COLLEGE("신안산대학교"),
  // 여주대학교
  YEOJU_COLLEGE("여주대학교"),
  // 용인예술과학대학교
  YONGIN_ART_SCIENCE_COLLEGE("용인예술과학대학교"),
  // 전남도립대학교
  JEONNAM_PROVINCIAL_COLLEGE("전남도립대학교"),
  // 한양여자대학교
  HANYANG_WOMEN_COLLEGE("한양여자대학교"),
  // 가톨릭관동대학교
  CATHOLIC_KWANDONG_COLLEGE("가톨릭관동대학교"),
  // 강릉원주대학교
  GANGNEUNG_WONJU_COLLEGE("강릉원주대학교"),
  // 강서대학교
  GANGSEO_COLLEGE("강서대학교"),
  // 경기대학교
  KYONGGI_COLLEGE("경기대학교"),
  // 경민대학교
  KYUNGMIN_COLLEGE("경민대학교"),
  // 경희대학교
  KYUNGHEE_COLLEGE("경희대학교"),
  // 계명대학교
  KEIMYUNG_COLLEGE("계명대학교"),
  // 고신대학교
  KOSIN_COLLEGE("고신대학교"),
  // 광신대학교
  KWANGSIN_COLLEGE("광신대학교"),
  // 남서울대학교
  NAMSEOUL_COLLEGE("남서울대학교"),
  // 단국대학교
  DANKOOK_COLLEGE("단국대학교"),
  // 대구가톨릭대학교
  DAEGU_CATHOLIC_COLLEGE("대구가톨릭대학교"),
  // 대구예술대학교
  DAEGU_ART_COLLEGE("대구예술대학교"),
  // 대신대학교
  DAESIN_COLLEGE("대신대학교"),
  // 대진대학교
  DAEJIN_COLLEGE("대진대학교"),
  // 동덕여자대학교
  DONGDUK_WOMEN_COLLEGE("동덕여자대학교"),
  // 동명대학교
  DONGMYUNG_COLLEGE("동명대학교"),
  // 동아대학교
  DONGA_COLLEGE("동아대학교"),
  // 동의대학교
  DONGUI_COLLEGE("동의대학교"),
  // 디지털서울문화예술대학교
  DIGITAL_SEOUL_CULTURE_ART_COLLEGE("디지털서울문화예술대학교"),
  // 목원대학교
  MOKWON_COLLEGE("목원대학교"),
  // 배재대학교
  PAICHAI_COLLEGE("배재대학교"),
  // 백석대학교
  BAEKSEOK_COLLEGE("백석대학교"),
  // 서경대학교
  SEOKYEONG_COLLEGE("서경대학교"),
  // 성결대학교
  SUNGKYUL_COLLEGE("성결대학교"),
  // 성신여자대학교
  SUNGSHIN_WOMEN_COLLEGE("성신여자대학교"),
  // 서울디지털대학교
  SEOUL_DIGITAL_COLLEGE("서울디지털대학교"),
  // 서울사이버대학교
  SEOUL_CYBER_COLLEGE("서울사이버대학교"),
  // 송원대학교
  SONGWON_COLLEGE("송원대학교"),
  // 세한대학교
  SEHAN_COLLEGE("세한대학교"),
  // 신한대학교
  SHINHAN_COLLEGE("신한대학교"),
  // 안양대학교
  ANYANG_COLLEGE("안양대학교"),
  // 용인대학교
  YONGIN_COLLEGE("용인대학교"),
  // 예원예술대학교
  YEWON_ART_COLLEGE("예원예술대학교"),
  // 중부대학교
  JOONGBU_COLLEGE("중부대학교"),
  // 중앙대학교
  CHUNG_ANG_COLLEGE("중앙대학교"),
  // 평택대학교
  PYEONGTAEK_COLLEGE("평택대학교"),
  // 한서대학교
  HANSEO_COLLEGE("한서대학교"),
  // 한일장신대학교
  HANIL_JANGSIN_COLLEGE("한일장신대학교"),
  // 한양대학교
  HANYANG_COLLEGE("한양대학교"),
  // 협성대학교
  HYUPSUNG_COLLEGE("협성대학교"),
  // 호남신학대학교
  HONAM_THEOLOGICAL_COLLEGE("호남신학대학교"),
  // 호서대학교
  HOSEO_COLLEGE("호서대학교"),
  // 호원대학교
  HOWON_COLLEGE("호원대학교"),
  // 홍익대학교
  HONGIK_COLLEGE("홍익대학교");

  private final String koreanName;

  @Override
  public String getName() {
    return this.getKoreanName();
  }

  @Override
  public String getCode() {
    return this.name();
  }
}
