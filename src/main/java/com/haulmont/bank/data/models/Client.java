package com.haulmont.bank.data.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 01.май2021
 * Time:  14:24
 * Project: bank-app
 * Description:
 */
@Entity
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

  private String fio;

  @Column(name = "phone_number")
  private String phoneNumber;

  private String email;

  @Column(name = "passport_number")
  private int passportNum;

  @ManyToOne
  @JoinColumn(name = "bank_id")
  private Bank bank;

}
