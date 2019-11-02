//import View.Body;

import View.Body;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Body body = new Body();
        Body body = Body.getBody();

        body.getStyleClass().add("scene");
        BorderPane borderPane = new BorderPane();
//        Produit.view.Body body = new Produit.view.Body();
        Scene scene = new Scene(body, 1400, 820);
        scene.getStylesheets().add(getClass().getResource("css/StyleCss.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
//        primaryStage.close();
       /* primaryStage.getScene().getRoot().setEffect(new DropShadow());
        primaryStage.getScene().setFill(Color.TRANSPARENT);*/

        body.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
                System.out.println("event = [" + xOffset + "]");
            }
        });
        body.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("event = [" + event.getScreenX() + "]");

                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
