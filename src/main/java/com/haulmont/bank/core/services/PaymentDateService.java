package com.haulmont.bank.core.services;

import com.haulmont.bank.core.dao.CreditOfferDAO;
import com.haulmont.bank.core.dao.PaymentDateDAO;
import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.models.Credit;
import com.haulmont.bank.core.models.CreditOffer;
import com.haulmont.bank.core.models.PaymentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 10.май.2021
 * Time:  16:11
 * Project: bank-app
 * Description:
 */
@Service
public class PaymentDateService {
  private final PaymentDateDAO paymentDateDAO;
  private final CreditOfferDAO creditOfferDAO;

  @Autowired
  public PaymentDateService(PaymentDateDAO paymentDateDAO, CreditOfferDAO creditOfferDAO) {
    this.paymentDateDAO = paymentDateDAO;
    this.creditOfferDAO = creditOfferDAO;
  }

  @Transactional
  public List<PaymentDate> getAll() {
    return paymentDateDAO.findAll();
  }

  @Transactional
  public PaymentDate save(PaymentDate paymentDate, Credit credit, Client client) {
    CreditOffer creditOffer = creditOfferDAO
            .findByCreditIdAndAndCredit(client.getClientUuid(), credit.getCreditId());
    paymentDate.setCreditOffer(creditOffer);
    return paymentDateDAO.save(paymentDate);
  }

  @Transactional
  public void delete(PaymentDate paymentDate, Credit credit, Client client) {
    CreditOffer creditOffer = creditOfferDAO
            .findByCreditIdAndAndCredit(client.getClientUuid(), credit.getCreditId());
    paymentDate.setCreditOffer(creditOffer);
    paymentDateDAO.delete(paymentDate);
  }


  @Transactional
  public void delete(PaymentDate paymentDate) {
    paymentDateDAO.delete(paymentDate);
  }
}
