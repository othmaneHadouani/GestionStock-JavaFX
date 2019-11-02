package produit.view;

import View.UplodeImage;
import categorie.manager.CategorieManagerImpl;
import categorie.model.Categorie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notification.Validation;
import produit.manager.ImplProduitManager;
import produit.model.Produit;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

public class AjouterProduit extends VBox {
    private static AjouterProduit ajouterVente;
    public JFXTextField fieldCode = new JFXTextField();
    public JFXComboBox<Categorie> categories;
    public JFXTextField fieldDesignation = new JFXTextField();
    public JFXTextField fieldPrixV = new JFXTextField();
    public JFXTextField fieldPrixA = new JFXTextField();
    public JFXButton ajouter = new JFXButton("Ajouter");
    public JFXButton modifier = new JFXButton("Modifier");

    Produit produitM;

    /*ProduitManager*/ ImplProduitManager produitManager = ImplProduitManager.getCategorieManager();

    HBox action;

    public AjouterProduit() {


        FilteredList<Categorie> filterCategories = new FilteredList<Categorie>(CategorieManagerImpl.getCategorieManager().get());
        categories = new JFXComboBox<Categorie>(filterCategories);
        categories.setPrefWidth(500);
        categories.setEditable(true);
      /*  categories.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                categories.setItems(CategorieManagerImpl.getCategorieManager().get());
            }
        });*/
        categories.getEditor().setOnKeyReleased(event -> {
         /*   Stream<Categorie> categorieStream = CategorieManagerImpl.getCategorieManager().get().stream().filter(categorie -> categorie.getLibelle().contains(categories.getEditor().getText()));
            ObservableList<Categorie> obcategories = FXCollections.observableArrayList();
            obcategories.setAll(categorieStream.collect(Collectors.toList()));
            categories.setItems(obcategories);*/
            filterCategories.setPredicate(categorie -> categorie.getLibelle().toUpperCase().contains(categories.getEditor().getText().toUpperCase()));
            categories.show();
        });
        /*valueProperty().addListener((observable, oldValue, newValue) -> {

        });*/
//        categories.show();
        setPadding(new Insets(20));
        setSpacing(25);
//        getChildren().add(code);
        fieldCode.setPromptText("Code");
//        fieldCode.setLabelFloat(true);
        getChildren().add(fieldCode);
        categories.setPromptText("Categorie");
//        categories.setLabelFloat(true);
        getChildren().add(categories);
      /*  getChildren().add(des);*/
        fieldDesignation.setPromptText("Designation");
//        fieldDesignation.setLabelFloat(true);
        getChildren().add(fieldDesignation);
//        getChildren().add(prixA);
        fieldPrixA.setPromptText("Prix Achat");
//        fieldPrixA.setLabelFloat(true);
        getChildren().add(fieldPrixA);
//        getChildren().add(prixV);
        fieldPrixV.setPromptText("Prix Vente");
//        fieldPrixV.setLabelFloat(true);
        getChildren().add(fieldPrixV);
        getChildren().add(UplodeImage.getUplodeImage());
        ajouter.getStyleClass().add("addButton");
        modifier.getStyleClass().add("addButton");
        getStyleClass().add("myVBox");
        setAlignment(Pos.CENTER);
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

        action = new HBox(ajouter);
        action.setAlignment(Pos.CENTER);
        getChildren().add(action);

        ajouter.setOnMouseClicked(event -> {
            validateFields();
            if (fieldCode.validate() && fieldPrixA.validate() && fieldPrixV.validate() && fieldDesignation.validate() && categories.validate()) {
                Produit produit = fieldsToValue();
                produitManager.add(produit);
                File file = UplodeImage.getUplodeImage().getFile();
                if (file != null) {
//                file.getAbsolutePath().split(".");
                    try {
                        String s[] = file.getAbsolutePath().split("\\.");
                        String name = s[s.length - 1];
                        FileInputStream fileInputStream = new FileInputStream(file);
//                    File file1 = new File(resourceBundle.getString("apache.url") + "images/" + produit.getId() + "." + name);
                        File file1 = new File(System.getProperty("user.home").replaceAll("\\\\", "/") + "/" + produit.getId() + "." + name);
//                    File file1 = new File("/" + produit.getId() + "." + name);
                        file1.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file1);
                        int i;
                        while ((i = fileInputStream.read()) != -1) {
                            fileOutputStream.write(i);
                        }
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                UplodeImage.getUplodeImage().intImage();
//            ListProd.getAside().getProduits().add(produit);
                this.vider();
            }
        });
        modifier.setOnMouseClicked(event -> {
            validateFields();
            if (fieldCode.validate() && fieldPrixA.validate() && fieldPrixV.validate() && fieldDesignation.validate() && categories.validate()) {
                fieldsToValue(produitM);
                produitManager.update(produitM);
                File file = UplodeImage.getUplodeImage().getFile();
                if (file != null) {
//                file.getAbsolutePath().split(".");
                    try {
                        String s[] = file.getAbsolutePath().split("\\.");
                        String name = s[s.length - 1];
                        FileInputStream fileInputStream = new FileInputStream(file);
                        File file1 = new File(System.getProperty("user.home").replaceAll("\\\\", "/") + "/" + produitM.getId() + "." + name);
                        if (!file1.exists())
                            file1.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file1);
                        int i;
                        while ((i = fileInputStream.read()) != -1) {
                            fileOutputStream.write(i);
                        }
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                UplodeImage.getUplodeImage().intImage();
//            ListProd.getAside().getProduits().add(produit);
                Body.getBody().jfxTabPane.getSelectionModel().select(0);
                this.vider();
            }
        });
        fieldCode.getValidators().add(Validation.getValidation().getRequiredFieldValidator());
        categories.getValidators().add(Validation.getValidation().getRequiredFieldValidator());
        fieldDesignation.getValidators().add(Validation.getValidation().getRequiredFieldValidator());
        fieldPrixV.getValidators().add(Validation.getValidation().getRequiredFieldValidator());
        fieldPrixA.getValidators().add(Validation.getValidation().getRequiredFieldValidator());
        fieldPrixV.getValidators().add(Validation.getValidation().getDoubleValidator());
        fieldPrixA.getValidators().add(Validation.getValidation().getDoubleValidator());
    }

    public static AjouterProduit getAjouterVente() {
        if (ajouterVente == null)
            ajouterVente = new AjouterProduit();
        return ajouterVente;
    }


    public Produit fieldsToValue() {
        return new Produit(Long.parseLong(fieldCode.getText()), fieldDesignation.getText(), Double.parseDouble(fieldPrixA.getText()), Double.parseDouble(fieldPrixV.getText()), categories.getSelectionModel().getSelectedItem());
    }

    public void fieldsToValue(Produit produit) {
        produit.setCode(Long.parseLong(fieldCode.getText()));
        produit.setDesignation(fieldDesignation.getText());
        produit.setPrixA(Double.parseDouble(fieldPrixA.getText()));
        produit.setPrixV(Double.parseDouble(fieldPrixV.getText()));
        produit.setId_cat(categories.getSelectionModel().getSelectedItem());
    }

    public void setFields(Produit produit) {
        this.produitM = produit;
        changeToModify();
        fieldCode.setText(produit.getCode() + "");
        fieldDesignation.setText(produit.getDesignation());
        fieldPrixA.setText(produit.getPrixA() + "");
        fieldPrixV.setText(produit.getPrixV() + "");
        categories.getSelectionModel().select(produit.getCategorie());
        File file = new File(System.getProperty("user.home").replaceAll("\\\\", "/") + "/" + produit.getId() + ".jpg");
        System.out.println("file:" + file.getAbsolutePath());
        UplodeImage.getUplodeImage().setImage(new Image("file:" + file.getAbsolutePath()));
    }

    public void changeToAjouter() {
        action.getChildren().setAll(ajouter);

    }

    public void changeToModify() {

        action.getChildren().setAll(modifier);
    }

    public void vider() {
        fieldPrixV.setText("");
        fieldDesignation.setText("");
        fieldPrixA.setText("");
        fieldCode.setText("");
    }

    public void validateFields() {
        fieldCode.validate();
        fieldPrixA.validate();
        fieldPrixV.validate();
        fieldDesignation.validate();
        categories.validate();
    }

}
