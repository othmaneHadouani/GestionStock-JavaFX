package View;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class TableActions extends HBox {
    private final JFXButton deleteButton = new JFXButton("Delete");
    private final JFXButton updateButton = new JFXButton("Update");
    private StackPane pane = new StackPane(deleteButton);

    public TableActions() {

        ImageView imageView = new ImageView("imgs/dustbin.png");
        imageView.setFitWidth(22);
        imageView.setFitHeight(22);
        deleteButton.setGraphic(imageView);
        deleteButton.setText("");
        ImageView imageView2 = new ImageView("imgs/edit.png");
        imageView2.setFitWidth(22);
        imageView2.setFitHeight(22);
        updateButton.setGraphic(imageView2);
        updateButton.setText("");

        getChildren().add(deleteButton);
        getChildren().add(updateButton);
    }

    public JFXButton getDeleteButton() {
        return deleteButton;
    }

    public JFXButton getUpdateButton() {
        return updateButton;
    }

    public StackPane getPane() {
        return pane;
    }
}
