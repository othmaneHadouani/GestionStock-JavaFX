package commande.vente.model;

import client.model.Client;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import commande.models.Commande;
import commande.models.LigneCommande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paiement.model.Reglement;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.function.BinaryOperator;

public class Vente extends Commande {

    ObservableList<Reglement> reglements;

    public Vente(long code, Date date, double totale) {
        super(code, date, totale);
    }

    public Vente() {
    }

    public ObservableList<Reglement> getReglements() {
        return reglements;
    }

    public void setReglements(Collection<Reglement> reglements) {
        if (this.reglements == null)
            this.reglements = FXCollections.observableArrayList();

        this.reglements.setAll(reglements);
    }

    public double calculeReste() {
        Reglement red = new Reglement();
        reglements.stream().reduce(red, (reglement, reglement2) -> {
            reglement.setMontant(reglement.getMontant() + reglement2.getMontant());
            return reglement;
        });
        return getTotal() - red.getMontant();
    }
}
