package commande.achat.manager;

import notification.AlertSupp;
import notification.Message;
import notification.NotifEchec;
import notification.NotifSuccee;
import produit.manager.ImplProduitManager;
import commande.vente.dao.LigneCommandeDaoImpl;
import commande.models.LigneCommande;
import produit.model.Produit;

import java.sql.SQLException;
import java.util.List;

public class LigneCommandeManagerImpl implements LigneCommandeManager {
    LigneCommandeDaoImpl ligneCommandeDao = new LigneCommandeDaoImpl();

    ImplProduitManager produitManager = ImplProduitManager.getCategorieManager();
    LigneCommandeManager commandeManager = new LigneCommandeManagerImpl();

    public List<LigneCommande> get() {
        return ligneCommandeDao.get();
    }


    public void add(LigneCommande value) {
        try {
            ligneCommandeDao.add(value);
            produitManager.augmenterQuantite(value.getProduit(), value.getQuantite());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(LigneCommande value) {
        try {
            ligneCommandeDao.update(value);
            int n = value.getQuantite() - value.getOldQuantite();
            if (n > 0) {
                produitManager.augmenterQuantite(produitManager.getById(value.getProduit().getId(), false), n);
            } else
                produitManager.diminuerQuantite(produitManager.getById(value.getProduit().getId(), false), n * -1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public LigneCommande getById(long Id) {
        return ligneCommandeDao.getById(Id);
    }


    public void deleteById(javafx.scene.Node node, LigneCommande ligneCommande) {
        AlertSupp.getAlertSupp().show(node, o -> {
            try {
                long l = ligneCommandeDao.deleteById(ligneCommande.getId());
                if (l > 0) {
                    Produit produit = produitManager.getById(ligneCommande.getProduit().getId(), false);
                    produitManager.diminuerQuantite(produit, ligneCommande.getQuantite());
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
