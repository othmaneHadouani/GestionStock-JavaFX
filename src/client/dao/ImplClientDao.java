package client.dao;

import client.model.Client;
import dao.MyConnection;
import client.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImplClientDao implements ClientDao {
    private Connection connection;
    private Statement statement;
    PreparedStatement preparedStatementAdd, preparedStatementSet;

    public ImplClientDao() {
        connection = MyConnection.getMyConnection().getConnection();
        try {
            this.statement = connection.createStatement();
            preparedStatementAdd = connection.prepareStatement("insert into personne (code,nom,adresse,tele,mail,type) VALUES (?,?,?,?,?,'c') ", Statement.RETURN_GENERATED_KEYS);
            preparedStatementSet = connection.prepareStatement("UPDATE personne SET code=? ,nom=? ,adresse=? ,tele=? ,mail=? WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Client getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        Client Client = new Client();
        Client.setId(resultSet.getLong(1));
        Client.setCode(resultSet.getLong(2));
        Client.setNom(resultSet.getString(3));
        Client.setAdresse(resultSet.getString(4));
        Client.setTele(resultSet.getString(5));
        Client.setMail(resultSet.getString(6));
        return Client;
    }

    @Override
    public List<Client> get() {
        List<Client> Clients = new ArrayList<>();
        Client Client;
        try {
            this.statement.execute("SELECT * FROM personne WHERE type='c'");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                Client = this.getValuesFromResultSet(resultSet);
                Clients.add(Client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Clients;
    }


    @Override
    public void add(Client value) throws SQLException {
       /* this.statement.execute(
                "insert into personne (code,nom,adresse,tele,mail,type) VALUES (" +
                        value.getCode() + ",'"
                        + value.getNom() + "','"
                        + value.getAdresse() + "','"
                        + value.getTele() + "','"
                        + value.getMail() + "','c')"
                , Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = this.statement.getGeneratedKeys();*/
        preparedStatementAdd.setLong(1, value.getCode());
        preparedStatementAdd.setString(2, value.getNom());
        preparedStatementAdd.setString(3, value.getAdresse());
        preparedStatementAdd.setString(4, value.getTele());
        preparedStatementAdd.setString(5, value.getMail());

        preparedStatementAdd.execute();
        ResultSet resultSet = preparedStatementAdd.getGeneratedKeys();
        if (resultSet.next()) {
            value.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void update(Client value) throws SQLException {
        preparedStatementSet.setLong(1, value.getCode());
        preparedStatementSet.setString(2, value.getNom());
        preparedStatementSet.setString(3, value.getAdresse());
        preparedStatementSet.setString(4, value.getTele());
        preparedStatementSet.setString(5, value.getMail());
        preparedStatementSet.setLong(6, value.getCode());
        preparedStatementSet.execute();
    }

    @Override
    public Client getById(long Id) {
        try {
            this.statement.execute("SELECT * FROM personne WHERE id=" + Id + "");
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
            return this.statement.executeUpdate("DELETE FROM personne WHERE id=" + Id + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Client> getByNom(String nom) {
        List<Client> personnes = new ArrayList<>();
        Client personne;
        try {
            this.statement.execute("SELECT * FROM personne WHERE personne.nom LIKE '%" + nom + "%'");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                personne = this.getValuesFromResultSet(resultSet);
                personnes.add(personne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnes;
    }


}
