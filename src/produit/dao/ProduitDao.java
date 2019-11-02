package produit.dao;


import dao.Dao;
import produit.model.Produit;

import java.util.List;

public interface ProduitDao extends Dao<Produit> {
    List<Produit> getByDesignation(String designation);

    List<Produit> getByCategorie(long idCat);

    boolean diminuerQuantite(long id, int quantite);

    boolean augmenterQuantite(long id, int quantite);

}
