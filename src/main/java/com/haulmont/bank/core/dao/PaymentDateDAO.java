package com.haulmont.bank.core.dao;

import com.haulmont.bank.core.models.PaymentDate;
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
public interface PaymentDateDAO extends CrudRepository<PaymentDate, UUID> {

  List<PaymentDate> findAll();

  PaymentDate save(PaymentDate paymentDate);

  @Query("select pd from payment_date pd " +
          "where pd.creditOffer.credit.creditId = ?1 and pd.creditOffer.client.clientId=?2")
  List<PaymentDate> search(UUID creditUuid, UUID clientUuid);

  @Query("select pd from payment_date pd " +
          "where pd.creditOffer.client.clientId = ?1 or pd.creditOffer.credit.creditId=?1")
  List<PaymentDate> search(@Param("searchTerm") UUID searchTerm);
}
