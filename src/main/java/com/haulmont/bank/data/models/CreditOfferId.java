package com.haulmont.bank.data.models;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 04.май.2021
 * Time:  22:01
 * Project: bank-app
 * Description:
 */

@Embeddable
@Data
public class CreditOfferId implements Serializable {

  private UUID creditId;
  private UUID clientId;

  public CreditOfferId() {

  }

  public CreditOfferId(UUID creditId, UUID clientId) {
    this.creditId = creditId;
    this.clientId = clientId;
  }
}