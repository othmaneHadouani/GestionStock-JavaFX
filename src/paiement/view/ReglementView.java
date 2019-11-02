package paiement.view;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import commande.vente.model.Vente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.Collation;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import paiement.dao.ReglementDaoImpl;
import paiement.manager.ReglementManagerImpl;
import paiement.model.Reglement;
import paiement.model.TypeReglement;
import paiement.socketClient.data.Compte;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class ReglementView extends VBox {
    public JFXTreeTableView<Reglement> tableReglement;
    JFXTreeTableColumn<Reglement, Double> montantColumn;
    JFXTreeTableColumn<Reglement, String> dateColumn;
    JFXTreeTableColumn<Reglement, String> typeColumn;
    public JFXButton ajouter = new JFXButton("Payer");
    public JFXButton annuler = new JFXButton("Annuler");
    HBox hBox2, hBox3;
    public JFXTextField reste = new JFXTextField();
    public JFXTextField apayer = new JFXTextField();
    public JFXTextField numero = new JFXTextField();
    public JFXTextField banque = new JFXTextField();
    public JFXTextField proprie = new JFXTextField();

    public JFXTextField numeroCarte = new JFXTextField();
    public JFXTextField code = new JFXTextField();

    //    public JFXTextField date = new JFXTextField();
    ObservableList<String> typeReglements = FXCollections.observableArrayList();
    JFXComboBox<String> jfxComboBox = new JFXComboBox<>();
    Vente vente;
    EventHandler<ActionEvent> addEnespece;
    EventHandler<ActionEvent> addParCheque;
    EventHandler<KeyEvent> addEnespeceKey;
    EventHandler<KeyEvent> addParChequeKey;
    EventHandler<KeyEvent> addEnligneKey;
    EventHandler<ActionEvent> addEnLigne;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
    ReglementManagerImpl reglementManager = ReglementManagerImpl.getReglementManager();
    RecursiveTreeItem<Reglement> recursiveTreeItem;

    public ReglementView() {
        recursiveTreeItem = new RecursiveTreeItem<Reglement>(reglementManager.get(), RecursiveTreeObject::getChildren);
        tableReglement = new JFXTreeTableView<Reglement>(recursiveTreeItem);
        tableReglement.setShowRoot(false);
        setSpacing(20);
        tableReglement.getColumns().setAll(getMontantColumn(), getDateColumn(), getTypeColumn());
        setAlignment(Pos.CENTER);
        reste.setPromptText("reste");
        reste.setLabelFloat(true);
        reste.setEditable(false);
        apayer.setPromptText("A payer");
        apayer.setLabelFloat(true);
        numero.setPromptText("Numero");
        numero.setLabelFloat(true);
        code.setPromptText("Code");
        code.setLabelFloat(true);
        numeroCarte.setPromptText("Numero Crate");
        numeroCarte.setLabelFloat(true);
        banque.setPromptText("Banque");
        banque.setLabelFloat(true);
        proprie.setPromptText("Prprietaire");
        proprie.setLabelFloat(true);
        hBox2 = new HBox(10, numero, banque);
        hBox3 = new HBox(10, numeroCarte, code);
        HBox hBox = new HBox(10, reste, apayer);
        jfxComboBox.setPromptText("Type de paiement :");
        jfxComboBox.setLabelFloat(true);
        typeReglements.add("Espece");
        typeReglements.add("Cheque");
        typeReglements.add("ELigne");
        initEvents();


        annuler.setOnAction(event -> {
            reset();
        });
        tableReglement.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                numero.setText(newValue.getValue().getNumero() + "");
                banque.setText(newValue.getValue().getBanque() + "");
                proprie.setText(newValue.getValue().getProprietaire() + "");
                afficher(newValue.getValue().getType());
            }
        });

        jfxComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficher(newValue);

            }
        });
        jfxComboBox.setItems(typeReglements);
        getChildren().add(jfxComboBox);
        getChildren().add(hBox);
        HBox box3 = new HBox(20, ajouter, annuler);
        box3.setAlignment(Pos.CENTER);
        getChildren().add(box3);
        ajouter.getStyleClass().add("addButton");
        annuler.getStyleClass().add("addButton");
        ajouter.setOnAction(addEnespece);

        setPadding(new Insets(20));
        getChildren().add(tableReglement);
    }


    public JFXTreeTableColumn<Reglement, Double> getMontantColumn() {
        if (montantColumn == null) {
            montantColumn = new JFXTreeTableColumn<>("Montant");
            montantColumn.prefWidthProperty().bind(tableReglement.widthProperty().divide(3));
            montantColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("montant"));
        }
        return montantColumn;
    }

    public JFXTreeTableColumn<Reglement, String> getTypeColumn() {
        if (typeColumn == null) {
            typeColumn = new JFXTreeTableColumn<>("Type");
            typeColumn.prefWidthProperty().bind(tableReglement.widthProperty().divide(3));
            typeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        }
        return typeColumn;
    }

    public JFXTreeTableColumn<Reglement, String> getDateColumn() {
        if (dateColumn == null) {
            dateColumn = new JFXTreeTableColumn<>("Date");
            dateColumn.prefWidthProperty().bind(tableReglement.widthProperty().divide(3.4));
            dateColumn.setCellValueFactory(param -> new SimpleStringProperty(
                    simpleDateFormat.format(new Date())));/*
            dateColumn.setCellValueFactory(param -> new SimpleStringProperty(
                    simpleDateFormat.format(param.getValue().getValue().getDate())));*/
        }
        return dateColumn;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
        this.vente.setReglements(reglementManager.getPaimentParIdCommande(this.vente.getId()));
        reste.setText(vente.calculeReste() + "");
        apayer.setText(vente.calculeReste() + "");
        afficher("espece");
        reset();
        jfxComboBox.getSelectionModel().select(0);
    }

    public void ajouterReglement() {

    }

    void afficher(String newValue) {
        switch (newValue) {
            case "Espece":
                afficherEnespece();
                ajouter.setOnAction(addEnespece);
                apayer.setOnKeyReleased(addEnespeceKey);
                break;
            case "espece":
                afficherEnespece();
                ajouter.setOnAction(addEnespece);
                apayer.setOnKeyReleased(addEnespeceKey);
                break;
            case "Cheque":
                afficherCheque();
                ajouter.setOnAction(addParCheque);
                apayer.setOnKeyReleased(addParChequeKey);
                numero.setOnKeyReleased(addParChequeKey);
                proprie.setOnKeyReleased(addParChequeKey);
                banque.setOnKeyReleased(addParChequeKey);
                break;
            case "cheque":
                afficherCheque();
                ajouter.setOnAction(addParCheque);
                apayer.setOnKeyReleased(addParChequeKey);
                numero.setOnKeyReleased(addParChequeKey);
                proprie.setOnKeyReleased(addParChequeKey);
                banque.setOnKeyReleased(addParChequeKey);
                break;
            case "ELigne":
                afficherEnLigne();
                ajouter.setOnAction(addEnLigne);
            case "enligne":
                afficherEnLigne();
                ajouter.setOnAction(addEnLigne);
                break;
        }
    }

    void afficherCheque() {
        try {
            getChildren().remove(hBox3);
            getChildren().add(2, hBox2);
            getChildren().add(3, proprie);
        } catch (Exception e) {

        }
    }

    void afficherEnLigne() {
        try {
            getChildren().remove(hBox2);
            getChildren().remove(proprie);
            getChildren().add(2, hBox3);
        } catch (Exception e) {

        }
    }

    public void reset() {
        numero.clear();
        banque.clear();
        proprie.clear();
    }

    void afficherEnespece() {
        getChildren().remove(hBox2);
        getChildren().remove(proprie);
        getChildren().remove(hBox3);
    }

    private void ajoutParCheque() {
        Reglement reglement = new Reglement();
        reglement.setType("cheque");
        reglement.setDate(new Date());
        reglement.setNumero(Long.parseLong(numero.getText()));
        reglement.setBanque(banque.getText());
        reglement.setProprietaire(proprie.getText());
        reglement.setMontant(Double.parseDouble(apayer.getText()));
        reglement.setId_v(getVente().getId());
        reglementManager.add(reglement);
        vente.setReglements(reglementManager.get());
        reste.setText((Double.parseDouble(reste.getText()) - Double.parseDouble(apayer.getText())) + "");
        apayer.setText(reste.getText());
        reset();
    }

    private void initEvents() {
        addEnespece = event -> ajoutEnespece();
        addEnLigne = event -> ajoutEnLigne();
        addParCheque = event -> ajoutParCheque();
        addEnespeceKey = event -> {
            if (event.getCode() == KeyCode.ENTER) ajoutEnespece();
        };
        addParChequeKey = event -> {
            if (event.getCode() == KeyCode.ENTER) ajoutParCheque();
        };
    }


    private void ajoutEnespece() {
        Reglement reglement = new Reglement();
        reglement.setType("espece");
        reglement.setDate(new Date());
        reglement.setMontant(Double.parseDouble(apayer.getText()));
        reglement.setId_v(getVente().getId());
        reglementManager.add(reglement);
        vente.setReglements(reglementManager.get());
        reste.setText((Double.parseDouble(reste.getText()) - Double.parseDouble(apayer.getText())) + "");
        apayer.setText(reste.getText());
        reset();
    }

    private void ajoutEnLigne() {
        Compte compte = new Compte();
        compte.setNumeroCarte(numeroCarte.getText());
        compte.setCode(code.getText());

        if (reglementManager.TestSolde(compte) != null) {
            Compte compte1 = reglementManager.debiter(compte, Double.parseDouble(apayer.getText()));
            if (compte1 != null) {
                Reglement reglement = new Reglement();
                reglement.setType("enLigne");
                reglement.setDate(new Date());
                reglement.setMontant(Double.parseDouble(apayer.getText()));
                reglement.setId_v(getVente().getId());
                reglement.setNumeroCarte(compte1.getNumeroCarte());
                reglement.setCode(compte1.getCode());
                reglement.setProprietaire(compte1.getClient().getNom());
                reglementManager.add(reglement);
                vente.setReglements(reglementManager.get());
                reste.setText((Double.parseDouble(reste.getText()) - Double.parseDouble(apayer.getText())) + "");
                apayer.setText(reste.getText());
                reset();
            }
        }
    }
}
