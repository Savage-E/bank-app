package com.haulmont.bank;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Created by Vlad Kotov
 * Date: 01.май2021
 * Time:  13:26
 * Project: bank-app
 * Description:
 */
@Route
public class Vaadin extends VerticalLayout {
  public Vaadin() {
    add(new Button("Click me", e -> Notification.show("Hello, Spring+Vaadin user!")));
  }
}