package commande.vente.view;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import produit.view.AjouterProduit;
import produit.view.ListProd;

public class Body extends BorderPane {
    commande.vente.view.menu menu = new menu(0);
    Container container = Container.getContainer();
    Tab tab;
    Tab tab2;
    //    ListProd aside = ListProd.getAside();
    private JFXTabPane jfxTabPane = new JFXTabPane();
    private static Body body;

    public static Body getBody() {
        if (body == null)
            body = new Body();
        return body;
    }

    private Body() {
        BorderPane borderPane = new BorderPane();
        BorderPane borderPane2 = new BorderPane();
        borderPane.getStyleClass().add("padd");
        borderPane2.getStyleClass().add("padd");
        borderPane.setCenter(Ajout.getAjout());
        borderPane2.setCenter(ListCommande.getListCommande());
        tab = new Tab("Ajouter", borderPane);
        tab2 = new Tab("Lister", borderPane2);

        jfxTabPane.getTabs().addAll(tab2, tab);
        setCenter(jfxTabPane);
//        setLeft(menu);
    }

    public JFXTabPane getJfxTabPane() {
        return jfxTabPane;
    }
}
