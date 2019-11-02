package personne.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public abstract class Personne extends RecursiveTreeObject<Personne> {
    private long id;
    private long code;
    private String nom;
    private String adresse;
    private String tele;
    private String mail;


    public Personne(long code, String nom, String adresse, String tele, String mail) {
        this.code = code;
        this.nom = nom;
        this.adresse = adresse;
        this.tele = tele;
        this.mail = mail;
    }

    public Personne() {
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
