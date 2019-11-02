package produit.view;

import View.TableHead;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notification.Alert;
import produit.manager.ImplProduitManager;
import produit.model.Produit;


public class ListProd extends VBox {
    public JFXTreeTableView<Produit> tableProduit;
    //    private ObservableList<Produit> produits = FXCollections.observableArrayList();
    TableHead tableHead;
    JFXTreeTableColumn<Produit, Integer> idColumn;
    JFXTreeTableColumn<Produit, Long> codeColumn;
    JFXTreeTableColumn<Produit, String> designationColumn;
    JFXTreeTableColumn<Produit, Double> prixAColumn;
    JFXTreeTableColumn<Produit, Double> prixVColumn;
    JFXTreeTableColumn<Produit, Integer> quantiteColumn;
    JFXTreeTableColumn<Produit, Produit> actionColumn;
    /*ProduitManager*/ ImplProduitManager produitManger = ImplProduitManager.getCategorieManager();


    private static ListProd aside;

    public static ListProd getAside() {
        if (aside == null)
            aside = new ListProd();
        return aside;
    }

    public ListProd() {
        tableHead = new TableHead("Liste des produits");

        TreeItem treeItem = new RecursiveTreeItem<Produit>(produitManger.get(), RecursiveTreeObject::getChildren);
        tableProduit = new JFXTreeTableView<Produit>(treeItem);
        tableProduit.setShowRoot(false);
        tableProduit.setEditable(true);
        tableProduit.getColumns().setAll(getCodeColumn(), getDesignationColumn(), getPrixAColumn(), getPrixVColumn(), getQuantitenColumn(), getActionColumn());
        tableProduit.rowFactoryProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("" + newValue);
        });
        tableHead.getSearch().textProperty().addListener((observable, oldValue, newValue) -> {
            /*produits.setAll(produitManger.chercherParDesignation(newValue));*/
            if (newValue != null)
                tableProduit.setPredicate(produitTreeItem -> produitTreeItem.getValue().getDesignation().contains(newValue));
        });
        setSpacing(20);
        setAlignment(Pos.CENTER);
        getChildren().add(tableHead);
        getChildren().add(tableProduit);
        tableProduit.setPrefHeight(700);

    }


    public JFXTreeTableColumn<Produit, Integer> getIdColumn() {
        if (idColumn == null) {
            idColumn = new JFXTreeTableColumn<>("Id");
            idColumn.setPrefWidth(30);

            idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        }
        return idColumn;
    }


    public JFXTreeTableColumn<Produit, Long> getCodeColumn() {
        if (codeColumn == null) {
            codeColumn = new JFXTreeTableColumn<>("code");
            codeColumn.prefWidthProperty().bind(tableProduit.widthProperty().divide(8));
            codeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        }
        return codeColumn;
    }


    public JFXTreeTableColumn<Produit, String> getDesignationColumn() {
        if (designationColumn == null) {
            designationColumn = new JFXTreeTableColumn<>("designation");
            designationColumn.prefWidthProperty().bind(tableProduit.widthProperty().divide(4));
            designationColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("designation"));
        }
        return designationColumn;
    }

    public JFXTreeTableColumn<Produit, Integer> getQuantitenColumn() {
        if (quantiteColumn == null) {
            quantiteColumn = new JFXTreeTableColumn<>("Quantite");
            quantiteColumn.prefWidthProperty().bind(tableProduit.widthProperty().divide(9));
            quantiteColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantite"));
        }
        return quantiteColumn;
    }


    public JFXTreeTableColumn<Produit, Double> getPrixAColumn() {
        if (prixAColumn == null) {
            prixAColumn = new JFXTreeTableColumn<>("Prix Achat");
            prixAColumn.prefWidthProperty().bind(tableProduit.widthProperty().divide(6));
            prixAColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("prixA"));
            prixAColumn.setCellFactory(param -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        }
        return prixAColumn;
    }

    public JFXTreeTableColumn<Produit, Double> getPrixVColumn() {
        if (prixVColumn == null) {
            prixVColumn = new JFXTreeTableColumn<>("Prix Vente");
            prixVColumn.prefWidthProperty().bind(tableProduit.widthProperty().divide(6));
            prixVColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("prixV"));
            prixVColumn.setCellFactory(param -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
           /* prixVColumn.setOnEditCommit(t -> {
                (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).
                        setPrixA(t.getNewValue());
            });*/
        }
        return prixVColumn;
    }

    public JFXTreeTableColumn<Produit, Produit> getActionColumn() {
        if (actionColumn == null) {
            actionColumn = new JFXTreeTableColumn<>("Actions");
            actionColumn.prefWidthProperty().bind(tableProduit.widthProperty().divide(6));
            actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue().getValue()));
            actionColumn.setCellFactory(param -> new JFXTreeTableCell<Produit, Produit>() {
                private final JFXButton deleteButton = new JFXButton("Delete");
                private final JFXButton updateButton = new JFXButton("Update");
                private final JFXButton detailButton = new JFXButton("");


                @Override
                public void startEdit() {
                    System.out.println("heloo");
                }

                @Override
                public void commitEdit(Produit newValue) {
                    System.out.println("newValue = [" + newValue + "]");
                }

                @Override
                public void cancelEdit() {

                }

                @Override
                protected void updateItem(Produit item, boolean empty) {

                    if (item == null) {
                        setGraphic(null);
                        return;
                    }
                    ImageView imageView = new ImageView("imgs/dustbin.png");
                    imageView.setFitWidth(22);
                    imageView.setFitHeight(22);
                    deleteButton.setGraphic(imageView);
                    deleteButton.setText("");
                    ImageView imageView2 = new ImageView("imgs/edit.png");
                    imageView2.setFitWidth(22);
                    imageView2.setFitHeight(22);
                    updateButton.setGraphic(imageView2);
                    updateButton.setText("");
                    ImageView imageView3 = new ImageView("imgs/loupe.png");
                    imageView3.setFitWidth(22);
                    imageView3.setFitHeight(22);
                    detailButton.setGraphic(imageView3);
                    HBox hBox = new HBox(1, deleteButton, updateButton, detailButton);
                    setGraphic(hBox);
                    detailButton.setOnMouseEntered(event -> {
                        Vproduit.getVproduitInstance().setFields(item);
                        Alert.getAlert().show(detailButton, Vproduit.getVproduitInstance());
                    });
                    detailButton.setOnMouseExited(event -> {
//                        Vproduit.getVproduitInstance().setFields(produit);
                        Alert.getAlert().hide();
                    });
                    updateButton.setOnAction(
                            event -> {

                                AjouterProduit.getAjouterVente().vider();
                                AjouterProduit.getAjouterVente().setFields(item);
                                Body.getBody().jfxTabPane.getSelectionModel().select(1);

                            }
                    );
                    deleteButton.setOnAction(
                            event -> {
                                produitManger.deleteById(deleteButton, item);
                            }
                    );
                }
            });
        }
        return actionColumn;
    }


  /*  public List<Produit> get() {
        return produitManger.get();
    }


    public long add(Produit value) {
        return produitManger.add(value);
    }

    public boolean update(Produit value) {
        return produitManger.update(value);
    }

    public Produit getById(long Id) {
        return produitManger.getById(Id);
    }

    public long deleteById(long Id) {
        return produitManger.deleteById(Id);
    }*/

  /*  public void create(ActionEvent actionEvent) {
        Produit produit = fieldsToValue();
        long id = produitManger.add(produit);
        produit.setId(id);
        produits.add(produit);
    }

    public Produit fieldsToValue() {
        return new Produit(Long.parseLong(fieldCode.getText()), fieldDesignation.getText(), Double.parseDouble(fieldPrix.getText()));
    }*/
}
