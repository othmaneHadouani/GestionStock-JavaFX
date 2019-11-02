package commande.achat.view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class menu extends VBox {
    JFXButton ajout = new JFXButton("Ajouter");
    JFXButton lister = new JFXButton("Lister");

    public menu(double spacing) {
        super(spacing);
        lister.setOnMouseClicked(event -> {
            Container.getContainer().setCenter(ListAchat.getListAchat());
        });
        ajout.setOnMouseClicked(event -> {
            Ajout.getAjout().vider();
            Ajout.getAjout().changeToAjouter();
            Container.getContainer().setCenter(Ajout.getAjout());
        });

        setPadding(new Insets(0));
        VBox vBox = new VBox(ajout, lister);
        Label label = new Label("Actions");
        label.setPadding(new Insets(10));
        getChildren().add(label);
        getChildren().add(vBox);
        getStyleClass().add("menu");

    }
}
