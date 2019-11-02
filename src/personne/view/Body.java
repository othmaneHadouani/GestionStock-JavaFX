package personne.view;


import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class Body extends BorderPane {
    menu menu;
    Container container;

    protected static Body body;

    protected Body(Container container) {
        this.container = container;
        setCenter(container);
    }

}
