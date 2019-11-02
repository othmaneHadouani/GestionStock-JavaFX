package categorie.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

public class Categorie extends RecursiveTreeObject<Categorie> {
    private LongProperty id;
    private StringProperty libelle;

    public long getId() {
        return idProperty().get();
    }

    public LongProperty idProperty() {
        if (id == null)
            id = new SimpleLongProperty();
        return id;
    }

    public void setId(long id) {
        idProperty().set(id);
    }

    public String getLibelle() {
        return libelle.get();
    }

    public StringProperty libelleProperty() {
        if (libelle == null)
            libelle = new SimpleStringProperty();
        return libelle;
    }

    public void setLibelle(String libelle) {
        libelleProperty().set(libelle);
    }

    public Categorie(long id, String libelle) {
        libelleProperty().set(libelle);
        idProperty().set(id);
    }

    public Categorie(String libelle) {
        libelleProperty().set(libelle);
    }

    public Categorie() {
    }
}
