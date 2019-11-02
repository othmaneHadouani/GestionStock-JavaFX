package categorie.manager;

import categorie.dao.CategorieDaoImpl;
import categorie.model.Categorie;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import notification.AlertSupp;
import notification.Message;
import notification.NotifEchec;
import notification.NotifSuccee;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CategorieManagerImpl implements CategorieManager {
    CategorieDaoImpl categorieDao = new CategorieDaoImpl();

    private ObservableList<Categorie> categories = FXCollections.observableArrayList();

    private static CategorieManagerImpl categorieManager;

    public static CategorieManagerImpl getCategorieManager() {
        if (categorieManager == null)
            categorieManager = new CategorieManagerImpl();
        return categorieManager;
    }

    private CategorieManagerImpl() {
        init();
    }

    public ObservableList<Categorie> init() {
        categories.setAll(categorieDao.get());
        return categories;
    }

    public ObservableList<Categorie> get() {
        return categories;
    }


    public void add(Categorie value) {
        try {
            categorieDao.add(value);
            categories.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infoaddsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infoaddechec"));
        }
    }


    public void update(Categorie value) {
        try {
            categorieDao.update(value);
            categories.remove(value);
            categories.add(value);
            NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosetsucc"));
        } catch (SQLException e) {
            NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosetechec"));
        }
    }


    public Categorie getById(long Id, boolean fromDb) {
        if (fromDb)
            return categorieDao.getById(Id);
        else
            return categories.stream().filter(categorie -> categorie.getId() == Id).findFirst().orElse(null);
        /* fkhatrkom {
            for (Categorie v : categories) {
                if (v.getId() == Id) {
                    return v;
                }
            }
            return null;
        }*/
    }


    public void deleteById(Node jfxButton, Categorie categorie) {
        AlertSupp.getAlertSupp().show(jfxButton, o -> {
            try {
                long nb = categorieDao.deleteById(categorie.getId());
                if (nb > 0) {
                    categories.remove(categorie);
                    NotifSuccee.getNotifSuccee().show(Message.get().getMessage("message.infosuppsucc"));
                    return;
                }
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            } catch (SQLException e) {
                NotifEchec.getNotifEchec().show(Message.get().getMessage("message.infosuppech"));
            }
        });
    }


    public List<Categorie> getByLibelle(String libelle, boolean fromDb) {
        if (fromDb)
            return categorieDao.getByLibelle(libelle);
        else
            return categories.stream().filter(categorie -> categorie.getLibelle().contains(libelle)).collect(Collectors.toList());
    }
}
