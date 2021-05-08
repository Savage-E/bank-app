package com.haulmont.bank.ui.views.list;

import com.haulmont.bank.core.models.Credit;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 08.май.2021
 * Time:  20:16
 * Project: bank-app
 * Description:
 */
public class CreditForm extends FormLayout {


  IntegerField limit = new IntegerField("limit");
  IntegerField interestRate = new IntegerField("interest rate");
  Button save = new Button("Save");
  Button cancel = new Button("Cancel");
  Button delete = new Button("Delete");

  Binder<Credit> binder = new BeanValidationBinder<>(Credit.class);
  private Credit credit;

  public CreditForm(List<Credit> credits) {
    addClassName("credit-form");

    binder.bindInstanceFields(this);

    add(
            limit,
            interestRate,
            save,
            delete,
            cancel,
            createButtonsLayout()
    );
  }

  public void setCredit(Credit credit) {
    this.credit = credit;
    binder.readBean(credit);
  }

  private Component createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);


   // save.addClickShortcut(Key.ENTER);
    cancel.addClickShortcut(Key.ESCAPE);

    save.addClickListener(click -> validateAndSave());
    delete.addClickListener(click -> fireEvent(new DeleteEvent(this, credit)));

    cancel.addClickListener(click -> fireEvent(new CancelEvent(this)));

    binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, cancel);
  }

  private void validateAndSave() {

    try {
      binder.writeBean(credit);
      fireEvent(new SaveEvent(this, credit));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }

  public static abstract class CreditFormEvent extends ComponentEvent<CreditForm> {
    private Credit credit;

    protected CreditFormEvent(CreditForm source, Credit credit) {
      super(source, false);
      this.credit = credit;
    }

    public Credit getCredit() {
      return credit;
    }
  }


  public static class SaveEvent extends CreditFormEvent {
    SaveEvent(CreditForm source, Credit credit) {
      super(source, credit);
    }
  }

  public static class DeleteEvent extends CreditFormEvent {
    DeleteEvent(CreditForm source, Credit credit) {
      super(source, credit);
    }

  }

  public static class CancelEvent extends CreditFormEvent {
    CancelEvent(CreditForm source) {
      super(source, null);
    }

  }

}


