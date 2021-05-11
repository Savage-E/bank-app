package com.haulmont.bank.core.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 01.май2021
 * Time:  14:29
 * Project: bank-app
 * Description:
 */
@Entity(name = "credit_offer")
@Table(name = "credit_offer")
@Getter
@Setter
public class CreditOffer {

  @EmbeddedId
  private CreditOfferId creditOfferId;

  @Column(name = "loan_amount")
  private double loanAmount;

  @Column(name = "loan_period")
  private int loanPeriod;
  @MapsId("creditId")
  @ManyToOne
  @JoinColumn(name = "credit_id")
  private Credit credit;

  @MapsId("clientId")
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @OneToMany(mappedBy = "creditOffer", orphanRemoval = true)
  private Set<PaymentDate> paymentDateSet;

  public CreditOffer() {
    creditOfferId = new CreditOfferId();
    paymentDateSet = new HashSet<>();
  }


  public UUID getClientId() {
    return creditOfferId.getClientId();
  }


  public void setClientId(UUID clientUuid) {
    creditOfferId.setClientId(clientUuid);
  }

  public UUID getCreditId() {
    return creditOfferId.getCreditId();
  }

  public void setCreditId(UUID creditUuid) {
    creditOfferId.setClientId(creditUuid);
  }

}
