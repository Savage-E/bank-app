package com.haulmont.bank.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 01.май2021
 * Time:  14:29
 * Project: bank-app
 * Description:
 */
@Entity
@Table(name = "credit")
@Data
public class Credit {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(nullable = false, name = "client_id")
  private UUID creditId;

  private int limit;
  @Column(name = "interest_rate")
  private int interestRate;

  @ManyToOne
  @JoinColumn(name = "bank_id")
  private Bank bank;

}
