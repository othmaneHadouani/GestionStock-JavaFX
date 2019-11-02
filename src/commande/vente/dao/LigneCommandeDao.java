package commande.vente.dao;

import dao.Dao;
import commande.models.LigneCommande;

import java.sql.SQLException;
import java.util.List;

public interface LigneCommandeDao extends Dao<LigneCommande> {
    long deleteById(LigneCommande ligneCommande) throws SQLException;

    public List<LigneCommande> getByCmdId(long Id);
}
