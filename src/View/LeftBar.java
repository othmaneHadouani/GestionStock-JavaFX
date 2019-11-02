package View;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class LeftBar extends HBox {
    private static LeftBar leftBar;
    private Label titre = new Label("Accueil");

    public static LeftBar getLeftBar() {
        if (leftBar == null)
            leftBar = new LeftBar();
        return leftBar;
    }


    public Label getTitre() {
        return titre;
    }

    public LeftBar() {
        titre.getStyleClass().add("mylabel");
        getChildren().add(titre);
        setAlignment(Pos.CENTER);
        ImageView imageView2 = new ImageView("imgs/left-arrow.png");
        imageView2.setFitWidth(40);
        imageView2.setFitHeight(40);

    }
}
