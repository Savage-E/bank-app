package com.haulmont.bank.web;

import com.haulmont.bank.data.models.Bank;
import com.haulmont.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 04.май.2021
 * Time:  21:58
 * Project: bank-app
 * Description:
 */
@RestController
@RequestMapping(
        path = "/bank",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class BankController {

  private final BankService bankService;

  @Autowired
  public BankController(BankService bankService) {
    this.bankService = bankService;
  }

  @GetMapping
  List<Bank> getBank() {
    return bankService.getAll();
  }

  @PostMapping
  Bank postBank() {
    Bank bank = new Bank();
    return bankService.save(bank);
  }
}
