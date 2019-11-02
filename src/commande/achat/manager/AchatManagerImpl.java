package commande.achat.manager;

import commande.achat.dao.AchatDaoImpl;
import commande.achat.model.Achat;
import commande.models.Commande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import commande.models.LigneCommande;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AchatManagerImpl /*implements AchatManager*/ {
    AchatDaoImpl achatDao = new AchatDaoImpl();

    private ObservableList<Commande> achats = FXCollections.observableArrayList();
    LigneCommandeManagerImpl ligneCommandeManager = new LigneCommandeManagerImpl();
    private static AchatManagerImpl achatManager;

    public static AchatManagerImpl getAchatManager() {
        if (achatManager == null)
            achatManager = new AchatManagerImpl();
        return achatManager;
    }

    private AchatManagerImpl() {
        init();
    }

    public ObservableList<Commande> init() {
        achats.setAll(achatDao.get());
        return achats;
    }

    public ObservableList<Commande> get() {
        return achats;
    }

    public void add(Achat value) {
        try {
            achatDao.add(value);

            List<LigneCommande> ligneCommandes = value.getLigneCommandes();
            value.setDate(new Date());
            ligneCommandes.stream().forEach(ligneCommande -> {
                ligneCommande.setCommande(value);
                ligneCommandeManager.add(ligneCommande);
            });
            achats.add(value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(Achat value) {
        try {
            achatDao.update(value);
            List<LigneCommande> ligneCommandes = value.getLigneCommandes();
            ligneCommandes.stream().forEach(ligneCommande -> {
                ligneCommande.setCommande(value);
                if (ligneCommande.getId() == 0) {
                    ligneCommandeManager.add(ligneCommande);
                } else
                    ligneCommandeManager.update(ligneCommande);
            });
            achats.remove(value);
            achats.add(value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Commande getById(long Id, boolean fromDb) {
        if (fromDb)
            return achatDao.getById(Id);
        else
            return achats.stream().filter(achat -> achat.getId() == Id).findFirst().orElse(null);
        /* fkhatrkom {
            for (Achat v : achats) {
                if (v.getId() == Id) {
                    return v;
                }
            }
            return null;
        }*/
    }


    public void deleteById(Achat achat) {
        try {
            long nb = achatDao.deleteById(achat.getId());
            if (nb > 0) {
                achats.remove(achat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
