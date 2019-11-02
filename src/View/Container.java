package View;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Container extends BorderPane {

    BarMenu barMenu = new BarMenu();
    WindowBar windowBar = new WindowBar();

    public Container() {
        VBox vBox = new VBox(windowBar, barMenu);
        setTop(vBox);

    }
}
