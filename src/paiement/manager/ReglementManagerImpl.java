package paiement.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import notification.AlertSupp;
import notification.Message;
import notification.NotifEchec;
import notification.NotifSuccee;
import paiement.dao.ReglementDaoImpl;
import paiement.model.Reglement;
import paiement.socketClient.data.Compte;
import paiement.socketClient.services.EmissionServices;
import paiement.socketClient.services.ReceptionServices;
import paiement.socketClient.sockets.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ReglementManagerImpl implements ReglementManager {
    ReglementDaoImpl reglementDao = new ReglementDaoImpl();

    private ObservableList<Reglement> reglements = FXCollections.observableArrayList();

    EmissionServices emissionServices;
    ReceptionServices receptionServices;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    Client client;


    private static ReglementManagerImpl reglementManager;

    public static ReglementManagerImpl getReglementManager() {
        if (reglementManager == null)
            reglementManager = new ReglementManagerImpl();
        return reglementManager;
    }

    private ReglementManagerImpl() {
        client = new Client("localhost", 3000);

        try {
            outputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
            inputStream = new ObjectInputStream(client.getSocket().getInputStream());

            emissionServices = new EmissionServices(outputStream, inputStream);
//            receptionServices = new ReceptionServices(inputStream);
//            Thread thread = new Thread(receptionServices);
//            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Reglement> get() {
        System.out.println(reglements.size());
        return reglements;
    }


    public void add(Reglement value) {
        try {
            reglementDao.add(value);
            reglements.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infoaddsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infoaddechec"));
        }
    }


    public void update(Reglement value) {
        try {
            reglementDao.update(value);
            reglements.remove(value);
            reglements.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosetsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosetechec"));
        }
    }


    public Reglement getById(long Id, boolean fromDb) {
        if (fromDb)
            return reglementDao.getById(Id);
        else
            return reglements.stream().filter(reglement -> reglement.getId() == Id).findFirst().orElse(null);
        /* fkhatrkom {
            for (Reglement v : reglements) {
                if (v.getId() == Id) {
                    return v;
                }
            }
            return null;
        }*/
    }


    public void deleteById(Node jfxButton, Reglement reglement) {
        AlertSupp.getAlertSupp().show(jfxButton, o -> {
            try {
                long nb = reglementDao.deleteById(reglement.getId());
                if (nb > 0) {
                    reglements.remove(reglement);
                    NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosuppsucc"));
                    return;
                }
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            } catch (SQLException e) {
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            }
        });
    }


    public List<Reglement> getPaimentParIdCommande(long id_v) {
        List list = reglementDao.getPaimentParIdCommande(id_v);
        reglements.setAll(list);
        return list;
    }

    public Compte TestSolde(Compte c) {
        return emissionServices.Tester(c);
    }

    public Compte debiter(Compte compte, double apayer) {
        return emissionServices.debiter(compte, apayer);
    }
}
