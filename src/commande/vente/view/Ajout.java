package commande.vente.view;

import View.TableActions;
import client.manager.ImplClientManager;
import client.model.Client;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import notification.AlertSupp;
import notification.Validation;
import produit.manager.ImplProduitManager;
import produit.model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import personne.model.Personne;
import commande.vente.manager.CommandeManagerImpl;
import commande.vente.manager.LigneCommandeManagerImpl;
import commande.vente.model.Vente;
import commande.models.LigneCommande;

import java.util.stream.Collectors;

public class Ajout extends VBox {

    private static Ajout ajouterVente;
    public JFXTextField fieldQuantite = new JFXTextField();
    public JFXTextField fieldSousTotal = new JFXTextField();
    public JFXTextField fieldTotale = new JFXTextField();

    public JFXTextField fieldCode = new JFXTextField();
    public JFXTextField fieldCategorie = new JFXTextField();
    public JFXTextField fieldDesignation = new JFXTextField();
    public JFXTextField fieldPrixV = new JFXTextField();
    public JFXTextField fieldPrixA = new JFXTextField();

    JFXTreeTableColumn<LigneCommande, String> quantiteColumn;
    JFXTreeTableColumn<LigneCommande, Double> sousTotalColumn;
    JFXTreeTableColumn<LigneCommande, Long> codeProduitColumn;
    JFXTreeTableColumn<LigneCommande, String> designationColumn;
    JFXTreeTableColumn<LigneCommande, LigneCommande> actionColumn;

    public JFXTreeTableView<LigneCommande> tableLigneCommande;
    private ObservableList<LigneCommande> ligneCommandes = FXCollections.observableArrayList();
    private Vente venteAAjouter = new Vente();
    private LigneCommande ligneCommandeAAjouter = new LigneCommande();
    public JFXComboBox<Personne> clients;
    ImplClientManager clientManager = ImplClientManager.getClientManager();

    public void setVenteAAjouter(Vente venteAAjouter) {
        this.venteAAjouter = venteAAjouter;
    }

    public ObservableList<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public JFXComboBox<Produit> produits;

    public JFXButton ajouter = new JFXButton("Ajouter");
    public JFXButton annuler = new JFXButton("Annuler");
    public JFXButton annuler2 = new JFXButton("Annuler");
    public JFXButton enregistrerLaCommande = new JFXButton("Enregistrer");
    CommandeManagerImpl commandeManager = CommandeManagerImpl.getCommandeManager();
    LigneCommandeManagerImpl ligneCommandeManager = new LigneCommandeManagerImpl();
    ImplProduitManager produitManager = ImplProduitManager.getCategorieManager();
    //    LigneCommande ligneCommandeAModifier;
    private static Ajout ajout;


    public static Ajout getAjout() {
        if (ajout == null)
            ajout = new Ajout();
        return ajout;
    }

    private Ajout() {
        setPadding(new Insets(20, 0, 0, 0));
        setSpacing(25);
//        getStyleClass().add("myVBox");
        setAlignment(Pos.TOP_CENTER);
//        getChildren().add(des);

        fieldCode.setPromptText("Code");
        fieldCode.setLabelFloat(true);
        fieldCode.setEditable(false);
        fieldCategorie.setPromptText("Categorie");
        fieldCategorie.setLabelFloat(true);
        fieldCategorie.setEditable(false);
        fieldDesignation.setPromptText("Designation");
        fieldDesignation.setLabelFloat(true);
        fieldDesignation.setEditable(false);
        fieldPrixA.setPromptText("Prix Achat");
        fieldPrixA.setLabelFloat(true);
        fieldPrixA.setEditable(false);
        fieldPrixV.setPromptText("Prix Vente");
        fieldPrixV.setLabelFloat(true);
        ajouter.setDisable(true);
        fieldQuantite.setPromptText("Quantite");
        fieldQuantite.setLabelFloat(true);
        fieldQuantite.getValidators().add(Validation.getValidation().getIntegerValidator());

        FilteredList<Produit> produitFilteredList = new FilteredList<Produit>(produitManager.get());
        produits = new JFXComboBox<Produit>(produitFilteredList);
        produits.setEditable(true);

        tableLigneCommande = new JFXTreeTableView<LigneCommande>(new RecursiveTreeItem<LigneCommande>(ligneCommandes, RecursiveTreeObject::getChildren));
        tableLigneCommande.setShowRoot(false);
        tableLigneCommande.setEditable(true);

        tableLigneCommande.getColumns().setAll(getDesProduitColumn(), getQuantite(), getTotalColumn(), getActionColumn());


        produits.getEditor().setOnKeyReleased(event -> {
            try {
                produitFilteredList.setPredicate(produit -> (produit.getDesignation().toUpperCase().contains(produits.getEditor().getText().toUpperCase())
                        || produits.getEditor().getText().equals(produit.getCode() + "")));
                produits.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        produits.setConverter(new StringConverter<Produit>() {
            @Override
            public String toString(Produit object) {
                if (object != null)
                    return object.getCode() + " " + object.getDesignation();
                return "";
            }

            @Override
            public Produit fromString(String string) {
                return produits.getItems().stream().filter(produit -> string.equals(produit.getCode() + " " + produit.getDesignation())).findFirst().orElse(null);
            }
        });
        produits.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fieldCode.setText(newValue.getCode() + "");
                fieldPrixA.setText(newValue.getPrixA() + "");
                fieldPrixV.setText(newValue.getPrixV() + "");
                fieldDesignation.setText(newValue.getDesignation() + "");
                fieldCategorie.setText(newValue.getCategorie().getLibelle() + "");
                if (newValue.getQuantite() <= 0 && (ligneCommandeAAjouter == null && ligneCommandeAAjouter.getId() == 0)) {
                    fieldQuantite.setDisable(true);
                } else {
                    fieldQuantite.setDisable(false);
                }
                if (!fieldQuantite.getText().isEmpty()) {
//                field.setText(newValue.getDesignation() + "");
                    fieldSousTotal.setText(newValue.getPrixV() * Integer.parseInt(fieldQuantite.getText()) + "");
                }
            }

        });

        fieldQuantite.setOnKeyReleased(event -> {
            try {
                fieldQuantite.validate();
                boolean test = false;
                Produit produit = produits.getSelectionModel().getSelectedItem();
                int qt = (fieldQuantite.getText().isEmpty()) ? 0 : Integer.parseInt(fieldQuantite.getText());
                if (qt < 0)
                    fieldQuantite.setText("0");
                if (ligneCommandeAAjouter != null && ligneCommandeAAjouter.getId() != 0) {
                    int qtd = qt - ligneCommandeAAjouter.getOldQuantite();
                    test = testQantite(produit, qtd);
                    if (produit != null && test) {
                        fieldSousTotal.setText(produit.getPrixV() * qt + "");
                        ajouter.setDisable(false);
                    } else
                        ajouter.setDisable(true);
                } else {
                    test = testQantite(produit, qt);
                    if (produit != null && test) {
                        fieldSousTotal.setText(produit.getPrixV() * qt + "");
                        ajouter.setDisable(false);
                    } else
                        ajouter.setDisable(true);
                }
                if (event.getCode() == KeyCode.ENTER) {
                    if (fieldQuantite.validate() && test) {
                        LigneCommande ligneCommande = fieldsToValue();
                        LigneCommande ligneCommande2 = ligneCommandes.stream().filter(ligneCommande1 -> ligneCommande.getProduit().getId() == ligneCommande1.getProduit().getId()).findFirst().orElse(null);
                        if (ligneCommande2 != null) {
                            ligneCommande2.setQuantite(ligneCommande2.getQuantite() + ligneCommande.getQuantite());
                            ligneCommande2.setSousTotal(ligneCommande2.getSousTotal() + ligneCommande.getSousTotal());
                            ligneCommandes.remove(ligneCommande2);
                            ligneCommandes.add(ligneCommande2);
                        } else
                            ligneCommandes.add(ligneCommande);
                        resetValues();
                    }
                }
                /*if (ligneCommandeAModifier != null && ligneCommandeAModifier.getId() != 0) {
                    qt = ligneCommandeAModifier.getQuantite() - ligneCommandeAModifier.getOldQuantite();
                }
                if (produit != null && testQantite(produit, qt)) {
                    fieldSousTotal.setText(produit.getPrixV() * ligneCommandeAModifier.getQuantite() + qt + "");
                    ajouter.setDisable(false);
                } else
                    ajouter.setDisable(true);*/
            } catch (NumberFormatException e) {

            }
        });
        fieldPrixV.setOnKeyReleased(event -> {
            if (!fieldPrixV.getText().isEmpty() && !fieldQuantite.getText().isEmpty()) {
                fieldSousTotal.setText(Integer.parseInt(fieldPrixV.getText()) * Integer.parseInt(fieldQuantite.getText()) + "");
            }
        });

        FilteredList<Personne> personneFilteredList = new FilteredList<Personne>(clientManager.get());

        clients = new JFXComboBox<Personne>(personneFilteredList);
        clients.setEditable(true);

        produits.setPromptText("Produits");
        produits.setLabelFloat(true);

        fieldTotale.setPromptText("Total");
        fieldTotale.setLabelFloat(true);
        fieldTotale.setEditable(false);

        fieldSousTotal.setPromptText("Sous Total");
        fieldSousTotal.setLabelFloat(true);
        fieldSousTotal.setEditable(false);

        HBox hBox = new HBox(10, produits, fieldQuantite, fieldSousTotal);
        /*
        HBox hBox = new HBox(10, produits, fieldCode, fieldCategorie, fieldDesignation);
        HBox hBox2 = new HBox(10, fieldPrixA, fieldPrixV, fieldQuantite, fieldSousTotal);*/
        ajouter.getStyleClass().add("addButton");
        annuler.getStyleClass().add("addButton");
        annuler2.getStyleClass().add("addButton");
        enregistrerLaCommande.getStyleClass().add("addButton");
        HBox hBox3 = new HBox(10, ajouter, annuler);
        HBox hBox4 = new HBox(10, enregistrerLaCommande, annuler2);
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(clients);
        borderPane.setRight(fieldTotale);
        hBox4.setAlignment(Pos.CENTER);
        hBox3.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
//        produits.setPrefWidth(500);
        setAlignment(Pos.TOP_CENTER);
//        tableLigneCommande.setMaxWidth(800);
        tableLigneCommande.setMaxHeight(400);
        getChildren().add(hBox);
        getChildren().add(hBox3);
        getChildren().add(tableLigneCommande);
        getChildren().add(borderPane);
        getChildren().add(hBox4);


//        HBox hBox = new HBox(ajouter);
//        hBox.setAlignment(Pos.CENTER);
//        getChildren().add(hBox);


     /*   clients.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                clients.setItems(ListClient.getListClient().getPersonnes());
            }
        });*/

        clients.getEditor().setOnKeyReleased(event -> {
            personneFilteredList.setPredicate(personne -> personne.getNom().contains(clients.getEditor().getText()) || clients.getEditor().getText().equals(personne.getCode() + ""));
            clients.show();
        });
        clients.setConverter(new StringConverter<Personne>() {
            @Override
            public String toString(Personne object) {
                if (object != null)
                    return object.getCode() + " " + object.getNom();
                return "";
            }

            @Override
            public Personne fromString(String string) {
                return clients.getItems().stream().filter(categorie -> string.equals(categorie.getCode() + " " + categorie.getNom())).findFirst().orElse(null);
            }
        });

        clients.setPromptText("Clients");
        clients.setLabelFloat(true);

        ajouter.setOnMouseClicked(event -> {
            LigneCommande ligneCommande = fieldsToValue();
            LigneCommande ligneCommande2 = ligneCommandes.stream().filter(ligneCommande1 -> ligneCommande.getProduit().getId() == ligneCommande1.getProduit().getId()).findFirst().orElse(null);
            if (ligneCommande2 != null) {
                ligneCommande2.setQuantite(ligneCommande2.getQuantite() + ligneCommande.getQuantite());
                ligneCommande2.setSousTotal(ligneCommande2.getSousTotal() + ligneCommande.getSousTotal());
                ligneCommandes.remove(ligneCommande2);
                ligneCommandes.add(ligneCommande2);
            } else
                ligneCommandes.add(ligneCommande);
            resetValues();
        });

        enregistrerLaCommande.setOnMouseClicked(event -> {

            venteAAjouter.setTotal(Double.parseDouble(fieldTotale.getText()));
            venteAAjouter.setLigneCommandes(ligneCommandes.stream().collect(Collectors.toList()));
            Client client = (Client) clients.getSelectionModel().getSelectedItem();
            venteAAjouter.setPersonne(client);
//            commande.setClient(new Client());

            if (venteAAjouter.getId() == 0) {

                commandeManager.add(venteAAjouter);
                if (venteAAjouter.getId() != -1) {
                    resetValues();
                    ligneCommandes.clear();
                }
            } else {
                commandeManager.update(venteAAjouter);
                resetValues();
                ligneCommandes.clear();
            }
            clients.getSelectionModel().clearSelection();
            venteAAjouter = new Vente();
        });
        annuler.setOnMouseClicked(event -> {
            if (ligneCommandeAAjouter != null && ligneCommandeAAjouter.getId() != 0) {
                ligneCommandes.add(ligneCommandeAAjouter);
                ligneCommandeAAjouter = new LigneCommande();
            }
            resetValues();
        });
        annuler2.setOnMouseClicked(event -> {
            resetValues();
            venteAAjouter = new Vente();
            ligneCommandes.clear();
            produits.getSelectionModel().clearSelection();
            clients.getSelectionModel().clearSelection();
        });
        ligneCommandes.addListener((ListChangeListener<LigneCommande>) c -> {
            double total = 0;
            for (LigneCommande ligneCommande : ligneCommandes) {
                total += ligneCommande.getSousTotal();
            }
            fieldTotale.setText(total + "");
        });
    }


    public JFXTreeTableColumn<LigneCommande, LigneCommande> getActionColumn() {
        if (actionColumn == null) {
            actionColumn = new JFXTreeTableColumn<>("Actions");
            actionColumn.setPrefWidth(150);
            actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper(param.getValue().getValue()));

            actionColumn.setCellFactory(param -> new JFXTreeTableCell<LigneCommande, LigneCommande>() {
                TableActions tableActions = new TableActions();

                @Override
                public void startEdit() {
                    System.out.println("startEdit");
                }

                @Override
                public void commitEdit(LigneCommande newValue) {
                    System.out.println("commitEdit");
                }

                @Override
                public void cancelEdit() {
                    System.out.println("cancelEdit");
                }

                @Override
                protected void updateItem(LigneCommande item, boolean empty) {
//                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }

                    setGraphic(tableActions);
//                    setGraphic(new JFXButton("Ajouter"));
                    tableActions.getUpdateButton().setOnMouseClicked(event -> {
                        ajouter.setDisable(false);
                        setValues(item);
                        ligneCommandeAAjouter = item;
                        ligneCommandeAAjouter.setOldQuantite(ligneCommandeAAjouter.getQuantite());
                    });
                    tableActions.getDeleteButton().setOnAction(
                            event -> {
                                AlertSupp.getAlertSupp().show(tableActions.getDeleteButton(), o -> {
                                    ligneCommandes.remove(item);
                                });
                                if (item.getId() != 0) {
                                    ligneCommandeManager.deleteById(tableActions.getDeleteButton(), item);
                                }
                            }
                    );
                }
            });
        }
        return actionColumn;
    }

    public JFXTreeTableColumn<LigneCommande, String> getQuantite() {
        if (quantiteColumn == null) {
            quantiteColumn = new JFXTreeTableColumn<>("quantite");
            quantiteColumn.setPrefWidth(100);
            quantiteColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantite"));

//            libelleColumn.setCellValueFactory(p -> p.getValue().getValue().libelleProperty());
        }
        return quantiteColumn;
    }

    public JFXTreeTableColumn<LigneCommande, Double> getTotalColumn() {
        if (sousTotalColumn == null) {
            sousTotalColumn = new JFXTreeTableColumn<>("Sous Total");
            sousTotalColumn.setPrefWidth(120);

            sousTotalColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("sousTotal"));

        }
        return sousTotalColumn;
    }

    public JFXTreeTableColumn<LigneCommande, Long> getCodeProduitColumn() {
        if (codeProduitColumn == null) {
            codeProduitColumn = new JFXTreeTableColumn<>("Code Produit");
            codeProduitColumn.setPrefWidth(120);

            codeProduitColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getProduit().getCode()));

        }
        return codeProduitColumn;
    }


    public JFXTreeTableColumn<LigneCommande, String> getDesProduitColumn() {
        if (designationColumn == null) {
            designationColumn = new JFXTreeTableColumn<>("Designation");
            designationColumn.setPrefWidth(120);
            designationColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getProduit().getDesignation()));
        }
        return designationColumn;
    }

    public LigneCommande fieldsToValue() {
        ligneCommandeAAjouter.setProduit(produits.getSelectionModel().getSelectedItem());
        ligneCommandeAAjouter.setSousTotal(Double.parseDouble(fieldSousTotal.getText()));
        ligneCommandeAAjouter.setQuantite(Integer.parseInt(fieldQuantite.getText()));
//        ligneCommandeAAjouter.setOldQuantite(Integer.parseInt(fieldQuantite.getText()));
        return ligneCommandeAAjouter;
    }

    public void resetValues() {
        ligneCommandeAAjouter = new LigneCommande();
        produits.getSelectionModel().clearSelection();
        fieldCode.setText("");
        fieldPrixA.setText("");
        fieldPrixV.setText("");
        fieldDesignation.setText("");
        fieldQuantite.setText("");
        fieldSousTotal.setText("");
        fieldCategorie.setText("");
        ajouter.setDisable(true);
//        commandeAAjouter = new Commande();
//        sousTotalColumn.setText("");
    }

    public void setValues(LigneCommande ligneCommande) {
        this.ligneCommandeAAjouter = ligneCommande;
        produits.getSelectionModel().select(produitManager.getById(ligneCommande.getProduit().getId(), false));
        fieldQuantite.setText(ligneCommande.getQuantite() + "");
        fieldSousTotal.setText(ligneCommande.getSousTotal() + "");
        ligneCommandes.remove(ligneCommande);
    }

    public boolean testQantite(Produit produit, int quantite) {
        Produit produit1 = produitManager.getById(produit.getId(), false);
        LigneCommande ligneCommande = ligneCommandes.stream().filter(ligneCommande1 -> ligneCommande1.getProduit().getId() == produit.getId()).findFirst().orElse(null);
        if (ligneCommande != null)
            quantite += ligneCommande.getQuantite();
        return produit1.getQuantite() >= quantite;
    }
}
