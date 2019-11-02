package categorie.view;

import View.TableHead;
import categorie.manager.CategorieManager;
import categorie.manager.CategorieManagerImpl;
import View.TableActions;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import categorie.model.Categorie;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.List;


public class ListCat extends VBox {
    TableHead tableHead;
    public JFXTreeTableView<Categorie> tableCategorie;
    JFXTreeTableColumn<Categorie, Integer> idColumn;
    JFXTreeTableColumn<Categorie, String> libelleColumn;
    JFXTreeTableColumn<Categorie, Categorie> actionColumn;

    /*CategorieManager*/ CategorieManagerImpl categorieManager = CategorieManagerImpl.getCategorieManager();

    VBox vBox;

    private static ListCat listCat;

    public static ListCat getListCat() {
        if (listCat == null)
            listCat = new ListCat();
        return listCat;
    }


    private ListCat() {
        tableHead = new TableHead("Liste des categories");
/*
        setOnKeyReleased(event -> {
            tableHead.getSearch().setText(event.getText());
        });*/
        final TreeItem<Categorie> root = new RecursiveTreeItem<Categorie>(categorieManager.get(), RecursiveTreeObject::getChildren);

        tableCategorie = new JFXTreeTableView<Categorie>(root);


        tableCategorie.setShowRoot(false);
        tableCategorie.setEditable(true);

        tableCategorie.getColumns().setAll(getLibelleColumn(), getActionColumn());
        tableCategorie.rowFactoryProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("" + newValue);
        });
        tableHead.getSearch().textProperty().addListener((observable, oldValue, newValue) -> {
//            categories.setAll(categorieManager.getByLibelle(newValue, false));
            if (newValue != null) ;
            tableCategorie.setPredicate(categorieTreeItem -> categorieTreeItem.getValue().getLibelle().contains(newValue));
        });
        tableCategorie.setPrefHeight(700);

/*
        vBox.setPadding(new Insets(10));
        vBox.getStyleClass().add("vBoxsearch");
        vBox.setAlignment(Pos.CENTER);*/
//        setPadding(new Insets(30));
        setSpacing(20);
        setAlignment(Pos.CENTER);
//        tableCategorie.setMaxWidth(1100);
        this.getChildren().add(tableHead);
        this.getChildren().add(tableCategorie);
        this.getChildren().add(Ajout.getAjout());
      /*  TreeTableRow<Categorie> jFXTreeTableRow = new JFXTreeTableRow<Categorie>();
        jFXTreeTableRow.startEdit();
        tableCategorie.get*/
        getStyleClass().add("listCat");
    }


    public JFXTreeTableColumn<Categorie, Integer> getIdColumn() {
        if (idColumn == null) {
            idColumn = new JFXTreeTableColumn<>("Id");
            idColumn.prefWidthProperty().bind(tableCategorie.widthProperty().divide(3));

            idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));

        }
        return idColumn;
    }


    public JFXTreeTableColumn<Categorie, String> getLibelleColumn() {
        if (libelleColumn == null) {
            libelleColumn = new JFXTreeTableColumn<>("Libelle");
//            libelleColumn.setPrefWidth(500);
            libelleColumn.prefWidthProperty().bind(tableCategorie.widthProperty().divide(2));
//            libelleColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("libelle"));

            libelleColumn.setCellValueFactory(p -> p.getValue().getValue().libelleProperty());
        }
        return libelleColumn;
    }


    public JFXTreeTableColumn<Categorie, Categorie> getActionColumn() {
        if (actionColumn == null) {
            actionColumn = new JFXTreeTableColumn<>("Actions");
            actionColumn.prefWidthProperty().bind(tableCategorie.widthProperty().divide(100 / 49.5));
            actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue().getValue()));

          /*  actionColumn.setCellFactory(param -> new TreeTableCell<Categorie, Categorie>() {
                @Override
                protected void updateItem(Categorie item, boolean empty) {
                    super.updateItem(item, empty);
                }
            });*/
            actionColumn.setCellFactory(param -> new JFXTreeTableCell<Categorie, Categorie>() {
                TableActions tableActions = new TableActions();

                @Override
                public void startEdit() {
                    System.out.println("startEdit");
                }

                @Override
                public void commitEdit(Categorie newValue) {
                    System.out.println("commitEdit");
                }

                @Override
                public void cancelEdit() {
                    System.out.println("cancelEdit");
                }

                @Override
                protected void updateItem(Categorie item, boolean empty) {
//                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }

                    setGraphic(tableActions);

//                    setGraphic(new JFXButton("Ajouter"));
                    tableActions.getUpdateButton().setOnMouseClicked(event -> {
                        Ajout.getAjout().vider();
                        Ajout.getAjout().setFields(item);
//                        Container.getContainer().setCenter(Ajout.getAjout());
                    });
                    tableActions.getDeleteButton().setOnAction(
                            event -> {
                                categorieManager.deleteById(tableActions.getDeleteButton(), item);

                            }
                    );
                }
            });
        }
        return actionColumn;
    }
}
