package fournisseur.dao;

import dao.Dao;
import fournisseur.model.Fournisseur;

import java.util.List;

public interface FournisseurDao extends Dao<Fournisseur> {
    List<Fournisseur> getByNom(String libelle);

}
