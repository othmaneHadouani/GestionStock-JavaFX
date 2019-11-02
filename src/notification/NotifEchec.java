package notification;

import View.Body;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotifEchec {
    private static NotifEchec notifSuccee;
    Notifications notifications;
    Image image;
    ImageView imageView;

    JFXDialog jfxDialog = new JFXDialog();
    JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();

    public static NotifEchec getNotifEchec() {
        if (notifSuccee == null)
            notifSuccee = new NotifEchec();
        return notifSuccee;
    }

    private NotifEchec() {


        notifications = Notifications.create();
        notifications.hideAfter(Duration.minutes(1));
    }

    public void show(String message) {
        notifications.text(message).owner(Body.getBody()).position(Pos.TOP_RIGHT).title("Message").showError();

    }
}
