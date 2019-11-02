package commande.vente.view;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class Container extends BorderPane {
    private static Container container;

    private Container() {
        setPadding(new Insets(10, 20, 20, 10));
        setCenter(ListCommande.getListCommande());
    }

    public static Container getContainer() {
        if (container == null)
            container = new Container();
        return container;
    }

}
