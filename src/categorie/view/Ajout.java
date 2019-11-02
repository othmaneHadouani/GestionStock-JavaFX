package categorie.view;

import categorie.manager.CategorieManager;
import categorie.manager.CategorieManagerImpl;
import categorie.model.Categorie;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notification.Validation;

public class Ajout extends BorderPane {

    private static Ajout ajouterVente;
    Categorie categorieM;


    public JFXTextField fieldLibelle = new JFXTextField();

    public JFXButton ajouter = new JFXButton("Ajouter");
    public JFXButton modifier = new JFXButton("Modifier");
    public JFXButton annuler = new JFXButton("Annuler");

    HBox hBox = new HBox(5);
    /*CategorieManager*/
    CategorieManagerImpl categorieManager = CategorieManagerImpl.getCategorieManager();

    private static Ajout ajout;

    public static Ajout getAjout() {
        if (ajout == null)
            ajout = new Ajout();
        return ajout;
    }

    private Ajout() {
//        setPadding(new Insets(20));
//        getChildren().add(des);
        fieldLibelle.getValidators().add(Validation.getValidation().getRequiredFieldValidator());
        fieldLibelle.setPromptText("Nouvelle categorie");
        fieldLibelle.setLabelFloat(true);
        fieldLibelle.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (fieldLibelle.validate()) {
                    if (hBox.getChildren().contains(ajouter)) {
                        Categorie categorie = fieldsToValue();
                        categorieManager.add(categorie);
                    } else {
                        fieldsToValue(categorieM);
                        categorieManager.update(categorieM);
                    }
                    this.vider();

                }
            }
        });
        ajouter.getStyleClass().add("addButton");
        modifier.getStyleClass().add("addButton");
        annuler.getStyleClass().add("addButton");
        fieldLibelle.setPrefWidth(400);
        setLeft(fieldLibelle);
        hBox.getChildren().setAll(ajouter, annuler);
        setRight(hBox);


        ajouter.setOnMouseClicked(event -> {
            if (fieldLibelle.validate()) {
                Categorie categorie = fieldsToValue();
                categorieManager.add(categorie);
                this.vider();
            }
        });
        modifier.setOnMouseClicked(event -> {
            if (fieldLibelle.validate()) {

                fieldsToValue(categorieM);
                categorieManager.update(categorieM);
                vider();
            }
//            Container.getContainer().setCenter(ListCat.getListCat());

            //  changeToAjouter();

        });
        annuler.setOnAction(event -> {
            changeToAjouter();
            vider();
        });
    }

    public static Ajout getAjouterVente() {
        if (ajouterVente == null)
            ajouterVente = new Ajout();
        return ajouterVente;
    }

    public void vider() {

        fieldLibelle.setText("");
    }


    public Categorie fieldsToValue() {
        return new Categorie(fieldLibelle.getText().toString());
    }

    public void fieldsToValue(Categorie Categorie) {
        Categorie.setLibelle(fieldLibelle.getText());
    }

    public void setFields(Categorie categorie) {
        this.categorieM = categorie;
        changeToModify();

        fieldLibelle.setText(categorie.getLibelle());


    }

    public void changeToAjouter() {
        hBox.getChildren().setAll(ajouter, annuler);

    }

    public void changeToModify() {

        hBox.getChildren().setAll(modifier, annuler);
    }
}
