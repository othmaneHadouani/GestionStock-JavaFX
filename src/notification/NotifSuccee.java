package notification;

import View.Body;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.action.Action;

import java.util.function.Consumer;

public class NotifSuccee {
    private static NotifSuccee notifSuccee;
    Notifications notifications;
    Image image;
    ImageView imageView;

    JFXDialog jfxDialog = new JFXDialog();
    JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();

    public static NotifSuccee getNotifSuccee() {
        if (notifSuccee == null)
            notifSuccee = new NotifSuccee();
        return notifSuccee;
    }

    private NotifSuccee() {
        notifications = Notifications.create();
    }

    public void show(String message) {
        notifications.text(message).owner(Body.getBody()).position(Pos.TOP_RIGHT).title("Message").showInformation();

    }
}
