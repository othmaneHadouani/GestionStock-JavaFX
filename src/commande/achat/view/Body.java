package commande.achat.view;

import javafx.scene.layout.BorderPane;

public class Body extends BorderPane {
    commande.achat.view.menu menu = new menu(0);
    Container container = Container.getContainer();

    private static Body body;

    public static Body getBody() {
        if (body == null)
            body = new Body();
        return body;
    }

    private Body() {
        setCenter(container);
        setLeft(menu);
    }
}
