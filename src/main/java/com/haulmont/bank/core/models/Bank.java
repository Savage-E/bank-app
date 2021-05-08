package com.haulmont.bank.core.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
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
@Entity(name = "bank")
@Table(name = "bank")
@Getter
@Setter
public class Bank {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "bank_id")
  private UUID bankId;

  @OneToMany(mappedBy = "bank", targetEntity = Client.class, orphanRemoval = true)
  @JsonManagedReference
  private Set<Client> clientSet;

  @OneToMany(mappedBy = "bank", targetEntity = Credit.class, orphanRemoval = true)
  @JsonManagedReference
  private Set<Credit> creditSet;


}
