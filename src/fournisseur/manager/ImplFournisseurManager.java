package fournisseur.manager;


import client.model.Client;
import fournisseur.dao.FournisseurDao;
import fournisseur.dao.ImplFournisseurDao;
import fournisseur.model.Fournisseur;
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

public class ImplFournisseurManager implements FournisseurManager {

    FournisseurDao fournisseurDao = new ImplFournisseurDao();

    protected ObservableList<Personne> personnes = FXCollections.observableArrayList();
    private static ImplFournisseurManager fournisseurManager;

    public static ImplFournisseurManager getFournisseurManager() {
        if (fournisseurManager == null)
            fournisseurManager = new ImplFournisseurManager();
        return fournisseurManager;
    }

    private ImplFournisseurManager() {
        init();
    }

    public ObservableList<Personne> init() {
        personnes.setAll(fournisseurDao.get());
        return personnes;
    }

    public ObservableList<Personne> get() {
        return personnes;
    }


    public void add(Fournisseur value) {
        try {
            fournisseurDao.add(value);
            personnes.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infoaddsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infoaddechec"));
        }
    }


    public void update(Fournisseur value) {
        try {
            fournisseurDao.update(value);
            personnes.remove(value);
            personnes.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosetsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosetechec"));
        }
    }


    public Personne getById(long Id, boolean fromDb) {
        if (fromDb)
            return fournisseurDao.getById(Id);
        else
            return personnes.stream().filter(fournisseur -> fournisseur.getId() == Id).findFirst().orElse(null);
    }


    public void deleteById(Node node, Fournisseur fournisseur) {
        AlertSupp.getAlertSupp().show(node, o -> {
            try {
                long nb = fournisseurDao.deleteById(fournisseur.getId());
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


    public List<Fournisseur> chercherParNom(String dis) {

        return fournisseurDao.getByNom(dis);
    }

}
