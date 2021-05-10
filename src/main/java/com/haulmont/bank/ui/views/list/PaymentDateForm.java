package com.haulmont.bank.ui.views.list;

import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.models.Credit;
import com.haulmont.bank.core.models.CreditOffer;
import com.haulmont.bank.core.models.PaymentDate;
import com.haulmont.bank.ui.util.DateConverter;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 10.май.2021
 * Time:  16:09
 * Project: bank-app
 * Description:
 */
public class PaymentDateForm extends FormLayout {

  TextField date = new TextField("date");
  NumberField amount = new NumberField("amount");
  NumberField bodyRepaymentAmount = new NumberField("body Repayment Amount");
  NumberField interestRepaymentAmount = new NumberField("interest Repayment Amount");
  ComboBox<Credit> credit = new ComboBox<>("credit");
  ComboBox<Client> client = new ComboBox<>("client");
  List<Credit> credits = new ArrayList<>();
  List<Client> clients = new ArrayList<>();

  Binder<PaymentDate> binder = new BeanValidationBinder<>(PaymentDate.class);

  Button save = new Button("Save");
  Button cancel = new Button("Cancel");
  Button delete = new Button("Delete");

  private PaymentDate paymentDate;

  public PaymentDateForm(List<CreditOffer> creditOffers) {
    addClassName("payday-form");

    for (CreditOffer offer : creditOffers) {
      credits.add(offer.getCredit());
      clients.add(offer.getClient());
    }
    binder.forField(date).withConverter(new DateConverter()).bind(PaymentDate::getDate, PaymentDate::setDate);
    binder.bindInstanceFields(this);
    credit.setItems(credits);
    credit.setItemLabelGenerator(Credit::getCreditIdString);
    client.setItems(clients);
    client.setItemLabelGenerator(Client::getClientIdString);
    add(

            date,
            amount,
            bodyRepaymentAmount,
            interestRepaymentAmount,
            credit,
            client,
            save,
            delete,
            cancel,
            createButtonsLayout()
    );
  }

  public void setPaymentDate(PaymentDate paymentDate) {
    this.paymentDate = paymentDate;
    binder.readBean(paymentDate);
  }


  private Component createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    cancel.addClickShortcut(Key.ESCAPE);

    save.addClickListener(click -> validateAndSave());

    delete.addClickListener(click ->
            fireEvent(new PaymentDateForm.DeleteEvent(this, paymentDate, client.getValue(), credit.getValue())));
    cancel.addClickListener(click -> fireEvent(new PaymentDateForm.CancelEvent(this)));

    binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, cancel);
  }

  private void validateAndSave() {

    try {
      binder.writeBean(paymentDate);

      fireEvent(new PaymentDateForm.SaveEvent(this, paymentDate, credit.getValue(), client.getValue()));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }

  public static abstract class PaymentDateFormEvent extends ComponentEvent<PaymentDateForm> {
    private PaymentDate paymentDate;
    private Client clientEntity;
    private Credit creditEntity;

    protected PaymentDateFormEvent(PaymentDateForm source, PaymentDate paymentDate, Credit creditId,
                                   Client clientId) {
      super(source, false);
      this.paymentDate = paymentDate;
      this.clientEntity = clientId;
      this.creditEntity = creditId;
    }

    protected PaymentDateFormEvent(PaymentDateForm source, PaymentDate paymentDate) {
      super(source, false);
      this.paymentDate = paymentDate;
    }

    public Client getClientEntity() {
      return clientEntity;
    }

    public Credit getCreditEntity() {
      return creditEntity;
    }

    public PaymentDate getPaymentDate() {
      return paymentDate;
    }
  }

  public static class SaveEvent extends PaymentDateFormEvent {
    SaveEvent(PaymentDateForm source, PaymentDate paymentDate, Credit creditId, Client clientId) {
      super(source, paymentDate, creditId, clientId);
    }
  }

  public static class DeleteEvent extends PaymentDateFormEvent {
    DeleteEvent(PaymentDateForm source, PaymentDate paymentDate, Client clientId, Credit creditId) {
      super(source, paymentDate, creditId, clientId);
    }
  }

  public static class CancelEvent extends PaymentDateFormEvent {
    CancelEvent(PaymentDateForm source) {
      super(source, null);
    }
  }

}
