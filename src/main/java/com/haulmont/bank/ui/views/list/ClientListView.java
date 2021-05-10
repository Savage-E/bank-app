package com.haulmont.bank.ui.views.list;

import com.haulmont.bank.core.models.Client;
import com.haulmont.bank.core.services.ClientService;
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

/**
 * Created by Vlad Kotov
 * Date: 08.май.2021
 * Time:  23:39
 * Project: bank-app
 * Description:
 */
@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Clients | Bank app")
public class ClientListView extends VerticalLayout {
  ClientForm form;
  Grid<Client> grid = new Grid<>(Client.class);
  TextField filterText = new TextField();

  ClientService clientService;

  @Autowired
  public ClientListView(ClientService clientService) {
    this.clientService = clientService;
    addClassName("client-list-view");
    setSizeFull();
    configureGrid();

    form = new ClientForm(clientService.getAll());
    form.addListener(ClientForm.SaveEvent.class, this::saveClient);
    form.addListener(ClientForm.DeleteEvent.class, this::deleteClient);
    form.addListener(ClientForm.CancelEvent.class, e -> closeEditor());
    Div content = new Div(grid, form);
    content.addClassName("client-content");
    content.setSizeFull();

    add(getToolBar(), content);
    updateList();
    closeEditor();
  }

  private void deleteClient(ClientForm.DeleteEvent evt) {
    clientService.delete(evt.getClient());
    updateList();
    closeEditor();
  }

  private HorizontalLayout getToolBar() {
    filterText.setPlaceholder("Find by fio...");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);

    filterText.addValueChangeListener(e -> updateList());

    Button addClientButton = new Button("Add Client", click -> addClient());
    HorizontalLayout toolbar = new HorizontalLayout(filterText, addClientButton);
    toolbar.addClassName("client-toolbar");
    return toolbar;
  }

  private void closeEditor() {
    form.setClient(null);
    form.setVisible(false);
    removeClassName("client-editing");
  }

  private void addClient() {
    grid.asSingleSelect().clear();
    editClient(new Client());
  }

  private void configureGrid() {
    grid.addClassName("client-grid");
    grid.setSizeFull();
    grid.setColumns("clientId", "fio", "phoneNumber", "email", "passportNum");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));

    grid.asSingleSelect().addValueChangeListener(evt -> editClient(evt.getValue()));
  }

  private void editClient(Client client) {
    if (client == null) {
      closeEditor();
    } else {
      form.setClient(client);
      form.setVisible(true);
      addClassName("client-editing");
    }
  }

  private void saveClient(ClientForm.SaveEvent evt) {
    clientService.save(evt.getClient());
    updateList();
    closeEditor();
  }

  private void updateList() {
    if (!filterText.getValue().equals("")) {
      grid.setItems(clientService.get(filterText.getValue()));
    } else {
      grid.setItems(clientService.getAll());
    }
  }

}
