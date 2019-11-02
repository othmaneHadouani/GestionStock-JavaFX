package paiement.socketClient.data;

import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String nom;
    private String prenom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        return "Client{" +
//                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    public void setPrenom(String prenom) {

        this.prenom = prenom;
    }
}
