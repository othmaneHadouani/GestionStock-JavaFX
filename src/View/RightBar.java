package View;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RightBar extends VBox {

    private static RightBar rightBar;

    public static RightBar getRightBar() {
        if (rightBar == null)
            rightBar = new RightBar();
        return rightBar;
    }

    private RightBar() {
        super(2);
    }
}
