package categorie.dao;

import categorie.model.Categorie;
import dao.Dao;

import java.util.List;

public interface CategorieDao extends Dao<Categorie> {
    List<Categorie> getByLibelle(String libelle);

}
