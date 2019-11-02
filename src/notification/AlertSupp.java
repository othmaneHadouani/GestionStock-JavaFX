package notification;

import View.Body;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.action.Action;

import java.util.function.Consumer;

public class AlertSupp {
    private static AlertSupp suppression;
    VBox vBox = new VBox();
    ImageView supprimer = new ImageView(new Image("imgs/checked.png"));
    ImageView annuler = new ImageView(new Image("imgs/x-button(1).png"));
    HBox hBox = new HBox(5, supprimer, annuler);
    Text text = new Text("Etes-vous sûr?");
    HBox hBox2 = new HBox(text);
    PopOver popOver = new PopOver(vBox);

    public static AlertSupp getAlertSupp() {
        if (suppression == null)
            suppression = new AlertSupp();
        return suppression;
    }

    private AlertSupp() {

        hBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("alert");
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setPadding(new Insets(5));
        vBox.getChildren().add(hBox2);
        vBox.getChildren().add(hBox);

        annuler.setOnMouseClicked(event -> {
            popOver.hide();
        });
    }

    public void show(Node node, Consumer consumer) {
        supprimer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                consumer.accept(null);
                popOver.hide();
            }
        });
        popOver.show(node);
    }
}
