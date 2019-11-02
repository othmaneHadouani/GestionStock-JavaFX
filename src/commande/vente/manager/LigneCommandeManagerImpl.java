package commande.vente.manager;

import dao.MyConnection;
import notification.AlertSupp;
import notification.Message;
import notification.NotifEchec;
import notification.NotifSuccee;
import produit.manager.ImplProduitManager;
import commande.vente.dao.LigneCommandeDaoImpl;
import commande.models.LigneCommande;
import produit.model.Produit;

import javax.xml.soap.Node;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LigneCommandeManagerImpl /*implements LigneCommandeManager*/ {
    LigneCommandeDaoImpl ligneCommandeDao = new LigneCommandeDaoImpl();

    ImplProduitManager produitManager = ImplProduitManager.getCategorieManager();
    private Connection connection = MyConnection.getMyConnection().getConnection();


    public List<LigneCommande> get() {
        return ligneCommandeDao.get();
    }


    public void add(LigneCommande value) throws SQLException {
//        try {
        System.out.println("value =ja2 [" + value + "]");
        ligneCommandeDao.add(value);
        produitManager.diminuerQuantite(value.getProduit(), value.getQuantite());
       /* } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("Le produit dont la designation est " + value.getProduit().getDesignation() + " n'a pas été ajouté"));
        }*/
    }


    public void update(LigneCommande value) throws SQLException {
//        try {
        ligneCommandeDao.update(value);
        int n = value.getQuantite() - value.getOldQuantite();
        System.out.println("value nnnn= [" + n + "]");
        if (n > 0) {
            produitManager.diminuerQuantite(produitManager.getById(value.getProduit().getId(), false), n);
        } else
            produitManager.augmenterQuantite(produitManager.getById(value.getProduit().getId(), false), n * -1);
       /* } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("Le produit dont la designation est " + value.getProduit().getDesignation() + " n'a pas été modifié"));
        }*/
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
                    produitManager.augmenterQuantite(produit, ligneCommande.getQuantite());
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
