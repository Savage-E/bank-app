package com.haulmont.bank.data.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 01.май2021
 * Time:  14:30
 * Project: bank-app
 * Description:
 */
@Entity
@Table(name = "bank")
@Data
public class Bank {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "bank_id")
  private UUID bankUuid;

  @OneToMany(mappedBy = "bank")
  private Set<Client> clientSet;

  @OneToMany(mappedBy = "bank")
  private Set<Credit> creditSet;


}
