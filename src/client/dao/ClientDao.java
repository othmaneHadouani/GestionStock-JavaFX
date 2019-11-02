package client.dao;

import client.model.Client;
import dao.Dao;

import java.util.List;

public interface ClientDao extends Dao<Client> {
    List<Client> getByNom(String libelle);


}
