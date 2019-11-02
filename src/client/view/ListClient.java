package client.view;

import View.TableActions;
import View.TableHead;
import client.manager.ClientManager;
import client.model.Client;
import client.manager.ImplClientManager;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import personne.model.Personne;
import personne.view.ListPersonne;

import java.util.Collection;
import java.util.List;


public class ListClient extends ListPersonne {

    ImplClientManager clientManager = ImplClientManager.getClientManager();
    protected JFXTreeTableColumn<Personne, String> faxColumn;
    private static ListClient listCat;

    public static ListClient getListClient() {
        if (listCat == null)
            listCat = new ListClient();
        return (ListClient) listCat;
    }


    private ListClient() {
        tableHead = new TableHead("Liste des Clients");


        tablePersonne = new JFXTreeTableView<Personne>(new RecursiveTreeItem<Personne>(clientManager.get(), RecursiveTreeObject::getChildren));
        tablePersonne.setShowRoot(false);
        tablePersonne.setEditable(true);

        tablePersonne.getColumns().setAll(getCodeColumn(), getNomColumn(), getAdresseColumn(), getTeleColumn(), getMailColumn(), getActionColumn());
        tablePersonne.rowFactoryProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("" + newValue);
        });
        tableHead.getSearch().textProperty().addListener((observable, oldValue, newValue) -> {
//            personnes.setAll(clientManager.chercherParNom(newValue));
            if (newValue != null)
                tablePersonne.setPredicate(personneTreeItem -> personneTreeItem.getValue().getNom().contains(newValue));
        });


        tablePersonne.setPrefHeight(700);
        setSpacing(20);
        setAlignment(Pos.CENTER);
        getChildren().add(tableHead);
        getChildren().add(tablePersonne);
      /*  TreeTableRow<Client> jFXTreeTableRow = new JFXTreeTableRow<Client>();
        jFXTreeTableRow.startEdit();
        tableClient.get*/
        getStyleClass().add("listCat");
    }

    public JFXTreeTableColumn<Personne, Personne> getActionColumn() {
        if (actionColumn == null) {
            actionColumn = new JFXTreeTableColumn<>("Actions");
            actionColumn.prefWidthProperty().bind(tablePersonne.widthProperty().divide(100 / 15.80));
            actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue()));
            actionColumn.setCellFactory(param -> new JFXTreeTableCell<Personne, Personne>() {
                TableActions tableActions = new TableActions();

                @Override
                public void startEdit() {
                    System.out.println("startEdit");
                }

                @Override
                public void commitEdit(Personne newValue) {
                    System.out.println("commitEdit");
                }

                @Override
                public void cancelEdit() {
                    System.out.println("cancelEdit");
                }

                @Override
                protected void updateItem(Personne item, boolean empty) {
//                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }

                    setGraphic(tableActions);
//                    setGraphic(new JFXButton("Ajouter"));
                    tableActions.getUpdateButton().setOnMouseClicked(event -> {
                        Ajout.getAjout().setFields((Client) item);
                        Container.getContainer().jfxTabPane.getSelectionModel().select(1);

                    });
                    tableActions.getDeleteButton().setOnAction(
                            event -> {
                                clientManager.deleteById(tableActions.getDeleteButton(), (Client) item);
                            }
                    );
                }
            });
        }
        return actionColumn;
    }


}
