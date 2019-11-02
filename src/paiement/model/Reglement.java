package paiement.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import commande.models.Commande;

import java.util.Date;

public class Reglement extends RecursiveTreeObject<Reglement> {

    private long id;
    private long numero;
    private double montant;
    private String type;
    private String banque = "";
    private String proprietaire = "";
    private Date date;

    private String numeroCarte;
    private String code;

    private long id_v;

    public Reglement() {
    }

    public Reglement(long id, double montant) {
        this.id = id;
        this.montant = montant;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getBanque() {
        return banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public long getId_v() {
        return id_v;
    }

    public void setId_v(long id_v) {
        this.id_v = id_v;
    }
}
