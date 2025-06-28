package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ServiceCost {

  private int cost;

  private String unit;

}
