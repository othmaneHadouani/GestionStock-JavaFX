package paiement.manager;

import categorie.model.Categorie;
import paiement.model.Reglement;

import java.util.List;

public interface ReglementManager /*extends Manager<Categorie>*/ {
    List<Reglement> getPaimentParIdCommande(long id);
}
