package commande.vente.dao;

import dao.MyConnection;
import produit.model.Produit;
import commande.vente.model.Vente;
import commande.models.LigneCommande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeDaoImpl implements LigneCommandeDao {
    private Connection connection;
    private Statement statement;


    public LigneCommandeDaoImpl() {
        connection = MyConnection.getMyConnection().getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<LigneCommande> get() {
        List<LigneCommande> categories = new ArrayList<>();
        LigneCommande lignedecommande;
        try {
            this.statement.execute("SELECT * FROM lignedecommande,produit WHERE lignedecommande.id_pro=produit.id");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                lignedecommande = this.getValuesFromResultSet(resultSet);
                categories.add(lignedecommande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }


    @Override
    public void add(LigneCommande value) throws SQLException {

        this.statement.execute("insert into lignedecommande (quantite, sousTotal, id_v, id_pro) VALUES " +
                        "(" +
                        "" + value.getQuantite() + "," +
                        "" + value.getSousTotal() + "," +
                        "" + value.getCommande().getId() + "," +
                        "" + value.getProduit().getId() +
                        ")"
                , Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = this.statement.getGeneratedKeys();
        if (resultSet.next()) {
            value.setId(resultSet.getLong(1));
        }

    }

    @Override
    public void update(LigneCommande value) throws SQLException {
        this.statement.execute("UPDATE lignedecommande SET " +
                "quantite=" + value.getQuantite() + ", " +
                "sousTotal=" + value.getSousTotal() + ", " +
                "id_pro=" + value.getProduit().getId() +
                " WHERE id=" + value.getId() + "");
    }

    @Override
    public LigneCommande getById(long Id) {
        try {
            this.statement.execute("SELECT * FROM lignedecommande,produit WHERE lignedecommande.id_pro=produit.id and  lignedecommande.id=" + Id + "");
            ResultSet resultSet = this.statement.getResultSet();

            if (resultSet.next()) {
                return this.getValuesFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long deleteById(long Id) throws SQLException {
        return this.statement.executeUpdate("DELETE FROM lignedecommande WHERE id=" + Id + "");
    }

    @Override
    public long deleteById(LigneCommande ligneCommande) throws SQLException {
        return this.deleteById(ligneCommande.getId());
    }

    @Override
    public List<LigneCommande> getByCmdId(long id) {
        List<LigneCommande> categories = new ArrayList<>();
        LigneCommande lignedecommande;
        try {
            this.statement.execute("SELECT * FROM lignedecommande,produit WHERE lignedecommande.id_pro=produit.id and id_v=" + id + "");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                lignedecommande = this.getValuesFromResultSet(resultSet);
                categories.add(lignedecommande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }


    @Override
    public LigneCommande getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        LigneCommande lignedecommande = new LigneCommande();
        lignedecommande.setId(resultSet.getLong(1));
        lignedecommande.setQuantite(resultSet.getInt(2));
        lignedecommande.setOldQuantite(resultSet.getInt(2));
        lignedecommande.setSousTotal(resultSet.getDouble(3));

        Produit produit = new Produit();
        produit.setId(resultSet.getLong(4));


        Vente vente = new Vente();
        vente.setId(resultSet.getLong(5));
        lignedecommande.setCommande(vente);

        produit.setCode(resultSet.getLong(8));
        produit.setDesignation(resultSet.getString(9));
        lignedecommande.setProduit(produit);

        return lignedecommande;
    }

}
