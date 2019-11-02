package client.view;


import javafx.scene.layout.BorderPane;

public class Body extends personne.view.Body {
    private static Body body;

    public static Body getBody() {
        if (body == null)
            body = new Body();
        return body;
    }

    private Body() {
        super(Container.getContainer());
    }
}
