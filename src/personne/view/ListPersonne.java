package personne.view;

import View.TableActions;
import View.TableHead;
import categorie.model.Categorie;
import client.manager.ClientManager;
import client.manager.ImplClientManager;
import client.model.Client;
import client.view.Ajout;
import client.view.Container;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fournisseur.manager.FournisseurManager;
import fournisseur.manager.ImplFournisseurManager;
import fournisseur.model.Fournisseur;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import personne.model.Personne;

import java.util.Collection;
import java.util.List;


public class ListPersonne extends VBox {

    protected TableHead tableHead;
    public JFXTreeTableView<Personne> tablePersonne;
    protected JFXTreeTableColumn<Personne, Integer> idColumn;
    protected JFXTreeTableColumn<Personne, Long> codeColumn;
    protected JFXTreeTableColumn<Personne, String> nomColumn;
    protected JFXTreeTableColumn<Personne, String> adresseColumn;
    protected JFXTreeTableColumn<Personne, String> teleColumn;
    protected JFXTreeTableColumn<Personne, String> mailColumn;
    protected JFXTreeTableColumn<Personne, Personne> actionColumn;
    ImplClientManager clientManager = ImplClientManager.getClientManager();
    ImplFournisseurManager fournisseurManager = ImplFournisseurManager.getFournisseurManager();

    protected ListPersonne() {

    }


    public JFXTreeTableColumn<Personne, Integer> getIdColumn() {
        if (idColumn == null) {
            idColumn = new JFXTreeTableColumn<>("Id");
            idColumn.setPrefWidth(80);

            idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));

        }
        return idColumn;
    }

    public JFXTreeTableColumn<Personne, Long> getCodeColumn() {
        if (codeColumn == null) {
            codeColumn = new JFXTreeTableColumn<>("Code");
            codeColumn.prefWidthProperty().bind(tablePersonne.widthProperty().divide(100 / 10));

            codeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));

        }
        return codeColumn;
    }


    public JFXTreeTableColumn<Personne, String> getNomColumn() {
        if (nomColumn == null) {
            nomColumn = new JFXTreeTableColumn<>("Nom");
            nomColumn.prefWidthProperty().bind(tablePersonne.widthProperty().divide(100 / 16.66));
            nomColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom"));
        }
        return nomColumn;
    }

    public JFXTreeTableColumn<Personne, String> getAdresseColumn() {
        if (adresseColumn == null) {
            adresseColumn = new JFXTreeTableColumn<>("Adresse");
            adresseColumn.prefWidthProperty().bind(tablePersonne.widthProperty().divide(100 / 16.66));
            adresseColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("adresse"));
        }
        return adresseColumn;
    }

    public JFXTreeTableColumn<Personne, String> getTeleColumn() {
        if (teleColumn == null) {
            teleColumn = new JFXTreeTableColumn<>("Tele");
            teleColumn.prefWidthProperty().bind(tablePersonne.widthProperty().divide(100 / 11));
            teleColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("tele"));
        }
        return teleColumn;
    }

    public JFXTreeTableColumn<Personne, String> getMailColumn() {
        if (mailColumn == null) {
            mailColumn = new JFXTreeTableColumn<>("Mail");
            mailColumn.prefWidthProperty().bind(tablePersonne.widthProperty().divide(100 / 16.66));
            mailColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("mail"));
        }
        return mailColumn;
    }


}
