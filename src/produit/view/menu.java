package produit.view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class menu extends VBox {
    JFXButton vente = new JFXButton("Ajouter");
    JFXButton lister = new JFXButton("Lister");

    public menu(double spacing) {
        super(spacing);
        vente.setOnMouseClicked(event -> {
            AjouterProduit.getAjouterVente().vider();
            AjouterProduit.getAjouterVente().changeToAjouter();
            Container.getContainer().setCenter(AjouterProduit.getAjouterVente());

        });
        lister.setOnAction(event -> {
            Container.getContainer().setCenter(ListProd.getAside());
        });
        setPadding(new Insets(0));
        VBox vBox = new VBox(vente, lister);
        Label label = new Label("Actions");
        label.setPadding(new Insets(10));
        getChildren().add(label);
        getChildren().add(vBox);
        getStyleClass().add("menu");

    }
}
