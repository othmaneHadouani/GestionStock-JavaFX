package client.manager;

import client.model.Client;
import managers.Manager;

import java.util.List;


public interface ClientManager /*extends Manager<Client> */{
    public List<Client> chercherParNom(String dis);
}
