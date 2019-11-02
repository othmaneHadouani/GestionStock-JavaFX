package notification;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.util.function.Consumer;

public class Alert {
    private static Alert alert;
    PopOver popOver = new PopOver();

    public static Alert getAlert() {
        if (alert == null)
            alert = new Alert();
        return alert;
    }

    private Alert() {
        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
    }

    public void show(Node node, Node content) {
        popOver.setContentNode(content);
        popOver.show(node);
    }

    public void hide() {
        popOver.hide(Duration.ZERO);
    }

}
