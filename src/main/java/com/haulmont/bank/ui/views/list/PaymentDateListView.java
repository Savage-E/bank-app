package com.haulmont.bank.ui.views.list;

import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.models.Credit;
import com.haulmont.bank.core.models.PaymentDate;
import com.haulmont.bank.core.services.CreditOfferService;
import com.haulmont.bank.core.services.PaymentDateService;
import com.haulmont.bank.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Vlad Kotov
 * Date: 10.май.2021
 * Time:  17:46
 * Project: bank-app
 * Description:
 */
@Component
@Scope("prototype")
@Route(value = "payday", layout = MainLayout.class)
@PageTitle("Payment date | Bank app")
public class PaymentDateListView extends VerticalLayout {

  PaymentDateForm form;
  Grid<PaymentDate> grid = new Grid<>(PaymentDate.class);

  PaymentDateService paymentDateService;
  /*TextField filterTextClient = new TextField();
  TextField filterTextCredit = new TextField();
  */
  CreditOfferService creditOfferService;

  @Autowired
  public PaymentDateListView(PaymentDateService paymentDateService, CreditOfferService creditOfferService) {
    this.paymentDateService = paymentDateService;
    this.creditOfferService = creditOfferService;
    addClassName("payday-list-view");
    setSizeFull();
    configureGrid();

    form = new PaymentDateForm(creditOfferService.getAll());
    form.addListener(PaymentDateForm.SaveEvent.class, this::savePayday);
    form.addListener(PaymentDateForm.DeleteEvent.class, this::deletePayday);
    form.addListener(PaymentDateForm.CancelEvent.class, e -> closeEditor());


    Div content = new Div(grid, form);
    content.addClassName("payday-content");
    content.setSizeFull();

    add(getToolBar(), content);
    updateList();
    closeEditor();
  }

  private void deletePayday(PaymentDateForm.DeleteEvent evt) {
    PaymentDate paymentDate = evt.getPaymentDate();
    Client client = evt.getClientEntity();
    Credit credit = evt.getCreditEntity();
    paymentDateService.delete(paymentDate, credit, client);
    updateList();
    closeEditor();
  }


  private void savePayday(PaymentDateForm.SaveEvent evt) {
    PaymentDate paymentDate = evt.getPaymentDate();
    Client client = evt.getClientEntity();
    Credit credit = evt.getCreditEntity();
    paymentDateService.save(paymentDate, credit, client);
    updateList();
    closeEditor();
  }

  //TODO: add filter to PaymentDate view.
  private HorizontalLayout getToolBar() {
 /*   filterTextClient.setPlaceholder("Filter by client id...");
    filterTextClient.setClearButtonVisible(true);
    filterTextClient.setValueChangeMode(ValueChangeMode.LAZY);
    filterTextClient.addValueChangeListener(e -> updateList());

    filterTextCredit.setPlaceholder("Filter by credit id...");
    filterTextCredit.setClearButtonVisible(true);
    filterTextCredit.setValueChangeMode(ValueChangeMode.LAZY);
    filterTextCredit.addValueChangeListener(e -> updateList());*/

    Button addPaymentDateButton = new Button("Add payment date ", click -> addPayday());

    HorizontalLayout toolbar = new HorizontalLayout(addPaymentDateButton);
    toolbar.addClassName("payday-toolbar");
    return toolbar;
  }

  private void addPayday() {
    grid.asSingleSelect().clear();
    editPayday(new PaymentDate());
  }

  private void configureGrid() {
    grid.addClassName("payday-grid");
    grid.setSizeFull();

    grid.setColumns("id", "date", "amount", "bodyRepaymentAmount", "interestRepaymentAmount");
    grid.addColumn(paymentDate -> {
      Credit credit = paymentDate.getCreditOffer().getCredit();
      return credit == null ? "-" : paymentDate.getCreditOffer().getCreditId();
    }).setHeader("Credit");

    grid.addColumn(paymentDate -> {
      Client client = paymentDate.getCreditOffer().getClient();
      return client == null ? "-" : paymentDate.getCreditOffer().getClientId();
    }).setHeader("Client");

    grid.getColumns().forEach(col -> col.setAutoWidth(true));

    grid.asSingleSelect().addValueChangeListener(evt -> editPayday(evt.getValue()));
  }

  private void editPayday(PaymentDate paymentDate) {
    if (paymentDate == null) {
      closeEditor();
    } else {
      form.setPaymentDate(paymentDate);
      form.setVisible(true);
      addClassName("payday-editing");
    }
  }

  private void closeEditor() {
    form.setPaymentDate(null);
    form.setVisible(false);
    removeClassName("payday-editing");
  }


  private void updateList() {

    grid.setItems(paymentDateService.getAll());

  }

}
