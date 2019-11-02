package fournisseur.manager;

import fournisseur.model.Fournisseur;
import managers.Manager;

import java.util.List;


public interface FournisseurManager /*extends Manager<Fournisseur>*/ {
    public List<Fournisseur> chercherParNom(String dis);
}
