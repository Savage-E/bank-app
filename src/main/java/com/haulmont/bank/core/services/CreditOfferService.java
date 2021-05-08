package com.haulmont.bank.core.services;

import com.haulmont.bank.core.dao.ClientDAO;
import com.haulmont.bank.core.dao.CreditDAO;
import com.haulmont.bank.core.dao.CreditOfferDAO;
import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.models.Credit;
import com.haulmont.bank.core.models.CreditOffer;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 05.май.2021
 * Time:  13:41
 * Project: bank-app
 * Description:
 */
@Service
public class CreditOfferService {


  private static final Logger LOGGER = Logger.getLogger(CreditOfferService.class);
  private final CreditOfferDAO creditOfferDAO;
  private final CreditDAO creditDAO;
  private final ClientDAO clientDAO;

  @Autowired
  public CreditOfferService(CreditOfferDAO creditOfferDAO, CreditDAO creditDAO, ClientDAO clientDAO) {
    this.creditOfferDAO = creditOfferDAO;
    this.clientDAO = clientDAO;
    this.creditDAO = creditDAO;
  }

  @Transactional
  public List<CreditOffer> getAll() {
    return creditOfferDAO.findAll();
  }

/*  @Transactional
  public CreditOffer save(CreditOfferDTO creditOfferDTO) {
    CreditOffer creditOffer = new CreditOffer();
    Credit credit = creditDAO.findByCreditId(creditOfferDTO.getCreditId());
    Client client = clientDAO.findByClientId(creditOfferDTO.getClientId());

    creditOffer.setCredit(credit);
    creditOffer.setClient(client);
    creditOfferDAO.save(creditOffer);
    return creditOffer;
  }*/

  @Transactional
  public int delete() {
    return creditOfferDAO.deleteBy();
  }
}
