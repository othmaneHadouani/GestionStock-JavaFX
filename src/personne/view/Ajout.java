package personne.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import fournisseur.manager.FournisseurManager;
import fournisseur.manager.ImplFournisseurManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notification.Validation;

public class Ajout extends VBox {

    // private static Ajout ajouterFournisseur;
    HBox action;

    Label code = new Label("Code");
    protected JFXTextField fieldCode = new JFXTextField();

    Label nom = new Label("Nom");
    protected JFXTextField fieldNom = new JFXTextField();

    Label adresse = new Label("Adresse");
    protected JFXTextField fieldAdresse = new JFXTextField();

    Label tele = new Label("Tele");
    protected JFXTextField fieldTele = new JFXTextField();

    Label mail = new Label("Mail");
    protected JFXTextField fieldMail = new JFXTextField();


    protected JFXButton ajouter = new JFXButton("Ajouter");
    protected JFXButton modifier = new JFXButton("Modifier");


    protected Ajout() {
        setPadding(new Insets(20));
        setSpacing(25);
        fieldCode.setPromptText("Code");
//        fieldCode.setLabelFloat(true);
        fieldNom.setPromptText("Nom");
//        fieldNom.setLabelFloat(true);
        fieldAdresse.setPromptText("Adresse");
//        fieldAdresse.setLabelFloat(true);
        fieldTele.setPromptText("Tele");
//        fieldTele.setLabelFloat(true);
        fieldMail.setPromptText("Mail");
//        fieldMail.setLabelFloat(true);
        getStyleClass().add("myVBox");
        setAlignment(Pos.CENTER);
        getChildren().addAll(fieldCode, fieldNom, fieldAdresse, fieldTele, fieldMail);

        ajouter.getStyleClass().add("addButton");
        modifier.getStyleClass().add("addButton");
        action = new HBox(ajouter);

        action.setAlignment(Pos.CENTER);
        getChildren().add(action);

        fieldCode.getValidators().add(Validation.getValidation().getRequiredFieldValidator());
        fieldNom.getValidators().add(Validation.getValidation().getRequiredFieldValidator());
    }

    public void changeToModify() {

        action.getChildren().setAll(modifier);
    }

    public void changeToAjouter() {

        action.getChildren().setAll(ajouter);
    }

    public void validateFields() {
        fieldCode.validate();
        fieldNom.validate();
    }

}
