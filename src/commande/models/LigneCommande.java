package commande.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import commande.vente.model.Vente;
import produit.model.Produit;

public class LigneCommande extends RecursiveTreeObject<LigneCommande> {
    private long id;
    private int quantite;
    private int oldQuantite;
    private double sousTotal;
    private Produit produit;
    private Commande commande;

    public LigneCommande(int quantite, double sousTotal) {
        this.quantite = quantite;
        this.sousTotal = sousTotal;
    }

    public LigneCommande() {
    }

    public LigneCommande(int quantite, double sousTotal, Produit produit, Commande commande) {
        this.quantite = quantite;
        this.sousTotal = sousTotal;
        this.produit = produit;
        this.commande = commande;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(double sousTotal) {
        this.sousTotal = sousTotal;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public int getOldQuantite() {
        return oldQuantite;
    }

    public void setOldQuantite(int oldQuantite) {
        this.oldQuantite = oldQuantite;
    }


}
