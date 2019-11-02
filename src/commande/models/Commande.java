package commande.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import commande.achat.model.Achat;
import fournisseur.model.Fournisseur;
import personne.model.Personne;
import produit.model.Produit;

import java.util.Date;
import java.util.List;

public abstract class Commande extends RecursiveTreeObject<Commande> {
    private long id;
    private int quantite;
    private Date date;
    private Personne personne;
    private Produit produit;
    private double total;
    private List<LigneCommande> ligneCommandes;

    public Commande(long code, Date date, double totale) {
        this.id = code;
        this.date = date;
        this.total = totale;
    }

    public Commande() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }
}
