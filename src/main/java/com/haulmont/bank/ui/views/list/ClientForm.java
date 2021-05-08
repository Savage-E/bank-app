package com.haulmont.bank.ui.views.list;

import com.haulmont.bank.core.models.Client;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

/**
 * Created by Vlad Kotov
 * Date: 08.май.2021
 * Time:  23:52
 * Project: bank-app
 * Description:
 */

public class ClientForm extends FormLayout {

  TextField fio = new TextField("fio");
  IntegerField phoneNumber = new IntegerField("phone number");
  EmailField email = new EmailField("email");

  IntegerField passportNum = new IntegerField("passport number");
  Button save = new Button("Save");
  Button cancel = new Button("Cancel");
  Button delete = new Button("Delete");
  Binder<Client> binder = new BeanValidationBinder<>(Client.class);
  private Client client;

  public ClientForm(List<Client> clients) {
    addClassName("client-form");
    binder.bindInstanceFields(this);

    email.getElement().setAttribute("name", "email");
    email.setPlaceholder("username@example.com");
    email.setErrorMessage("Please enter a valid example.com email address");
    email.setClearButtonVisible(true);
    email.setPattern("^.+@example\\.com$");

    add(
            fio,
            phoneNumber,
            email,
            passportNum,
            save,
            delete,
            cancel,
            createButtonsLayout()
    );
  }

  public void setClient(Client client) {
    this.client = client;
    binder.readBean(client);
  }

  private Component createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);


//    save.addClickShortcut(Key.ENTER);
    cancel.addClickShortcut(Key.ESCAPE);

    save.addClickListener(click -> validateAndSave());
    delete.addClickListener(click -> fireEvent(new ClientForm.DeleteEvent(this, client)));

    cancel.addClickListener(click -> fireEvent(new ClientForm.CancelEvent(this)));

    binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, cancel);
  }

  private void validateAndSave() {

    try {
      binder.writeBean(client);
      fireEvent(new ClientForm.SaveEvent(this, client));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }

  public static abstract class ClientFormEvent extends ComponentEvent<ClientForm> {
    private Client client;

    protected ClientFormEvent(ClientForm source, Client client) {
      super(source, false);
      this.client = client;
    }

    public Client getClient() {
      return client;
    }
  }

  public static class SaveEvent extends ClientForm.ClientFormEvent {
    SaveEvent(ClientForm source, Client client) {
      super(source, client);
    }
  }

  public static class DeleteEvent extends ClientForm.ClientFormEvent {
    DeleteEvent(ClientForm source, Client client) {
      super(source, client);
    }

  }

  public static class CancelEvent extends ClientForm.ClientFormEvent {
    CancelEvent(ClientForm source) {
      super(source, null);
    }

  }
}

