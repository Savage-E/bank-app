package com.haulmont.bank.ui;

import com.haulmont.bank.ui.views.LoanView;
import com.haulmont.bank.ui.views.list.ClientListView;
import com.haulmont.bank.ui.views.list.CreditListView;
import com.haulmont.bank.ui.views.list.CreditOfferListView;
import com.haulmont.bank.ui.views.list.PaymentDateListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

/**
 * Created by Vlad Kotov
 * Date: 05.май.2021
 * Time:  11:17
 * Project: bank-app
 * Description:
 */

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {


  public MainLayout() {
    createHeader();
    createDrawer();
  }


  private void createHeader() {
    H1 logo = new H1("Bank app");
    logo.addClassName("logo");
    Anchor logout = new Anchor("/logout", "Log out");

    HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
    header.addClassName("header");
    header.setWidth("100%");
    header.expand(logo);
    header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    addToNavbar(header);
  }

  private void createDrawer() {
    RouterLink listLinkCreditList = new RouterLink("See credits", CreditListView.class);
    listLinkCreditList.setHighlightCondition(HighlightConditions.sameLocation());
    RouterLink listLinkClientList = new RouterLink("See clients", ClientListView.class);
    listLinkClientList.setHighlightCondition(HighlightConditions.sameLocation());

    RouterLink listLinkCreditOfferList = new RouterLink("See credit offers", CreditOfferListView.class);
    listLinkClientList.setHighlightCondition(HighlightConditions.sameLocation());

    RouterLink listLinkPaymentDayList = new RouterLink("See payment dates", PaymentDateListView.class);
    listLinkClientList.setHighlightCondition(HighlightConditions.sameLocation());

    RouterLink listLinkAddLoan = new RouterLink("Add new credit", LoanView.class);
    listLinkClientList.setHighlightCondition(HighlightConditions.sameLocation());

    addToDrawer(new VerticalLayout(
            listLinkCreditList,
            listLinkClientList,
            listLinkCreditOfferList,
            listLinkPaymentDayList,
            listLinkAddLoan
    ));
  }

}
