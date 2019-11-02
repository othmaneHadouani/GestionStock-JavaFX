package categorie.manager;

import categorie.model.Categorie;
import managers.Manager;

import java.util.List;

public interface CategorieManager /*extends Manager<Categorie>*/ {
    List<Categorie> getByLibelle(String libelle, boolean fromDb);

}
