package commande.vente.manager;

import commande.models.Commande;
import commande.vente.view.Body;
import commande.vente.view.ListCommande;
import dao.MyConnection;
import fournisseur.model.Fournisseur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import commande.vente.dao.CommandeDaoImpl;
import commande.vente.model.Vente;
import commande.models.LigneCommande;
import javafx.scene.Node;
import notification.AlertSupp;
import notification.Message;
import notification.NotifEchec;
import notification.NotifSuccee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CommandeManagerImpl /*implements CommandeManager*/ {
    CommandeDaoImpl commandeDao = new CommandeDaoImpl();
    LigneCommandeManagerImpl ligneCommandeManager = new LigneCommandeManagerImpl();
    private ObservableList<Commande> ventes = FXCollections.observableArrayList();
    private Connection connection = MyConnection.getMyConnection().getConnection();

    private static CommandeManagerImpl commandeManager;

    public static CommandeManagerImpl getCommandeManager() {
        if (commandeManager == null)
            commandeManager = new CommandeManagerImpl();
        return commandeManager;
    }

    private CommandeManagerImpl() {
        init();
    }

    public ObservableList<Commande> init() {
        ventes.setAll(commandeDao.get());
        return ventes;
    }

    public ObservableList<Commande> get() {
        return ventes;
    }


    public void add(Vente value) {
        try {
            connection.setAutoCommit(false);

            commandeDao.add(value);
            List<LigneCommande> ligneCommandes = value.getLigneCommandes();
            value.setDate(new Date());
            ligneCommandes.stream().forEach(ligneCommande -> {
                ligneCommande.setCommande(value);
                try {
                    ligneCommandeManager.add(ligneCommande);
                } catch (SQLException e) {
                    NotifEchec.getNotifEchec().show(Message.get().getMessage("Le produit dont la designation est " + value.getProduit().getDesignation() + " n'a pas été ajouté"));
                }
            });
            ventes.add(0, value);
            List list = ventes.stream().collect(Collectors.toList());
            ventes.setAll(list);
//            ListCommande.getListCommande().getReglementView().setVente(value);
            Body.getBody().getJfxTabPane().getSelectionModel().select(0);
            ListCommande.getListCommande().getTableCommande().getSelectionModel().select(0);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infoaddsuccCMD"));

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infoaddechecCMD"));
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {

            }
        }
    }


    public void update(Vente value) {
        try {
            connection.setAutoCommit(false);

            commandeDao.update(value);
            List<LigneCommande> ligneCommandes = value.getLigneCommandes();
            ligneCommandes.stream().forEach(ligneCommande -> {
                ligneCommande.setCommande(value);
                if (ligneCommande.getId() == 0) {
                    try {
                        ligneCommandeManager.add(ligneCommande);
                    } catch (SQLException e) {
                        NotifEchec.getNotifEchec().show(Message.get().getMessage("Le produit dont la designation est " + value.getProduit().getDesignation() + " n'a pas été ajouté"));
                    }
                } else
                    try {
                        ligneCommandeManager.update(ligneCommande);
                    } catch (SQLException e) {
                        NotifEchec.getNotifEchec().show(Message.get().getMessage("Le produit dont la designation est " + value.getProduit().getDesignation() + " n'a pas été modifié"));
                    }
            });
            ventes.remove(value);
            ventes.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosetsuccCMD"));
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosetechecCMD"));
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {

            }
        }
    }


    public Commande getById(long Id, boolean fromDb) {
        if (fromDb)
            return commandeDao.getById(Id);
        else
            return ventes.stream().filter(commande -> commande.getId() == Id).findFirst().orElse(null);
        /* fkhatrkom {
            for (Commande v : commandes) {
                if (v.getId() == Id) {
                    return v;
                }
            }
            return null;
        }*/
    }

    public void deleteById(Node node, Vente vente) {
        AlertSupp.getAlertSupp().show(node, o -> {
            try {
                long nb = commandeDao.deleteById(vente.getId());
                if (nb > 0) {
                    ventes.remove(vente);
                    NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosuppsucc"));
                    return;
                }
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            } catch (SQLException e) {
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            }
        });
    }
}
