package View;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class WindowBar extends BorderPane {

    JFXButton close;
    JFXButton minus;
    JFXButton ag;
    JFXButton acc;

    public WindowBar() {
        ImageView imageView = new ImageView(new Image("imgs/imageWB/cross.png"));
        ImageView imageView2 = new ImageView(new Image("imgs/imageWB/multi-tab.png"));
        ImageView imageView3 = new ImageView(new Image("imgs/imageWB/substract.png"));
        ImageView imageView4 = new ImageView(new Image("imgs/imageWB/round-account.png"));

        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        imageView2.setFitWidth(16);
        imageView2.setFitHeight(16);
        imageView3.setFitWidth(16);
        imageView3.setFitHeight(16);
        close = new JFXButton("", imageView);
        minus = new JFXButton("", imageView3);
        ag = new JFXButton("", imageView2);
        acc = new JFXButton("", imageView4);
        HBox hBox = new HBox(3, minus, ag, close);
        HBox hBox2 = new HBox(3, acc);
        HBox hBox1 = new HBox(20, hBox2, hBox);
        setRight(hBox1);
        close.setOnAction(event -> Platform.exit());
//        minus.setOnAction(event -> Platform);
        getStyleClass().add("winBar");
    }

}
