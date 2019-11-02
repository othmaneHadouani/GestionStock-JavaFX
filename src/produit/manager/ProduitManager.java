package produit.manager;

import managers.Manager;
import produit.model.Produit;

import java.util.List;


public interface ProduitManager /*extends Manager<Produit> */ {
    public List<Produit> chercherParDesignation(String dis);

    List<Produit> getByCategorie(long idCat);

    public boolean diminuerQuantite(Produit produit, int quantite);

    public boolean augmenterQuantite(Produit produit, int quantite);

}
