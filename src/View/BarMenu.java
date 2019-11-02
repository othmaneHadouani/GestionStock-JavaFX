package View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class BarMenu extends BorderPane {

    private static BarMenu barMenu;

    RightBar rightBar = RightBar.getRightBar();
    LeftBar leftBar = LeftBar.getLeftBar();

    public BarMenu() {


        super.setLeft(leftBar);
//        super.setRight(rightBar);
        this.getStyleClass().add("barmenu");
        this.setPadding(new Insets(10));
    }
}
