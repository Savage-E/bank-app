package com.haulmont.bank.core.services;

import com.haulmont.bank.core.models.Bank;
import com.haulmont.bank.core.dao.BankDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 04.май.2021
 * Time:  21:54
 * Project: bank-app
 * Description:
 */
@Service
public class BankService {

  private final BankDao bankDao;

  @Autowired
  public BankService(BankDao bankDao) {
    this.bankDao = bankDao;
  }

  public List<Bank> getAll() {
    return bankDao.findAll();
  }

  @Transactional
  public Bank save(Bank bank) {
    bankDao.save(bank);
    return bank;
  }

  @Transactional
  public int delete() {
    return bankDao.deleteAllBy();
  }
}
