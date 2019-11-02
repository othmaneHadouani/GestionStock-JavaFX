package produit.view;

import categorie.manager.CategorieManagerImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import produit.model.Produit;

import java.io.File;
import java.util.ResourceBundle;

public class Vproduit extends VBox {

    private static Vproduit vproduit;
    private Label desv = new Label();
    private Label catv = new Label();
    private Label Pvv = new Label();
    private Label Pav = new Label();
    private ImageView imageView = new ImageView();
    VBox vBox = new VBox();

    private Vproduit() {
    }

    private Vproduit(double spacing) {
        super(spacing);
        setPadding(new Insets(10));
        GridPane gridPane = new GridPane();
        Label desL = new Label("Designation :");
        Label catL = new Label("Categorie    :");
        Label PvL = new Label("Prix vente   :");
        Label PaL = new Label("Prix Achat   :");
        gridPane.add(desL, 0, 0);
        gridPane.add(catL, 0, 1);
        gridPane.add(PvL, 0, 2);
        gridPane.add(PaL, 0, 3);
        gridPane.add(desv, 1, 0);
        gridPane.add(catv, 1, 1);
        gridPane.add(Pvv, 1, 2);
        gridPane.add(Pav, 1, 3);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        getChildren().add(gridPane);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(imageView);
        this.getStyleClass().add("vproduit");
    }

    public static Vproduit getVproduitInstance() {
        if (vproduit == null)
            vproduit = new Vproduit(10);
        return vproduit;
    }

    public Label getDesv() {
        return desv;
    }

    public void setDesv(String desv) {
        this.desv.setText(desv);
    }

    public Label getCatv() {
        return catv;
    }

    public void setCatv(String catv) {
        this.catv.setText(catv);
    }

    public Label getPvv() {
        return Pvv;
    }

    public void setPvv(String pvv) {
        Pvv.setText(pvv);
    }

    public Label getPav() {
        return Pav;
    }

    public void setPav(String pav) {
        Pav.setText(pav);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        try {
            File file = new File(System.getProperty("user.home").replaceAll("\\\\", "/") + "/" + imageView + ".jpg");
            System.out.println("file:" + file.getAbsolutePath());
            if (file.exists()) {
                this.imageView.setImage(new Image("file:" + file.getAbsolutePath()));
                this.imageView.setFitWidth(250);
                this.imageView.setFitHeight(250);
            } else {
                this.imageView.setImage(new Image("imgs/picture(1).png"));
                this.imageView.setFitWidth(100);
                this.imageView.setFitHeight(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.imageView.setImage(new Image("imgs/picture.png"));
        }
    }

    public void setFields(Produit produit) {
        setDesv(produit.getDesignation());
        setPav(produit.getPrixA() + "");
        setPvv(produit.getPrixV() + "");

        CategorieManagerImpl.getCategorieManager().get().stream().filter(categorie -> categorie.getId() == produit.getCategorie().getId()).forEach(categorie -> {
            setCatv(categorie.getLibelle() + "");
        });
        setImageView("" + produit.getId());
    }
}
