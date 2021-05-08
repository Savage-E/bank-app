package com.haulmont.bank.core.services;

import com.haulmont.bank.core.dao.BankDao;
import com.haulmont.bank.core.dao.ClientDAO;
import com.haulmont.bank.core.models.Client;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 05.май.2021
 * Time:  12:29
 * Project: bank-app
 * Description:
 */
@Service
public class ClientService {

  private static final Logger LOGGER = Logger.getLogger(ClientService.class);
  private final ClientDAO clientDAO;
  private final BankDao bankDao;

  @Autowired
  public ClientService(ClientDAO clientDAO, BankDao bankDao) {
    this.clientDAO = clientDAO;
    this.bankDao = bankDao;
  }

  @Transactional
  public List<Client> getAll() {
    return clientDAO.findAll();
  }

  @Transactional
  public Client save(Client client) {
    client.setBank(bankDao.findAll().get(0));
    clientDAO.save(client);
    return client;
  }

  @Transactional
  public List<Client> get(String fio) {
    return clientDAO.search(fio);
  }

  @Transactional
  public void delete(Client client) {
    clientDAO.delete(client);
  }
}
