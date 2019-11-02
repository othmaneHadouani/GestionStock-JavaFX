package fournisseur.model;

import personne.model.Personne;

public class Fournisseur extends Personne {
    private String fax;

    public Fournisseur(long code, String nom, String tele, String adresse, String mail, String fax) {
        super(code, nom, tele, adresse, mail);
        this.fax = fax;
    }

    public Fournisseur() {
    }


    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
