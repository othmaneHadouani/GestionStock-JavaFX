package View;

import categorie.model.Categorie;
import client.model.Client;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class menu extends VBox {
    JFXButton accueil = new JFXButton("Accueil", new ImageView(new Image("imgs/menu/home.png")));
    JFXButton Produits = new JFXButton("Produits", new ImageView(new Image("imgs/menu/box.png")));
    JFXButton Categories = new JFXButton("Categories", new ImageView(new Image("imgs/menu/2-squares.png")));
    JFXButton Clients = new JFXButton("Clients", new ImageView(new Image("imgs/menu/customer.png")));
    JFXButton Fournisseurs = new JFXButton("Fournisseurs", new ImageView(new Image("imgs/menu/delivery.png")));
    JFXButton Achat = new JFXButton("Achat", new ImageView(new Image("imgs/menu/shopping-bag.png")));
    JFXButton Vente = new JFXButton("Vente", new ImageView(new Image("imgs/menu/shopping-cart.png")));
    Label text = new Label("Magasin");
    VBox vBoxm;
    Node active;

    public menu(double spacing) {
        super(spacing);
        accueil.setContentDisplay(ContentDisplay.TOP);
        accueil.getStyleClass().add("acc");
        Produits.setAlignment(Pos.BASELINE_LEFT);
        Produits.setGraphicTextGap(16);
        Categories.setAlignment(Pos.BASELINE_LEFT);
        Categories.setGraphicTextGap(16);
        active = accueil;
        Clients.setAlignment(Pos.BASELINE_LEFT);
        Clients.setGraphicTextGap(16);
        accueil.getStyleClass().add("active");
        Fournisseurs.setAlignment(Pos.BASELINE_LEFT);
        Fournisseurs.setGraphicTextGap(16);

        Achat.setAlignment(Pos.BASELINE_LEFT);
        Achat.setGraphicTextGap(16);

        Vente.setAlignment(Pos.BASELINE_LEFT);
        Vente.setGraphicTextGap(16);

        vBoxm = new VBox(Produits, Categories, Clients, Fournisseurs, Achat, Vente);
        accueil.setOnMouseClicked(event -> {
            Body.getBody().getContainer().setCenter(null);
            LeftBar.getLeftBar().getTitre().setText("");
            setActive(accueil);
        });
        Produits.setOnMouseClicked(event -> {
            Body.getBody().getContainer().setCenter(produit.view.Body.getBody());
            LeftBar.getLeftBar().getTitre().setText("Gestion des produits");
            setActive(Produits);
        });
        Categories.setOnMouseClicked(event -> {
            Body.getBody().getContainer().setCenter(categorie.view.Body.getBody());
            LeftBar.getLeftBar().getTitre().setText("Gestion des categories");
            setActive(Categories);
        });
        Clients.setOnMouseClicked(event -> {
            Body.getBody().getContainer().setCenter(client.view.Body.getBody());
            LeftBar.getLeftBar().getTitre().setText("Gestion des clients");
            setActive(Clients);
        });
        Fournisseurs.setOnMouseClicked(event -> {
            Body.getBody().getContainer().setCenter(fournisseur.view.Body.getBody());
            LeftBar.getLeftBar().getTitre().setText("Gestion des fournisseurs");
            setActive(Fournisseurs);

        });
        Achat.setOnMouseClicked(event -> {
            Body.getBody().getContainer().setCenter(commande.achat.view.Body.getBody());
            LeftBar.getLeftBar().getTitre().setText("Gestion des achats");
            setActive(Achat);

        });
        Vente.setOnMouseClicked(event -> {
            Body.getBody().getContainer().setCenter(commande.vente.view.Body.getBody());
            LeftBar.getLeftBar().getTitre().setText("Gestion des ventes");

            setActive(Vente);

        });

        setPadding(new Insets(0));
        vBoxm.getStyleClass().add("items");
        text.getStyleClass().add("header");
        getChildren().add(text);
        getChildren().add(accueil);
        getChildren().add(vBoxm);
        getStyleClass().add("menu");

    }

    public void setActive(Node active) {
        this.active.getStyleClass().remove("active");
        active.getStyleClass().add("active");
        this.active = active;
    }
}
