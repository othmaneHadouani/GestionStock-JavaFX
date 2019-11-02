package client.view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class menu extends personne.view.menu {

    public menu(double spacing) {
        super(spacing);
        lister.setOnMouseClicked(event -> {
            Container.getContainer().setCenter(ListClient.getListClient());
        });
        ajout.setOnMouseClicked(event -> {
            Ajout.getAjout().changeToAjouter();
            Container.getContainer().setCenter(Ajout.getAjout());
        });


    }
}
