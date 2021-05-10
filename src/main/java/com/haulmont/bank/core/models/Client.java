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
 * Time:  14:24
 * Project: bank-app
 * Description:
 */
@Entity(name = "client")
@Table(name = "client")
@Data
public class Client {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(nullable = false, name = "client_id")

  private UUID clientId;

  @Column(length = 60)
  private String fio;

  @Column(name = "phone_number")
  private int phoneNumber;

  @Column(length = 60)
  private String email;

  @Column(name = "passport_number")
  private int passportNum;

  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "bank_id")
  @JsonBackReference
  private Bank bank;

  @OneToMany(mappedBy = "client", orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<CreditOffer> creditOfferSet;

  public String getClientIdString() {
    return clientId.toString();
  }

  public UUID getClientUuid() {
    return clientId;
  }


}
