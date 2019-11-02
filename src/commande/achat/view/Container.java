package commande.achat.view;

import javafx.scene.layout.BorderPane;

public class Container extends BorderPane {
    private static Container container;

    private Container() {
        setCenter(ListAchat.getListAchat());
    }

    public static Container getContainer() {
        if (container == null)
            container = new Container();
        return container;
    }

}
