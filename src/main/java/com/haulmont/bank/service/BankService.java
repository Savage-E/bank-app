package com.haulmont.bank.service;

import com.haulmont.bank.dao.BankDao;
import com.haulmont.bank.data.models.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    List<Bank> bank = bankDao.findAll();
    return bank;
  }

  public Bank save(Bank bank) {
    bankDao.save(bank);
    return bank;
  }
}
