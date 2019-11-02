package fournisseur.dao;

import dao.MyConnection;
import fournisseur.model.Fournisseur;
import fournisseur.model.Fournisseur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImplFournisseurDao implements FournisseurDao {
    private Connection connection;
    private Statement statement;

    public ImplFournisseurDao() {
        connection = MyConnection.getMyConnection().getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Fournisseur getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        Fournisseur Fournisseur = new Fournisseur();
        Fournisseur.setId(resultSet.getLong(1));
        Fournisseur.setCode(resultSet.getLong(2));
        Fournisseur.setNom(resultSet.getString(3));
        Fournisseur.setAdresse(resultSet.getString(4));
        Fournisseur.setTele(resultSet.getString(5));
        Fournisseur.setMail(resultSet.getString(6));
        Fournisseur.setFax(resultSet.getString(7));
        return Fournisseur;
    }

    @Override
    public List<Fournisseur> get() {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        Fournisseur fournisseur;
        try {
            this.statement.execute("SELECT * FROM personne WHERE type='f'");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                fournisseur = this.getValuesFromResultSet(resultSet);
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }


    @Override
    public void add(Fournisseur value) throws SQLException {
        this.statement.execute(
                "insert into personne (code,nom,adresse,tele,mail,fax,type) VALUES (" +
                        value.getCode() + ",'"
                        + value.getNom() + "','"
                        + value.getAdresse() + "','"
                        + value.getTele() + "','"
                        + value.getMail() + "','"
                        + value.getFax() + "','f')"
                , Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = this.statement.getGeneratedKeys();
        if (resultSet.next()) {
            value.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void update(Fournisseur value) throws SQLException {
        this.statement.execute("UPDATE personne SET " +
                "code=" + value.getCode() + " ," +
                "nom='" + value.getNom() + "' ," +
                "tele='" + value.getTele() + "' ," +
                "mail='" + value.getMail() + "' ," +
                "fax='" + value.getFax() + "' ," +
                "adresse='" + value.getAdresse() + "'  WHERE id=" + value.getId() + "");

    }

    @Override
    public Fournisseur getById(long Id) {
        Fournisseur fournisseur = null;
        try {
            this.statement.execute("SELECT * FROM personne WHERE id=" + Id + "");
            ResultSet resultSet = this.statement.getResultSet();

            if (resultSet.next()) {
                fournisseur = this.getValuesFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseur;
    }

    @Override
    public long deleteById(long Id) {
        try {
            return this.statement.executeUpdate("DELETE FROM personne WHERE id=" + Id + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Fournisseur> getByNom(String nom) {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        Fournisseur fournisseur;
        try {
            this.statement.execute("SELECT * FROM personne WHERE personne.nom LIKE '%" + nom + "%'");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                fournisseur = this.getValuesFromResultSet(resultSet);
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fournisseurs;
    }

}
