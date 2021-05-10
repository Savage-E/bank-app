package com.haulmont.bank.core.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 01.май2021
 * Time:  14:29
 * Project: bank-app
 * Description:
 */
@Entity(name = "credit")
@Table(name = "credit")
@Data
public class Credit {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(nullable = false, name = "creditId")
  private UUID creditId;

  private double limit;

  @Column(name = "interest_rate")
  private double interestRate;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "bank_id")
  @JsonBackReference
  private Bank bank;
  @OneToMany(mappedBy = "credit", orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<CreditOffer> creditOfferSet;

  public String getCreditIdString() {
    return creditId.toString();
  }

}
