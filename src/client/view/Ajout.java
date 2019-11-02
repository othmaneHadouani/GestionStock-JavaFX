package client.view;

import client.manager.ClientManager;
import client.manager.ImplClientManager;
import client.model.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Ajout extends personne.view.Ajout {


    ImplClientManager clientManager = ImplClientManager.getClientManager();
    Client clientM;

    private static Ajout ajout;

    public static Ajout getAjout() {
        if (ajout == null)
            ajout = new Ajout();
        return ajout;
    }

    private Ajout() {

        ajouter.setOnMouseClicked(event -> {
            validateFields();
            if (fieldCode.validate() && fieldNom.validate()) {
                Client client = fieldsToValue();
                clientManager.add(client);
                this.vider();
            }
        });

        modifier.setOnMouseClicked(event -> {
            validateFields();
            if (fieldCode.validate() && fieldNom.validate()) {
                fieldsToValue(clientM);
                clientManager.update(clientM);
                changeToAjouter();
                this.vider();
                Container.getContainer().jfxTabPane.getSelectionModel().select(0);
            }
        });
    }

    public Client fieldsToValue() {
        return new Client(Long.parseLong(fieldCode.getText()),
                fieldNom.getText(),
                fieldAdresse.getText(),
                fieldTele.getText(),
                fieldMail.getText());
    }

    private void vider() {
        fieldCode.setText("");
        fieldNom.setText("");
        fieldAdresse.setText("");
        fieldTele.setText("");
        fieldMail.setText("");

    }

    public void fieldsToValue(Client Client) {
        Client.setCode(Long.parseLong(fieldCode.getText()));
        Client.setNom(fieldNom.getText());
        Client.setAdresse(fieldAdresse.getText());
        Client.setMail(fieldMail.getText());
        Client.setTele(fieldTele.getText());
    }

    public void setFields(Client Client) {
        this.clientM = Client;
        changeToModify();
        fieldCode.setText(Client.getCode() + "");
        fieldNom.setText(Client.getNom());
        fieldAdresse.setText(Client.getAdresse());
        fieldTele.setText(Client.getTele());
        fieldMail.setText(Client.getMail());

    }
}
