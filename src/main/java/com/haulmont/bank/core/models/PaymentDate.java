package com.haulmont.bank.core.models;

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

  private double amount;

  @Column(name = "body_repay_amount ")
  private double bodyRepaymentAmount;
  @Column(name = "interest_repay_amount")
  private double interestRepaymentAmount;

  @ManyToOne
  @JoinColumns(value = {@JoinColumn(name = "client_id"), @JoinColumn(name = "credit_id")})
  private CreditOffer creditOffer;

  public PaymentDate() {
    date = LocalDate.now();
  }

  public String dateToString() {
    return date.toString();
  }
}
