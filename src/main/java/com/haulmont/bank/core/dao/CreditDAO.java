package com.haulmont.bank.core.dao;

import com.haulmont.bank.core.models.Credit;
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
public interface CreditDAO extends CrudRepository<Credit, UUID> {
  List<Credit> findAll();

  Credit save(Credit credit);

  int deleteAllBy();
  Credit findByCreditId(UUID uuid);
}
