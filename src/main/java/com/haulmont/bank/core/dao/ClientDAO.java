package com.haulmont.bank.core.dao;

import com.haulmont.bank.core.models.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
public interface ClientDAO extends CrudRepository<Client, UUID> {
  List<Client> findAll();

  Client save(Client client);

  Client findByClientId(UUID uuid);

  @Query("select c from client c " +
          "where lower(c.fio) like lower(concat('%', :searchTerm, '%')) ")
  List<Client> search(@Param("searchTerm") String searchTerm);

}
