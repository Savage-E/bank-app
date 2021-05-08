package com.haulmont.bank.core.services;

import com.haulmont.bank.core.dao.BankDao;
import com.haulmont.bank.core.dao.CreditDAO;
import com.haulmont.bank.core.models.Credit;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 05.май.2021
 * Time:  13:34
 * Project: bank-app
 * Description:
 */
@Service
public class CreditService {

  private static final Logger LOGGER = Logger.getLogger(CreditService.class);
  private final CreditDAO creditDAO;
  private final BankDao bankDao;

  @Autowired
  public CreditService(CreditDAO creditDAO, BankDao bankDao) {
    this.creditDAO = creditDAO;
    this.bankDao = bankDao;
  }


  @Transactional
  public List<Credit> getAll() {
    return creditDAO.findAll();
  }

  @Transactional
  public Credit save(Credit credit) {

    credit.setBank(bankDao.findAll().get(0));
    creditDAO.save(credit);
    return credit;
  }


  public Credit get(UUID creditUuid) {
    return creditDAO.findByCreditId(creditUuid);
  }

  @Transactional
  public void delete(Credit credit) {
    creditDAO.delete(credit);
  }
}
