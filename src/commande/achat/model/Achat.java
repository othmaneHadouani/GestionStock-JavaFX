package commande.achat.model;

import commande.models.Commande;
import produit.model.Produit;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fournisseur.model.Fournisseur;
import commande.models.LigneCommande;

import java.util.Date;
import java.util.List;

public class Achat extends Commande {

    public Achat(long code, Date date, double totale) {
        super(code, date, totale);
    }

    public Achat() {
    }
}
