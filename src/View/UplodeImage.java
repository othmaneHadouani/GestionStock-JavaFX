package View;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.List;

public class UplodeImage extends ImageView {
    private static UplodeImage uplodeImage;
    private File file;

    public static UplodeImage getUplodeImage() {
        if (uplodeImage == null)
            uplodeImage = new UplodeImage();
        return uplodeImage;
    }

    public File getFile() {
        return file;
    }

    public void intImage() {
        file = null;
        setImage(new Image("imgs/image-add-button.png"));
    }

    private UplodeImage() {
        setImage(new Image("imgs/image-add-button.png"));
        setFitHeight(200);
        setFitWidth(200);
        final FileChooser fileChooser = new FileChooser();
        setCursor(Cursor.CROSSHAIR);
        setOnMouseClicked(event -> {
            configureFileChooser(fileChooser);
            file = fileChooser.showOpenDialog(getScene().getWindow());
            if (file != null) {
                try {
                    setImage(new Image(new FileInputStream(file)));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }
}
