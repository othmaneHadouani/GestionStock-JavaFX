package commande.vente.dao;

import dao.Dao;
import commande.vente.model.Vente;

import java.util.List;

public interface CommandeDao extends Dao<Vente> {
    public List<Vente> getIsNullIdCli();

    public List<Vente> getIsNotNullIdCli();
}
