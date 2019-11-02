package produit.dao;


import categorie.model.Categorie;
import dao.MyConnection;
import produit.model.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImplProduitDao implements ProduitDao {
    private Connection connection;
    private Statement statement;
    PreparedStatement preparedStatementAdd, preparedStatementSet;

    public ImplProduitDao() {
        connection = MyConnection.getMyConnection().getConnection();
        try {
            this.statement = connection.createStatement();
            preparedStatementAdd = connection.prepareStatement("insert into produit (code,designation,prixAchat,prixVente,id_cat) VALUES (?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
            preparedStatementSet = connection.prepareStatement("UPDATE produit SET code=? ,designation=? ,prixAchat=? ,prixVente=? ,quantite=? WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Produit getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        Produit produit = new Produit();
        produit.setId(resultSet.getLong(1));
        produit.setCode(resultSet.getLong(2));
        produit.setDesignation(resultSet.getString(3));
        produit.setPrixA(resultSet.getDouble(4));
        produit.setPrixV(resultSet.getDouble(5));

       /* produit.setId_cat(resultSet.getLong(6));*/

        produit.setQuantite(resultSet.getInt(8));
        Categorie categorie = new Categorie();
        categorie.setId(resultSet.getLong(9));
        categorie.setLibelle(resultSet.getString(10));
        produit.setCategorie(categorie);
        return produit;
    }

    @Override
    public List<Produit> get() {
        List<Produit> produits = new ArrayList<>();
        Produit produit;
        try {
            this.statement.execute("SELECT * FROM produit,categorie WHERE id_cat=categorie.id");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                produit = this.getValuesFromResultSet(resultSet);
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    @Override
    public void add(Produit value) throws SQLException {
        preparedStatementAdd.setLong(1, value.getCode());
        preparedStatementAdd.setString(2, value.getDesignation());
        preparedStatementAdd.setDouble(3, value.getPrixA());
        preparedStatementAdd.setDouble(4, value.getPrixV());
        preparedStatementAdd.setLong(5, value.getCategorie().getId());

       /* this.statement.execute(
                "insert into produit (code,designation,prixAchat,prixVente,id_cat) VALUES (" + value.getCode() + ",'" + value.getDesignation() + "'," + value.getPrixA() + "," + value.getPrixV() + "," + value.getCategorie().getId() + ")"
                , Statement.RETURN_GENERATED_KEYS);*/
        preparedStatementAdd.execute();
        ResultSet resultSet = preparedStatementAdd.getGeneratedKeys();
        if (resultSet.next()) {
            value.setId(resultSet.getInt(1));
        }

    }

    @Override
    public void update(Produit value) throws SQLException {
     /*   this.statement.execute("UPDATE produit SET " +
                "code=" + value.getCode() + " ," +
                "designation='" + value.getDesignation() + "'" +
                " ,prixAchat=" + value.getPrixA() +
                " ,prixVente=" + value.getPrixV() +
                " ,quantite=" + value.getQuantite() +
                "  WHERE id=" + value.getId() + "");*/
        preparedStatementSet.setLong(1, value.getCode());
        preparedStatementSet.setString(2, value.getDesignation());
        preparedStatementSet.setDouble(3, value.getPrixA());
        preparedStatementSet.setDouble(4, value.getPrixV());
        preparedStatementSet.setInt(5, value.getQuantite());
        preparedStatementSet.setLong(6, value.getId());
        preparedStatementSet.execute();
//        preparedStatementAdd.setLong(5, value.getCategorie().getId());
    }

    @Override
    public boolean diminuerQuantite(long id, int quantite) {
        try {
            this.statement.execute("UPDATE produit SET " +
                    " quantite=quantite - " + quantite +
                    "  WHERE id=" + id + "");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean augmenterQuantite(long id, int quantite) {
        try {
            this.statement.execute("UPDATE produit SET " +
                    " quantite=quantite +" + quantite +
                    "  WHERE id=" + id + "");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit getById(long Id) {
        try {
            this.statement.execute("SELECT * FROM produit p,categorie c WHERE p.id_cat=c.id and p.id=" + Id + "");
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
    public long deleteById(long Id) {
        try {
            return this.statement.executeUpdate("DELETE FROM produit WHERE id=" + Id + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Produit> getByDesignation(String designation) {
        List<Produit> produits = new ArrayList<>();
        Produit produit;
        try {
            this.statement.execute("SELECT * FROM produit p,categorie c WHERE p.id_cat=c.id and designation LIKE '%" + designation + "%'");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                produit = this.getValuesFromResultSet(resultSet);
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

    @Override
    public List<Produit> getByCategorie(long idCat) {
        List<Produit> produits = new ArrayList<>();
        Produit produit;
        try {
            this.statement.execute("SELECT * FROM produit p,categorie c WHERE p.id_cat=c.id and id_cat=" + idCat + "");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                produit = this.getValuesFromResultSet(resultSet);
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }
}
