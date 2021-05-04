package com.haulmont.bank.dao;

import com.haulmont.bank.data.models.PaymentDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
}
