package produit.model;

import categorie.model.Categorie;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import models.Image;

public class Produit extends RecursiveTreeObject<Produit> {
    private long id;
    private long code;
    private String designation;
    private double prixA;
    private double prixV;
    private Image image;
    private Categorie categorie;
    private int quantite;

    public Produit(long code, String designation, double prixA, double prixV) {
        this.code = code;
        this.designation = designation;
        this.prixA = prixA;
        this.prixV = prixV;
    }

    public Produit(long code, String designation, double prixA, double prixV, Categorie categorie) {
        this.code = code;
        this.designation = designation;
        this.prixA = prixA;
        this.prixV = prixV;
        this.categorie = categorie;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Produit() {
    }

    public Produit(long id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrixA() {
        return prixA;
    }

    public void setPrixA(double prixA) {
        this.prixA = prixA;
    }

    public double getPrixV() {
        return prixV;
    }

    public void setPrixV(double prixV) {
        this.prixV = prixV;
    }

    public void setId_cat(Categorie selectedItem) {
    }
}
