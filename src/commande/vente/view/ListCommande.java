package commande.vente.view;

import View.TableActions;
import View.TableHead;
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
import commande.vente.manager.CommandeManagerImpl;
import commande.vente.model.Vente;
import paiement.view.ReglementView;

import java.text.SimpleDateFormat;


public class ListCommande extends BorderPane {
    public JFXTreeTableView<Commande> tableCommande;
    //    private ObservableList<Commande> commandes = FXCollections.observableArrayList();
    TableHead tableHead;
    JFXTreeTableColumn<Commande, Integer> idColumn;
    JFXTreeTableColumn<Commande, Double> totalColumn;
    JFXTreeTableColumn<Commande, String> nomClientColumn;
    JFXTreeTableColumn<Commande, Long> codeClientColumn;
    JFXTreeTableColumn<Commande, String> dateColumn;
    JFXTreeTableColumn<Commande, Commande> actionColumn;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
    CommandeManagerImpl commandeManager = CommandeManagerImpl.getCommandeManager();
    private ReglementView reglementView = new ReglementView();

    private static ListCommande listCat;

    public static ListCommande getListCommande() {
        if (listCat == null)
            listCat = new ListCommande();
        return listCat;
    }

    public ReglementView getReglementView() {
        return reglementView;
    }

    public ListCommande() {
        tableHead = new TableHead("Liste des commandes");

        tableCommande = new JFXTreeTableView<Commande>(new RecursiveTreeItem<Commande>(commandeManager.get(), RecursiveTreeObject::getChildren));
        tableCommande.setShowRoot(false);
        tableCommande.setEditable(true);

        tableCommande.getColumns().setAll(getNomClientColumn(), getDateColumn(), getTotalColumn(), getActionColumn());
        tableCommande.rowFactoryProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("" + newValue);
        });
       /* search.textProperty().addListener((observable, oldValue, newValue) -> {
            achats.setAll(achatManager.getByLibelle(newValue));
        });*/

        tableCommande.setPrefHeight(700);
        tableCommande.setMaxWidth(600);
        tableCommande.setMinWidth(600);

        VBox vBox = new VBox(20);
        vBox.getChildren().add(tableHead);
        vBox.getChildren().add(tableCommande);
        tableHead.setMaxWidth(600);
        setLeft(vBox);
        setCenter(reglementView);
      /*  TreeTableRow<Commande> jFXTreeTableRow = new JFXTreeTableRow<Commande>();
        jFXTreeTableRow.startEdit();
        tableCommande.get*/
        getStyleClass().add("listCat");

        tableCommande.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                reglementView.setVente((Vente) newValue.getValue());
            }
        });
    }


    public JFXTreeTableColumn<Commande, Integer> getIdColumn() {
        if (idColumn == null) {
            idColumn = new JFXTreeTableColumn<>("Id");
            idColumn.setPrefWidth(30);

            idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));

        }
        return idColumn;
    }


    public JFXTreeTableColumn<Commande, Double> getTotalColumn() {
        if (totalColumn == null) {
            totalColumn = new JFXTreeTableColumn<>("Total");
            totalColumn.prefWidthProperty().bind(tableCommande.widthProperty().divide(100 / 20));

            totalColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("total"));

        }
        return totalColumn;
    }

    public JFXTreeTableColumn<Commande, Long> getCodeClientColumn() {
        if (codeClientColumn == null) {
            codeClientColumn = new JFXTreeTableColumn<>("Code Client");
            codeClientColumn.setPrefWidth(120);

            codeClientColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(
                    param.getValue().getValue().getPersonne() != null
                            ? param.getValue().getValue().getPersonne().getCode() : 0));

        }
        return codeClientColumn;
    }


    public JFXTreeTableColumn<Commande, String> getNomClientColumn() {
        if (nomClientColumn == null) {
            nomClientColumn = new JFXTreeTableColumn<>("Nom Client");
            nomClientColumn.prefWidthProperty().bind(tableCommande.widthProperty().divide(4));
            nomClientColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getPersonne() != null ?
                    param.getValue().getValue().getPersonne().getNom() : ""));
        }
        return nomClientColumn;
    }


    public JFXTreeTableColumn<Commande, String> getDateColumn() {
        if (dateColumn == null) {
            dateColumn = new JFXTreeTableColumn<>("Date");
            dateColumn.prefWidthProperty().bind(tableCommande.widthProperty().divide(4));
            dateColumn.setCellValueFactory(param -> new SimpleStringProperty(simpleDateFormat.format(param.getValue().getValue().getDate())));
        }
        return dateColumn;
    }

    public JFXTreeTableColumn<Commande, Commande> getActionColumn() {
        if (actionColumn == null) {
            actionColumn = new JFXTreeTableColumn<>("Actions");
            actionColumn.prefWidthProperty().bind(tableCommande.widthProperty().divide(100 / 22));
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
                        item.getLigneCommandes().stream().forEach(ligneCommande -> ligneCommande.setOldQuantite(ligneCommande.getQuantite()));
                        Ajout.getAjout().getLigneCommandes().setAll(item.getLigneCommandes());
                        Ajout.getAjout().setVenteAAjouter((Vente) item);
                        Ajout.getAjout().clients.getSelectionModel().clearSelection();
                        if (item.getPersonne() != null)
                            Ajout.getAjout().clients.getSelectionModel().select(item.getPersonne());
                        Ajout.getAjout().fieldTotale.setText(item.getTotal() + "");
                        Body.getBody().getJfxTabPane().getSelectionModel().select(1);
                    });
                    tableActions.getDeleteButton().setOnAction(
                            event -> {
                                commandeManager.deleteById(tableActions.getDeleteButton(), (Vente) item);

                            }
                    );
                }
            });
        }
        return actionColumn;
    }

    public JFXTreeTableView<Commande> getTableCommande() {
        return tableCommande;
    }

/*  public void create(ActionEvent actionEvent) {
        Commande produit = fieldsToValue();
        int id = achatManager.add(produit);
        produit.setId(id);
        achats.add(produit);
    }

    public Commande fieldsToValue() {
        return new Commande(Long.parseLong(fieldCode.getText()), fieldDesignation.getText(), Double.parseDouble(fieldPrix.getText()));
    }*/
}
