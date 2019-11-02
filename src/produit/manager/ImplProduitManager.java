package produit.manager;


import fournisseur.model.Fournisseur;
import javafx.scene.Node;
import notification.AlertSupp;
import notification.Message;
import notification.NotifEchec;
import notification.NotifSuccee;
import produit.dao.ImplProduitDao;
import produit.dao.ProduitDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import produit.model.Produit;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ImplProduitManager implements ProduitManager {

    ProduitDao produitDao = new ImplProduitDao();

    private ObservableList<Produit> produits = FXCollections.observableArrayList();

    private static ImplProduitManager categorieManager;

    public static ImplProduitManager getCategorieManager() {
        if (categorieManager == null)
            categorieManager = new ImplProduitManager();
        return categorieManager;
    }

    private ImplProduitManager() {
        init();
    }

    public ObservableList<Produit> init() {
        produits.setAll(produitDao.get());
        return produits;
    }

    public ObservableList<Produit> get() {
        return produits;
    }


    public void add(Produit value) {
        try {
            produitDao.add(value);
            produits.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infoaddsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infoaddechec"));
        }
    }


    public void update(Produit value) {
        try {
            produitDao.update(value);
            produits.remove(value);
            produits.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosetsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosetechec"));
        }
    }

    @Override
    public boolean diminuerQuantite(Produit produit, int quantite) {
        boolean b = produitDao.diminuerQuantite(produit.getId(), quantite);
        if (b) {
            produit.setQuantite(produit.getQuantite() - quantite);
            produits.remove(produit);
            produits.add(produit);
        }
        return b;
    }

    @Override
    public boolean augmenterQuantite(Produit produit, int quantite) {
        boolean b = produitDao.augmenterQuantite(produit.getId(), quantite);
        if (b) {
            produit.setQuantite(produit.getQuantite() + quantite);
            produits.remove(produit);
            produits.add(produit);
        }
        return b;
    }

    public Produit getById(long Id, boolean fromDb) {
        if (fromDb)
            return produitDao.getById(Id);
        else
            return produits.stream().filter(categorie -> categorie.getId() == Id).findFirst().orElse(null);
    }


    public void deleteById(Node node, Produit produit) {
        AlertSupp.getAlertSupp().show(node, o -> {
            try {
                long nb = produitDao.deleteById(produit.getId());
                if (nb > 0) {
                    produits.remove(produit);
                    NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosuppsucc"));
                    try {
                        File file1 = new File(System.getProperty("user.home") + "/" + produit.getId() + ".jpg");
                        file1.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            } catch (SQLException e) {
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            }
        });
    }
/*
    @Override
    public List<Produit> get() {
        return produitDao.get();
    }

    @Override
    public long add(Produit value) {
        return produitDao.add(value);
    }

    @Override
    public boolean update(Produit value) {
        return produitDao.update(value);
    }

    @Override
    public Produit getById(long Id) {
        return produitDao.getById(Id);
    }

    @Override
    public long deleteById(long Id) {
        return produitDao.deleteById(Id);
    }*/


    public List<Produit> chercherParDesignation(String dis) {

        return produitDao.getByDesignation(dis);
    }

    @Override
    public List<Produit> getByCategorie(long idCat) {
        return produitDao.getByCategorie(idCat);
    }
}
