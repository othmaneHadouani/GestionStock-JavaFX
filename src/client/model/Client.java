package client.model;

import personne.model.Personne;

public class Client extends Personne {

    public Client(long code, String nom, String tele, String adresse, String mail) {
        super(code, nom, tele, adresse, mail);
    }

    public Client() {
    }
}
