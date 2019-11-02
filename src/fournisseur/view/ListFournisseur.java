package fournisseur.view;

import View.TableActions;
import View.TableHead;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fournisseur.manager.FournisseurManager;
import fournisseur.manager.FournisseurManager;
import fournisseur.manager.ImplFournisseurManager;
import fournisseur.manager.ImplFournisseurManager;
import fournisseur.model.Fournisseur;
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


public class ListFournisseur extends ListPersonne {


    ImplFournisseurManager fournisseurManager = ImplFournisseurManager.getFournisseurManager();
    protected JFXTreeTableColumn<Personne, String> faxColumn;

    protected static ListFournisseur listFournisseur;

    public static ListFournisseur getListFournisseur() {
        if (listFournisseur == null)
            listFournisseur = new ListFournisseur();
        return (ListFournisseur) listFournisseur;
    }


    private ListFournisseur() {
        tableHead = new TableHead("Liste des Fournisseurs");


        tablePersonne = new JFXTreeTableView<Personne>(new RecursiveTreeItem<Personne>(fournisseurManager.get(), RecursiveTreeObject::getChildren));
        tablePersonne.setShowRoot(false);
        tablePersonne.setEditable(true);

        tablePersonne.getColumns().setAll(getCodeColumn(), getNomColumn(), getAdresseColumn(), getTeleColumn(), getMailColumn(), getFaxColumn(), getActionColumn());
        tablePersonne.rowFactoryProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("" + newValue);
        });
        tableHead.getSearch().textProperty().addListener((observable, oldValue, newValue) -> {
//            personnes.setAll(fournisseurManager.chercherParNom(newValue));
            if (newValue != null)
                tablePersonne.setPredicate(personneTreeItem -> personneTreeItem.getValue().getNom().contains(newValue));
        });

        tablePersonne.setPrefHeight(700);

        setSpacing(20);
        setAlignment(Pos.CENTER);
        getChildren().add(tableHead);
        getChildren().add(tablePersonne);
      /*  TreeTableRow<Fournisseur> jFXTreeTableRow = new JFXTreeTableRow<Fournisseur>();
        jFXTreeTableRow.startEdit();
        tableFournisseur.get*/
        getStyleClass().add("listCat");
    }

    public JFXTreeTableColumn<Personne, String> getFaxColumn() {
        if (faxColumn == null) {
            faxColumn = new JFXTreeTableColumn<>("Fax");
            faxColumn.prefWidthProperty().bind(tablePersonne.widthProperty().divide(100 / 11));

            faxColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("fax"));

        }
        return faxColumn;
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
                        Ajout.getAjout().vider();
                        Ajout.getAjout().setFields((Fournisseur) item);
//                        Container.getContainer().setCenter(Ajout.getAjout());
                        Container.getContainer().jfxTabPane.getSelectionModel().select(1);

                    });
                    tableActions.getDeleteButton().setOnAction(
                            event -> {
                                fournisseurManager.deleteById(tableActions.getDeleteButton(), (Fournisseur) item);
                            }
                    );
                }
            });
        }
        return actionColumn;
    }


}
