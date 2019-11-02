package fournisseur.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import fournisseur.manager.FournisseurManager;
import fournisseur.manager.ImplFournisseurManager;
import fournisseur.model.Fournisseur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Ajout extends personne.view.Ajout {

    // private static Ajout ajouterFournisseur;

    Label mail = new Label("Fax");
    JFXTextField fieldFax = new JFXTextField();
    Fournisseur fournisseurM;
    ImplFournisseurManager fournisseurtManager = ImplFournisseurManager.getFournisseurManager();

    private static Ajout ajout;

    public static Ajout getAjout() {
        if (ajout == null)
            ajout = new Ajout();
        return ajout;
    }

    private Ajout() {
        super();
        int size = getChildren().size();
        fieldFax.setPromptText("Fax");
        fieldFax.setLabelFloat(true);
        getChildren().add(size - 2, fieldFax);

        ajouter.setOnMouseClicked(event -> {
            validateFields();
            if (fieldCode.validate() && fieldNom.validate()) {
                Fournisseur fournisseurt = fieldsToValue();
                fournisseurtManager.add(fournisseurt);
                this.vider();
            }
        });
        modifier.setOnMouseClicked(event -> {
            validateFields();
            if (fieldCode.validate() && fieldNom.validate()) {
                fieldsToValue(fournisseurM);
                fournisseurtManager.update(fournisseurM);
                changeToAjouter();
                Container.getContainer().jfxTabPane.getSelectionModel().select(0);
                this.vider();
            }
        });
    }


    public Fournisseur fieldsToValue() {
        return new Fournisseur(Long.parseLong(fieldCode.getText()),
                fieldNom.getText(),
                fieldAdresse.getText(),
                fieldTele.getText(),
                fieldMail.getText(),
                fieldFax.getText());
    }

    public void fieldsToValue(Fournisseur fournisseur) {
        fournisseur.setCode(Long.parseLong(fieldCode.getText()));
        fournisseur.setNom(fieldNom.getText());
        fournisseur.setAdresse(fieldAdresse.getText());
        fournisseur.setMail(fieldMail.getText());
        fournisseur.setTele(fieldTele.getText());
        fournisseur.setFax(fieldFax.getText());
    }

    public void setFields(Fournisseur fournisseur) {
        this.fournisseurM = fournisseur;
        changeToModify();
        fieldCode.setText(fournisseur.getCode() + "");
        fieldNom.setText(fournisseur.getNom());
        fieldAdresse.setText(fournisseur.getAdresse());
        fieldTele.setText(fournisseur.getTele());
        fieldMail.setText(fournisseur.getMail());
        fieldFax.setText(fournisseur.getFax());
    }

    public void vider() {
        fieldCode.setText("");
        fieldNom.setText("");
        fieldAdresse.setText("");
        fieldTele.setText("");
        fieldMail.setText("");
        fieldFax.setText("");

    }
}
