package commande.achat.view;

import categorie.manager.CategorieManagerImpl;
import fournisseur.manager.ImplFournisseurManager;
import javafx.collections.transformation.FilteredList;
import produit.manager.ImplProduitManager;
import produit.model.Produit;
import commande.achat.model.Achat;
import commande.achat.manager.AchatManagerImpl;
import categorie.model.Categorie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import fournisseur.model.Fournisseur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import personne.model.Personne;

import java.util.Date;

public class Ajout extends VBox {

    private static Ajout ajouterVente;
    public JFXTextField fieldQuantite = new JFXTextField();
    public JFXTextField fieldTotale = new JFXTextField();

    public JFXComboBox<Categorie> categories;
    public JFXComboBox<Produit> produits;
    //    ObservableList<Produit> obProduits = FXCollections.observableArrayList();
    public JFXComboBox<Personne> fournisseurs;
    ImplFournisseurManager fournisseurManager = ImplFournisseurManager.getFournisseurManager();

    public JFXButton ajouter = new JFXButton("Ajouter");
    public JFXButton modifier = new JFXButton("Modifier");
    AchatManagerImpl achatManager = AchatManagerImpl.getAchatManager();
    Achat achatM;

    HBox action;

    ImplProduitManager produitManager = ImplProduitManager.getCategorieManager();
    CategorieManagerImpl categorieManager = CategorieManagerImpl.getCategorieManager();
    private static Ajout ajout;

    public static Ajout getAjout() {
        if (ajout == null)
            ajout = new Ajout();
        return ajout;
    }

    private Ajout() {
        setPadding(new Insets(20));
        setSpacing(25);
        getStyleClass().add("myVBox");
        setAlignment(Pos.CENTER);
//        getChildren().add(des);
        fieldQuantite.setPromptText("Quantite");
        fieldQuantite.setLabelFloat(true);
//        produits = new JFXComboBox<Produit>(obProduits);
        FilteredList<Produit> filterProduits = new FilteredList<Produit>(produitManager.get());
        produits = new JFXComboBox<Produit>(filterProduits);
        produits.setPrefWidth(500);
        produits.setEditable(true);
       /* produits.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                produits.setItems(obProduits);
            }
        });*/
        produits.getEditor().setOnKeyReleased(event -> {
            try {
               /* Stream<Produit> categorieStream = obProduits.stream().filter(produit -> produit.getDesignation().contains(produits.getEditor().getText()) || produits.getEditor().getText().equals(produit.getCode() + ""));
                ObservableList<Produit> obcategories = FXCollections.observableArrayList();
                obcategories.setAll(categorieStream.collect(Collectors.toList()));
                produits.setItems(obcategories);*/
                filterProduits.setPredicate(produit -> produit.getDesignation().contains(produits.getEditor().getText())
                        || produits.getEditor().getText().equals(produit.getCode() + ""));
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

        FilteredList<Categorie> filterCategories = new FilteredList<Categorie>(CategorieManagerImpl.getCategorieManager().get());
        categories = new JFXComboBox<Categorie>(filterCategories);
        categories.setPrefWidth(500);
        categories.setEditable(true);

      /*categories.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                categories.setItems(CategorieManagerImpl.getCategorieManager().get());
            }
        });*/

        categories.getEditor().setOnKeyReleased(event -> {
            filterCategories.setPredicate(categorie -> categorie.getLibelle().contains(categories.getEditor().getText()));
            categories.show();
        });
        categories.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("helo");
            if (newValue != null)
                filterProduits.setPredicate(produit -> produit.getCategorie().getId() == newValue.getId());
//                obProduits.setAll(produitManager.getByCategorie(newValue.getId()));

        });
        produits.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !fieldQuantite.getText().isEmpty()) {
                fieldTotale.setText(newValue.getPrixA() * Integer.parseInt(fieldQuantite.getText()) + "");
            }

        });
        fieldQuantite.setOnKeyReleased(event -> {
            Produit produit = produits.getSelectionModel().getSelectedItem();
            if (produit != null && !fieldQuantite.getText().isEmpty()) {
                fieldTotale.setText(produit.getPrixA() * Integer.parseInt(fieldQuantite.getText()) + "");
            }
        });

        categories.setConverter(new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie object) {
                if (object != null)
                    return object.getLibelle();
                return "";
            }

            @Override
            public Categorie fromString(String string) {
                return categories.getItems().stream().filter(categorie -> categorie.getLibelle().equals(string)).findFirst().orElse(null);
            }
        });

        FilteredList<Personne> personneFilteredList = new FilteredList<Personne>(fournisseurManager.get());

        fournisseurs = new JFXComboBox<Personne>(personneFilteredList);
        fournisseurs.setPrefWidth(500);
        fournisseurs.setEditable(true);
/*

        fournisseurs.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fournisseurs.setItems(ListFournisseur.getListFournisseur().getPersonnes());
            }
        });
*/

        fournisseurs.getEditor().setOnKeyReleased(event -> {
            /*Stream<Personne> categorieStream = ListFournisseur.getListFournisseur().getPersonnes().stream().filter(personne -> personne.getNom().contains(fournisseurs.getEditor().getText()) || fournisseurs.getEditor().getText().equals(personne.getCode() + ""));
            ObservableList<Personne> obcategories = FXCollections.observableArrayList();
            obcategories.setAll(categorieStream.collect(Collectors.toList()));
            fournisseurs.setItems(obcategories);*/
            personneFilteredList.setPredicate(personne -> personne.getNom().contains(fournisseurs.getEditor().getText()) || fournisseurs.getEditor().getText().equals(personne.getCode() + ""));
            fournisseurs.show();
        });
        fournisseurs.setConverter(new StringConverter<Personne>() {
            @Override
            public String toString(Personne object) {
                if (object != null)
                    return object.getCode() + " " + object.getNom();
                return "";
            }

            @Override
            public Personne fromString(String string) {
                return fournisseurs.getItems().stream().filter(categorie -> string.equals(categorie.getCode() + " " + categorie.getNom())).findFirst().orElse(null);
            }
        });

        produits.setPromptText("Produits");
        produits.setLabelFloat(true);
        categories.setPromptText("Categories");
        categories.setLabelFloat(true);

        fournisseurs.setPromptText("Fournisseurs");
        fournisseurs.setLabelFloat(true);

        fieldTotale.setPromptText("Total");
        fieldTotale.setLabelFloat(true);
        fieldTotale.setEditable(false);

        getChildren().add(categories);
        getChildren().add(produits);
        getChildren().add(fournisseurs);
        getChildren().add(fieldQuantite);
        getChildren().add(fieldTotale);


        ajouter.getStyleClass().add("addButton");
        action = new HBox(ajouter);
        action.setAlignment(Pos.CENTER);
        getChildren().add(action);

        ajouter.setOnMouseClicked(event -> {
            Achat achat = fieldsToValue();
            achatManager.add(achat);
            if (achat.getId() != -1) {
                produitManager.augmenterQuantite(achat.getProduit(), achat.getQuantite());
            }
            this.vider();
        });
        modifier.setOnMouseClicked(event -> {
            fieldsToValue(achatM);
            achatManager.update(achatM);
            // Container.getContainer().setCenter(ListAchat.getListAchat());
            //  changeToAjouter();
            Container.getContainer().setCenter(ListAchat.getListAchat());
            this.vider();

        });
    }

    public static Ajout getAjouterVente() {
        if (ajouterVente == null)
            ajouterVente = new Ajout();
        return ajouterVente;
    }


    public Achat fieldsToValue() {
        Achat achat = new Achat();
        achat.setQuantite(Integer.parseInt(fieldQuantite.getText()));
        achat.setProduit(produits.getSelectionModel().getSelectedItem());

//        achat.getProduit().setQuantite(achat.getProduit().getQuantite() + achat.getQuantite());

        achat.setPersonne(fournisseurs.getSelectionModel().getSelectedItem());
        achat.setDate(new Date());
        achat.setTotal(Double.parseDouble(fieldTotale.getText()));
        return achat;
    }

    public void changeToAjouter() {
        action.getChildren().setAll(ajouter);

    }

    public void changeToModify() {

        action.getChildren().setAll(modifier);
    }

    public void fieldsToValue(Achat achat) {
        achat.setQuantite(Integer.parseInt(fieldQuantite.getText()));
        achat.setProduit(produits.getSelectionModel().getSelectedItem());
        achat.setPersonne(fournisseurs.getSelectionModel().getSelectedItem());
        achat.setDate(new Date());
        achat.setTotal(Double.parseDouble(fieldTotale.getText()));
    }

    public void setFields(Achat achat) {
        this.achatM = achat;
        changeToModify();
        categories.getSelectionModel().select(categorieManager.getById(achat.getProduit().getCategorie().getId(), false));
        fournisseurs.getSelectionModel().select(achat.getPersonne());
        produits.getSelectionModel().select(achat.getProduit());
        fieldQuantite.setText(achat.getQuantite() + "");
        fieldTotale.setText(achat.getTotal() + "");
    }

    public void vider() {
        fournisseurs.getSelectionModel().clearSelection();
        fieldQuantite.setText("");
        fieldTotale.setText("");
        categories.getSelectionModel().clearSelection();
        produits.getSelectionModel().clearSelection();

    }
}
