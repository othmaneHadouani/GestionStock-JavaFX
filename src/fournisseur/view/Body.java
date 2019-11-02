package fournisseur.view;


import javafx.scene.layout.BorderPane;

public class Body extends personne.view.Body {


    public static Body getBody() {
        if (body == null)
            body = new Body();
        return (Body) body;
    }

    private Body() {
        super(Container.getContainer());
    }
}
