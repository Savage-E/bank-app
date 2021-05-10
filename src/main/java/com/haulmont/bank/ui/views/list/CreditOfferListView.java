package com.haulmont.bank.ui.views.list;

import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.models.Credit;
import com.haulmont.bank.core.models.CreditOffer;
import com.haulmont.bank.core.services.ClientService;
import com.haulmont.bank.core.services.CreditOfferService;
import com.haulmont.bank.core.services.CreditService;
import com.haulmont.bank.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 10.май.2021
 * Time:  13:50
 * Project: bank-app
 * Description:
 */
@Component
@Scope("prototype")
@Route(value = "offers", layout = MainLayout.class)
@PageTitle("Credit offers | Bank app")
public class CreditOfferListView extends VerticalLayout {

  private static final Logger LOGGER = Logger.getLogger(CreditOfferListView.class);
  CreditOfferForm form;
  Grid<CreditOffer> grid = new Grid<>(CreditOffer.class);

  CreditOfferService creditOfferService;
  TextField filterTextClient = new TextField();
  CreditService creditService;
  ClientService clientService;
  TextField filterTextCredit = new TextField();

  @Autowired
  public CreditOfferListView(CreditOfferService creditOfferService, ClientService clientService,
                             CreditService creditService) {
    this.creditOfferService = creditOfferService;
    this.creditService = creditService;
    this.clientService = clientService;
    addClassName("credit-offer-list-view");
    setSizeFull();
    configureGrid();

    form = new CreditOfferForm(creditService.getAll(), clientService.getAll());
    form.addListener(CreditOfferForm.SaveEvent.class, this::saveCreditOffer);
    form.addListener(CreditOfferForm.DeleteEvent.class, this::deleteCreditOffer);
    form.addListener(CreditOfferForm.CancelEvent.class, e -> closeEditor());


    Div content = new Div(grid, form);
    content.addClassName("credit-offer-content");
    content.setSizeFull();

    add(getToolBar(), content);
    updateList();
    closeEditor();
  }

  private void deleteCreditOffer(CreditOfferForm.DeleteEvent evt) {
    CreditOffer creditOffer = evt.getCreditOffer();
    creditOffer.setCredit(evt.getCreditEntity());
    creditOffer.setClient(evt.getClientEntity());
    creditOfferService.delete(creditOffer);
    updateList();
    closeEditor();
  }

  private void saveCreditOffer(CreditOfferForm.SaveEvent evt) {
    CreditOffer creditOffer = evt.getCreditOffer();
    Client client = evt.getClientEntity();
    Credit credit = evt.getCreditEntity();
    creditOfferService.save(creditOffer, client, credit);
    updateList();
    closeEditor();
  }

  private HorizontalLayout getToolBar() {
    filterTextClient.setPlaceholder("Filter by client id...");
    filterTextClient.setClearButtonVisible(true);
    filterTextClient.setValueChangeMode(ValueChangeMode.LAZY);
    filterTextClient.addValueChangeListener(e -> updateList());

    filterTextCredit.setPlaceholder("Filter by credit id...");
    filterTextCredit.setClearButtonVisible(true);
    filterTextCredit.setValueChangeMode(ValueChangeMode.LAZY);
    filterTextCredit.addValueChangeListener(e -> updateList());

    Button addCreditOfferButton = new Button("Add credit offer", click -> addCreditOffer());

    HorizontalLayout toolbar = new HorizontalLayout(filterTextClient, filterTextCredit, addCreditOfferButton);
    toolbar.addClassName("credit-offer-toolbar");
    return toolbar;
  }

  private void addCreditOffer() {
    grid.asSingleSelect().clear();
    editCreditOffer(new CreditOffer());
  }

  private void configureGrid() {
    grid.addClassName("credit-offer-grid");
    grid.setSizeFull();
    grid.setColumns("loanAmount");

    grid.addColumn(creditOffer -> {
      Credit credit = creditOffer.getCredit();
      return credit == null ? "-" : creditOffer.getCreditId();
    }).setHeader("Credit");

    grid.addColumn(creditOffer -> {
      Client client = creditOffer.getClient();
      return client == null ? "-" : creditOffer.getClientId();
    }).setHeader("Client");

    grid.getColumns().forEach(col -> col.setAutoWidth(true));

    grid.asSingleSelect().addValueChangeListener(evt -> editCreditOffer(evt.getValue()));
  }

  private void editCreditOffer(CreditOffer creditOffer) {
    if (creditOffer == null) {
      closeEditor();
    } else {
      form.setCreditOffer(creditOffer);
      form.setVisible(true);
      addClassName("credit-offer-editing");
    }
  }

  private void closeEditor() {
    form.setCreditOffer(null);
    form.setVisible(false);
    removeClassName("credit-offer-editing");
  }

  private void updateList() {
    if (!filterTextClient.getValue().equals("")) {
      try {
        UUID uuid = UUID.fromString(filterTextClient.getValue());
        grid.setItems(creditOfferService.get(uuid));

      } catch (IllegalArgumentException ex) {
        filterTextClient.setErrorMessage("Get valid Uuid");
      }
    } else if (!filterTextCredit.getValue().equals("")) {
      try {


        UUID uuid = UUID.fromString(filterTextClient.getValue());
        grid.setItems(creditOfferService.get(uuid));
      } catch (IllegalArgumentException ex) {
        filterTextCredit.setErrorMessage("Get valid Uuid");
      }
    } else {
      grid.setItems(creditOfferService.getAll());
    }
  }

}
