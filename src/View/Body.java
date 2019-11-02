package View;

import javafx.scene.layout.BorderPane;

public class Body extends BorderPane {
    menu menu = new menu(0);
    Container container = new Container();

    private static Body body;

    public static Body getBody() {
        if (body == null)
            body = new Body();
        return body;
    }

    public void init() {
        setCenter(container);
        setLeft(menu);
    }

    private Body() {
        init();
    }

    public Container getContainer() {
        return container;
    }
}
