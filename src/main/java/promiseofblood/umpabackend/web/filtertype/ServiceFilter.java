package promiseofblood.umpabackend.web.filtertype;

public enum ServiceFilter {
  ALL,
  LIKED,
  RECENT,
  POPULAR;

  public static ServiceFilter fromString(String value) {
    if (value == null) {
      return ALL;
    }
    try {
      return valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ALL;
    }
  }
}
