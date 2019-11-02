package commande.achat.view;

import View.TableActions;

import commande.achat.manager.AchatManagerImpl;
import commande.achat.model.Achat;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import commande.models.Commande;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;


public class ListAchat extends BorderPane {
    public JFXTreeTableView<Commande> tableCommande;
    JFXTextField search = new JFXTextField();
    JFXTreeTableColumn<Commande, Integer> idColumn;
    JFXTreeTableColumn<Commande, Long> codeFournisseurColumn;
    JFXTreeTableColumn<Commande, String> quantiteColumn;
    JFXTreeTableColumn<Commande, Double> totalColumn;
    JFXTreeTableColumn<Commande, Long> codeProduitColumn;
    JFXTreeTableColumn<Commande, String> designationColumn;
    JFXTreeTableColumn<Commande, Commande> actionColumn;

    JFXTreeTableColumn<Commande, String> nomFournisseurColumn;
    JFXTreeTableColumn<Commande, String> dateColumn;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    AchatManagerImpl achatManager = AchatManagerImpl.getAchatManager();


    private static ListAchat listCat;

    public static ListAchat getListAchat() {
        if (listCat == null)
            listCat = new ListAchat();
        return listCat;
    }


    public ListAchat() {
        Label label = new Label("Liste des achats");
        label.getStyleClass().add("titre");

        tableCommande = new JFXTreeTableView<Commande>(new RecursiveTreeItem<Commande>(achatManager.get(), RecursiveTreeObject::getChildren));
        tableCommande.setShowRoot(false);
        tableCommande.setEditable(true);

        tableCommande.getColumns().setAll(getIdColumn(), getDateColumn(), getQuantite(), getTotalColumn(),
                getCodeFournisseurColumn(), getNomFournisseurColumn(), getCodeProduitColumn(), getDesProduitColumn(), getActionColumn());
        tableCommande.rowFactoryProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("" + newValue);
        });
       /* search.textProperty().addListener((observable, oldValue, newValue) -> {
            achats.setAll(achatManager.getByLibelle(newValue));
        });*/

        tableCommande.setPrefHeight(700);
        getChildren().add(search);

        VBox vBox = new VBox(label, search);
        vBox.setPadding(new Insets(10));

        vBox.getStyleClass().add("vBoxsearch");
        vBox.setAlignment(Pos.CENTER);
        setPadding(new Insets(30));
        tableCommande.setMaxWidth(1100);

        this.setTop(vBox);
        this.setCenter(tableCommande);
      /*  TreeTableRow<Commande> jFXTreeTableRow = new JFXTreeTableRow<Commande>();
        jFXTreeTableRow.startEdit();
        tableCommande.get*/
        getStyleClass().add("listCat");
    }


    public JFXTreeTableColumn<Commande, Integer> getIdColumn() {
        if (idColumn == null) {
            idColumn = new JFXTreeTableColumn<>("Id");
            idColumn.setPrefWidth(30);

            idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));

        }
        return idColumn;
    }


    public JFXTreeTableColumn<Commande, String> getQuantite() {
        if (quantiteColumn == null) {
            quantiteColumn = new JFXTreeTableColumn<>("quantite");
            quantiteColumn.setPrefWidth(100);
            quantiteColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantite"));

//            libelleColumn.setCellValueFactory(p -> p.getValue().getValue().libelleProperty());
        }
        return quantiteColumn;
    }

    public JFXTreeTableColumn<Commande, Double> getTotalColumn() {
        if (totalColumn == null) {
            totalColumn = new JFXTreeTableColumn<>("Total");
            totalColumn.setPrefWidth(120);

            totalColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("total"));

        }
        return totalColumn;
    }

    public JFXTreeTableColumn<Commande, Long> getCodeFournisseurColumn() {
        if (codeFournisseurColumn == null) {
            codeFournisseurColumn = new JFXTreeTableColumn<>("Code Fournisseur");
            codeFournisseurColumn.setPrefWidth(120);

            codeFournisseurColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getPersonne().getCode()));

        }
        return codeFournisseurColumn;
    }


    public JFXTreeTableColumn<Commande, String> getNomFournisseurColumn() {
        if (nomFournisseurColumn == null) {
            nomFournisseurColumn = new JFXTreeTableColumn<>("Nom");
            nomFournisseurColumn.setPrefWidth(120);
            nomFournisseurColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getPersonne().getNom()));
        }
        return nomFournisseurColumn;
    }

    public JFXTreeTableColumn<Commande, Long> getCodeProduitColumn() {
        if (codeProduitColumn == null) {
            codeProduitColumn = new JFXTreeTableColumn<>("Code Produit");
            codeProduitColumn.setPrefWidth(120);

            codeProduitColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getProduit().getCode()));

        }
        return codeProduitColumn;
    }


    public JFXTreeTableColumn<Commande, String> getDesProduitColumn() {
        if (designationColumn == null) {
            designationColumn = new JFXTreeTableColumn<>("Designation");
            designationColumn.setPrefWidth(120);
            designationColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getProduit().getDesignation()));
        }
        return designationColumn;
    }

    public JFXTreeTableColumn<Commande, String> getDateColumn() {
        if (dateColumn == null) {
            dateColumn = new JFXTreeTableColumn<>("Date");
            dateColumn.setPrefWidth(120);
            dateColumn.setCellValueFactory(param -> new SimpleStringProperty(simpleDateFormat.format(param.getValue().getValue().getDate())));
        }
        return dateColumn;
    }

    public JFXTreeTableColumn<Commande, Commande> getActionColumn() {
        if (actionColumn == null) {
            actionColumn = new JFXTreeTableColumn<>("Actions");
            actionColumn.setPrefWidth(150);
            actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue().getValue()));

          /*  actionColumn.setCellFactory(param -> new TreeTableCell<Commande, Commande>() {
                @Override
                protected void updateItem(Commande item, boolean empty) {
                    super.updateItem(item, empty);
                }
            });*/
            actionColumn.setCellFactory(param -> new JFXTreeTableCell<Commande, Commande>() {
                TableActions tableActions = new TableActions();

                @Override
                public void startEdit() {
                    System.out.println("startEdit");
                }

                @Override
                public void commitEdit(Commande newValue) {
                    System.out.println("commitEdit");
                }

                @Override
                public void cancelEdit() {
                    System.out.println("cancelEdit");
                }

                @Override
                protected void updateItem(Commande item, boolean empty) {
//                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }

                    setGraphic(tableActions);
//                    setGraphic(new JFXButton("Ajouter"));
                    tableActions.getUpdateButton().setOnMouseClicked(event -> {
                        Ajout.getAjout().vider();
                        Ajout.getAjout().setFields((Achat) item);
                        Container.getContainer().setCenter(Ajout.getAjout());
                    });
                    tableActions.getDeleteButton().setOnAction(
                            event -> {
                                achatManager.deleteById((Achat) item);
                            }
                    );
                }
            });
        }
        return actionColumn;
    }


}
