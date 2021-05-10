package com.haulmont.bank.ui.views.list;

import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.models.Credit;
import com.haulmont.bank.core.models.CreditOffer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 09.май.2021
 * Time:  12:16
 * Project: bank-app
 * Description:
 */
public class CreditOfferForm extends FormLayout {

  NumberField loanAmount = new NumberField("loan amount");
  ComboBox<Client> client = new ComboBox<>("client");
  ComboBox<Credit> credit = new ComboBox<>("credit");
  Binder<CreditOffer> binder = new BeanValidationBinder<>(CreditOffer.class);

  Button save = new Button("Save");
  Button cancel = new Button("Cancel");
  Button delete = new Button("Delete");

  private CreditOffer creditOffer;

  public CreditOfferForm(List<Credit> credits, List<Client> clients) {
    addClassName("credit-offer-form");

    binder.bindInstanceFields(this);
    credit.setItems(credits);
    credit.setItemLabelGenerator(Credit::getCreditIdString);
    client.setItems(clients);
    client.setItemLabelGenerator(Client::getClientIdString);

    add(
            client,
            credit,
            loanAmount,
            save,
            delete,
            cancel,
            createButtonsLayout()
    );
  }

  public void setCreditOffer(CreditOffer creditOffer) {
    this.creditOffer = creditOffer;
    binder.readBean(creditOffer);
  }

  private Component createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    cancel.addClickShortcut(Key.ESCAPE);

    save.addClickListener(click -> validateAndSave());


    delete.addClickListener(click ->
            fireEvent(new DeleteEvent(this, creditOffer, client.getValue(), credit.getValue())));
    cancel.addClickListener(click -> fireEvent(new CancelEvent(this)));

    binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, cancel);
  }

  private void validateAndSave() {

    try {
      binder.writeBean(creditOffer);

      fireEvent(new SaveEvent(this, creditOffer, credit.getValue(), client.getValue()));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }

  public static abstract class CreditOfferFormEvent extends ComponentEvent<CreditOfferForm> {
    private CreditOffer creditOffer;
    private Client clientEntity;
    private Credit creditEntity;

    protected CreditOfferFormEvent(CreditOfferForm source, CreditOffer creditOffer, Credit creditId,
                                   Client clientId) {
      super(source, false);
      this.creditOffer = creditOffer;
      this.clientEntity = clientId;
      this.creditEntity = creditId;
    }

    protected CreditOfferFormEvent(CreditOfferForm source, CreditOffer creditOffer) {
      super(source, false);
      this.creditOffer = creditOffer;
    }

    public Client getClientEntity() {
      return clientEntity;
    }

    public Credit getCreditEntity() {
      return creditEntity;
    }

    public CreditOffer getCreditOffer() {
      return creditOffer;
    }
  }

  public static class SaveEvent extends CreditOfferFormEvent {
    SaveEvent(CreditOfferForm source, CreditOffer creditOffer, Credit creditId, Client clientId) {
      super(source, creditOffer, creditId, clientId);
    }
  }

  public static class DeleteEvent extends CreditOfferFormEvent {
    DeleteEvent(CreditOfferForm source, CreditOffer creditOffer, Client clientId, Credit creditId) {
      super(source, creditOffer, creditId, clientId);
    }

  }

  public static class CancelEvent extends CreditOfferFormEvent {
    CancelEvent(CreditOfferForm source) {
      super(source, null);
    }
  }

}
