package com.haulmont.bank.ui.views;

import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.models.Credit;
import com.haulmont.bank.core.models.PaymentDate;
import com.haulmont.bank.core.services.ClientService;
import com.haulmont.bank.core.services.CreditService;
import com.haulmont.bank.ui.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Vlad Kotov
 * Date: 10.май.2021
 * Time:  18:59
 * Project: bank-app
 * Description:
 */
@Component
@Scope("prototype")
@Route(value = "credit", layout = MainLayout.class)
@PageTitle("Add credit | Bank app")
public class LoanView extends VerticalLayout {

  ComboBox<Credit> creditComboBox = new ComboBox<>("Credit");
  ComboBox<Client> clientComboBox = new ComboBox<>("Client");
  NumberField numberField = new NumberField("Loan amount");
  Grid<PaymentDate> grid = new Grid<>(PaymentDate.class);

  CreditService creditService;
  ClientService clientService;

  @Autowired
  public LoanView(CreditService creditService, ClientService clientService) {
    addClassName("loan-form");
    this.creditService = creditService;
    this.clientService = clientService;

    creditComboBox.setItems(creditService.getAll());
    creditComboBox.setItemLabelGenerator(Credit::getCreditIdString);

    clientComboBox.setItems(clientService.getAll());
    clientComboBox.setItemLabelGenerator(Client::getClientIdString);
    configureGrid();

    Div content = new Div(grid);
    content.addClassName("loan-payday-content");
    content.setSizeFull();


    HorizontalLayout horizontalLayout = new HorizontalLayout(creditComboBox,
            clientComboBox, numberField, content);
    add(horizontalLayout);

  }

  private void configureGrid() {
    grid.addClassName("loan-payday-grid");
    grid.setColumns("date", "amount", "bodyRepaymentAmount", "interestRepaymentAmount");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));

  }
}
