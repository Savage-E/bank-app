package com.haulmont.bank.dao;

import com.haulmont.bank.data.models.CreditOffer;
import com.haulmont.bank.data.models.CreditOfferId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vlad Kotov
 * Date: 04.май.2021
 * Time:  21:06
 * Project: bank-app
 * Description:
 */
@Repository
public interface CreditOfferDAO extends CrudRepository<CreditOffer, CreditOfferId> {
}
