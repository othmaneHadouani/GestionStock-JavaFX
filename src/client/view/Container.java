package client.view;

import com.jfoenix.controls.JFXTabPane;

import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class Container extends personne.view.Container {
    private static personne.view.Container container;
    Tab tab;
    Tab tab2;
    //    ListProd aside = ListProd.getAside();
    JFXTabPane jfxTabPane = new JFXTabPane();

    private Container() {
        BorderPane borderPane = new BorderPane();
        BorderPane borderPane2 = new BorderPane();
        borderPane.getStyleClass().add("padd");
        borderPane2.getStyleClass().add("padd");
        borderPane.setCenter(Ajout.getAjout());
        borderPane2.setCenter(ListClient.getListClient());
        tab = new Tab("Ajouter", borderPane);
        tab2 = new Tab("Lister", borderPane2);

        jfxTabPane.getTabs().addAll(tab2, tab);
        setCenter(jfxTabPane);

    }


    public static Container getContainer() {
        if (container == null)
            container = new Container();
        return (Container) container;
    }

}
