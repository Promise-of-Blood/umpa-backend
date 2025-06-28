package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class DurationRange {

  private int minDays;

  private int maxDays;

}