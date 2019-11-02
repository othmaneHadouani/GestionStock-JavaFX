package View;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TableHead extends BorderPane {
    private JFXTextField search = new JFXTextField();

    private Label label;

    public TableHead(String titre) {
        label = new Label(titre);
        label.getStyleClass().add("titre");
        search.setPromptText("Rechercher");
        setLeft(label);
        setRight(search);
    }

    public Label getLabel() {
        return label;
    }

    public JFXTextField getSearch() {
        return search;
    }
}
