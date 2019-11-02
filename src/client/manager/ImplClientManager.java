package client.manager;


import client.dao.ClientDao;
import client.dao.ImplClientDao;
import client.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import notification.AlertSupp;
import notification.Message;
import notification.NotifEchec;
import notification.NotifSuccee;
import personne.model.Personne;

import java.sql.SQLException;
import java.util.List;

public class ImplClientManager implements ClientManager {

    ClientDao clientDao = new ImplClientDao();


    protected ObservableList<Personne> personnes = FXCollections.observableArrayList();
    private static ImplClientManager fournisseurManager;

    public static ImplClientManager getClientManager() {
        if (fournisseurManager == null)
            fournisseurManager = new ImplClientManager();
        return fournisseurManager;
    }

    private ImplClientManager() {
        init();
    }

    public ObservableList<Personne> init() {
        personnes.setAll(clientDao.get());
        return personnes;
    }

    public ObservableList<Personne> get() {
        return personnes;
    }


    public void add(Client value) {
        try {
            clientDao.add(value);
            personnes.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infoaddsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infoaddechec"));
        }
    }


    public void update(Client value) {
        try {
            clientDao.update(value);

            personnes.remove(value);
            personnes.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosetsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosetechec"));
        }
    }


    public Personne getById(long Id, boolean fromDb) {
        if (fromDb)
            return clientDao.getById(Id);
        else
            return personnes.stream().filter(fournisseur -> fournisseur.getId() == Id).findFirst().orElse(null);
    }


    public void deleteById(Node node, Client fournisseur) {
        AlertSupp.getAlertSupp().show(node, o -> {
            try {
                long nb = clientDao.deleteById(fournisseur.getId());
                if (nb > 0) {
                    personnes.remove(fournisseur);
                    NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosuppsucc"));
                    return;
                }
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            } catch (SQLException e) {
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            }
        });
    }


    public List<Client> chercherParNom(String dis) {

        return clientDao.getByNom(dis);
    }


}
