package com.haulmont.bank.core.dao;

import com.haulmont.bank.core.models.CreditOffer;
import com.haulmont.bank.core.models.CreditOfferId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

  @Query("select co from credit_offer co " +
          "where co.client.clientId = ?1 or co.credit.creditId=?1")
  List<CreditOffer> search(@Param("searchTerm") UUID searchTerm);

  @Query("select co from credit_offer co " +
          "where co.client.clientId = ?1 and co.credit.creditId=?2")
  CreditOffer findByCreditIdAndAndCredit(UUID clientUuid, UUID creditUuid);

}
