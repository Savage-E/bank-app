package com.haulmont.bank.core.dao;

import com.haulmont.bank.core.models.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 04.май.2021
 * Time:  21:05
 * Project: bank-app
 * Description:
 */
@Repository
public interface BankDAO extends CrudRepository<Bank, UUID> {
  List<Bank> findAll();

  Bank save(Bank bank);

  Bank findByBankId(UUID uuid);

  int deleteAllBy();
}
