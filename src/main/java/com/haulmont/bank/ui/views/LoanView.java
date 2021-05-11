package com.haulmont.bank.ui.views;

import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.models.Credit;
import com.haulmont.bank.core.models.CreditOffer;
import com.haulmont.bank.core.models.PaymentDate;
import com.haulmont.bank.core.services.ClientService;
import com.haulmont.bank.core.services.CreditOfferService;
import com.haulmont.bank.core.services.CreditService;
import com.haulmont.bank.core.services.PaymentDateService;
import com.haulmont.bank.ui.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class LoanView extends HorizontalLayout {

  private static final Logger LOGGER = Logger.getLogger(LoanView.class);
  ComboBox<Credit> creditComboBox = new ComboBox<>("Credit");
  ComboBox<Client> clientComboBox = new ComboBox<>("Client");
  NumberField loanAmount = new NumberField("Loan amount");
  IntegerField loanPeriod = new IntegerField("loan period");
  Grid<PaymentDate> grid = new Grid<>(PaymentDate.class);
  Button calculateButton = new Button("Create a credit offer");
  Button saveButton = new Button("Save credit");
  CreditService creditService;
  ClientService clientService;
  CreditOfferService creditOfferService;
  PaymentDateService paymentDateService;

  List<PaymentDate> paymentDateList;

  CreditOffer creditOffer;

  @Autowired
  public LoanView(CreditService creditService, ClientService clientService,
                  CreditOfferService creditOfferService, PaymentDateService paymentDateService) {

    addClassName("loan-form");
    this.creditService = creditService;
    this.clientService = clientService;
    this.paymentDateService = paymentDateService;
    this.creditOfferService = creditOfferService;
    configureComboBox();
    configureGrid();

    saveButton.addClickListener(event -> saveCredit());
    saveButton.setVisible(false);

    calculateButton.setEnabled(false);
    calculateButton.addClickListener(event -> calculate(creditComboBox.getValue(),
            clientComboBox.getValue(), loanAmount.getValue(),
            loanPeriod.getValue()));

    saveButton.addClickListener(event -> saveCredit());

    clientComboBox.addValueChangeListener(event -> enableCalculateButton());
    creditComboBox.addValueChangeListener(event -> enableCalculateButton());
    loanPeriod.addValueChangeListener(event -> enableCalculateButton());
    loanAmount.addValueChangeListener(event -> enableCalculateButton());
    Div content = new Div(grid);
    content.addClassName("loan-payday-content");
    content.setSizeFull();

    VerticalLayout verticalLayout1 = new VerticalLayout(content);
    verticalLayout1.setSizeFull();
    VerticalLayout verticalLayout = new VerticalLayout();
    verticalLayout.add(creditComboBox, clientComboBox, loanAmount,
            loanPeriod, calculateButton, saveButton);
    verticalLayout.setSpacing(true);
    verticalLayout.setPadding(true);
    setPadding(true);
    setSpacing(true);

    add(verticalLayout, verticalLayout1);
  }

  private boolean isCreditOfferExist() {
    CreditOffer tempCreditOffer = creditOfferService
            .get(creditComboBox.getValue(), clientComboBox.getValue());
    if (tempCreditOffer != null) {
      Dialog dialog = new Dialog(new Text("Current credit offer already exists."));
      dialog.setCloseOnEsc(true);
      dialog.open();
      return true;
    }
    return false;
  }

  private void calculate(Credit credit, Client client, double sum, int period) {

    if (isCreditOfferExist()) {

      return;
    }
    if (credit.getLimit() >= sum) {
      creditOffer = new CreditOffer();
      creditOffer.setCredit(credit);
      creditOffer.setClient(client);
      creditOffer.setLoanAmount(sum);
      creditOffer.setLoanPeriod(period);

      BigDecimal percent = BigDecimal.valueOf(credit.getInterestRate());
      BigDecimal totalSum = new BigDecimal(sum);
      BigDecimal repaymentInMonth = totalSum.divide(BigDecimal.valueOf(period),
              2, RoundingMode.HALF_EVEN);
      BigDecimal percentMonth = percent.divide(BigDecimal.valueOf(12),
              4, RoundingMode.HALF_EVEN);
      paymentDateList = new ArrayList<>();
      PaymentDate paymentDate;
      for (int i = 0; i < period; i++) {
        paymentDate = new PaymentDate();
        BigDecimal monthPayForPercent = totalSum.multiply(percentMonth)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
        totalSum = totalSum.subtract(repaymentInMonth);
        paymentDate.setDate(LocalDate.now().plusMonths(i));
        paymentDate.setBodyRepaymentAmount(repaymentInMonth.doubleValue());
        paymentDate.setInterestRepaymentAmount(monthPayForPercent.doubleValue());
        paymentDate.setAmount((repaymentInMonth.doubleValue() + monthPayForPercent.doubleValue()));
        paymentDateList.add(paymentDate);
      }

      grid.setItems(paymentDateList);
      saveButton.setVisible(true);
    } else {
      Dialog dialog = new Dialog(new Text("Amount of money out of credit limit!"));
      dialog.setCloseOnEsc(true);
      dialog.open();
    }
  }

  private void saveCredit() {
    creditOffer = creditOfferService.save(creditOffer);
    paymentDateList.forEach(x -> x.setCreditOffer(creditOffer));
    paymentDateService.save(paymentDateList);
    updateComponents();
  }

  private void updateComponents() {
    configureComboBox();
    creditComboBox.clear();
    clientComboBox.clear();
    loanAmount.clear();
    loanPeriod.clear();
    grid.setItems(Collections.emptyList());
  }

  private void configureGrid() {
    grid.addClassName("loan-payday-grid");
    grid.setColumns("date", "amount", "bodyRepaymentAmount", "interestRepaymentAmount");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));

  }

  private void configureComboBox() {
    creditComboBox.setItems(creditService.getAll());
    creditComboBox.setItemLabelGenerator(Credit::getCreditIdString);
    clientComboBox.setItems(clientService.getAll());
    clientComboBox.setItemLabelGenerator(Client::getClientIdString);
  }

  private void enableCalculateButton() {
    if ((clientComboBox.getValue() != null && creditComboBox.getValue() != null)
            && (loanPeriod.getValue() != null && loanPeriod.getValue() > 0)
            && (loanAmount != null && loanAmount.getValue() > 0)) {
      calculateButton.setEnabled(true);
    }
  }
}
