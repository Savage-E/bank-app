package com.haulmont.bank.core.dao;

import com.haulmont.bank.core.models.CreditOffer;
import com.haulmont.bank.core.models.CreditOfferId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 04.май.2021
 * Time:  21:06
 * Project: bank-app
 * Description:
 */
@Repository
public interface CreditOfferDAO extends CrudRepository<CreditOffer, CreditOfferId> {
  List<CreditOffer> findAll();

  CreditOffer save(CreditOffer creditOffer);

  int deleteBy();


}
