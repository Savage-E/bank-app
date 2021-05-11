package com.haulmont.bank.ui.views.list;

import com.haulmont.bank.core.models.Credit;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Vlad Kotov
 * Date: 08.май.2021
 * Time:  20:12
 * Project: bank-app
 * Description:
 */
@Component
@Scope("prototype")
@Route(value = "credits", layout = MainLayout.class)
@PageTitle("Credits | Bank app")
public class CreditListView extends VerticalLayout {
  CreditForm form;
  Grid<Credit> grid = new Grid<>(Credit.class);
  TextField filterText = new TextField();


  CreditService creditService;

  @Autowired
  public CreditListView(CreditService creditService) {
    this.creditService = creditService;
    addClassName("credit-list-view");
    setSizeFull();
    configureGrid();


    form = new CreditForm(creditService.getAll());
    form.addListener(CreditForm.SaveEvent.class, this::saveCredit);
    form.addListener(CreditForm.DeleteEvent.class, this::deleteCredit);
    form.addListener(CreditForm.CancelEvent.class, e -> closeEditor());


    Div content = new Div(grid, form);
    content.addClassName("credit-content");
    content.setSizeFull();

    add(getToolBar(), content);
    updateList();
    closeEditor();
  }

  private void deleteCredit(CreditForm.DeleteEvent evt) {
    creditService.delete(evt.getCredit());
    updateList();
    closeEditor();
  }

  private HorizontalLayout getToolBar() {
    filterText.setPlaceholder("Find by id...");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);

    filterText.addValueChangeListener(e -> updateList());

    Button addCreditButton = new Button("Add credit", click -> addCredit());
    HorizontalLayout toolbar = new HorizontalLayout(filterText, addCreditButton);
    toolbar.addClassName("credit-toolbar");
    return toolbar;
  }

  private void closeEditor() {
    form.setCredit(null);
    form.setVisible(false);
    removeClassName("credit-editing");
  }

  private void addCredit() {
    grid.asSingleSelect().clear();
    editCredit(new Credit());
  }

  private void configureGrid() {
    grid.addClassName("credit-grid");
    grid.setSizeFull();
    grid.setColumns("creditId", "limit", "interestRate");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));

    grid.asSingleSelect().addValueChangeListener(evt -> editCredit(evt.getValue()));
  }

  private void editCredit(Credit credit) {
    if (credit == null) {
      closeEditor();
    } else {
      form.setCredit(credit);
      form.setVisible(true);
      addClassName("credit-editing");
    }
  }


  private void saveCredit(CreditForm.SaveEvent evt) {
    creditService.save(evt.getCredit());
    updateList();
    closeEditor();
  }


  private void updateList() {
    if (!filterText.getValue().equals("")) {
      try {
        UUID uuid = UUID.fromString(filterText.getValue());
        grid.setItems(creditService.get(uuid));

      } catch (IllegalArgumentException ex) {

      }
    } else {
      grid.setItems(creditService.getAll());
    }
  }
}
