package com.haulmont.bank.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
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
  private int loanAmount;

  public CreditOffer() {
    creditOfferId = new CreditOfferId();
  }



}
