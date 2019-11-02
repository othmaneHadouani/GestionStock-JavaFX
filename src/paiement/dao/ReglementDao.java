package paiement.dao;


import dao.Dao;
import paiement.model.Reglement;

import java.util.List;

public interface ReglementDao extends Dao<Reglement> {
    List<Reglement> getPaimentParIdCommande(long id);

}
