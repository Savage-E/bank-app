package com.haulmont.bank.data.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 02.май2021
 * Time:  20:30
 * Project: bank-app
 * Description:
 */
@Entity(name = "payment_date")
@Table(name = "payment_date")
@Data
public class PaymentDate {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator"
  )
  private UUID id;

  private LocalDate date;

  private int amount;

  @Column(name = "body_repay_amount ")
  private int bodyRepaymentAmount;
  @Column(name = "interest_repay_amount")
  private int interestRepaymentAmount;

  @ManyToOne
  @JoinColumns(value = {@JoinColumn(name = "client_id"), @JoinColumn(name = "credit_id")})
  private CreditOffer creditOffer;

}
