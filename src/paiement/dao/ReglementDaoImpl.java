package paiement.dao;

import dao.MyConnection;
import paiement.model.Reglement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReglementDaoImpl implements ReglementDao {
    private Connection connection;
    private Statement statement;
    PreparedStatement preparedStatementAdd;

    public ReglementDaoImpl() {
        connection = MyConnection.getMyConnection().getConnection();
        try {
            this.statement = connection.createStatement();
            preparedStatementAdd = connection.prepareStatement("insert into reglement (montant,type,id_v,numero,banque,proprietaire) VALUES (?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Reglement> get() {
        List<Reglement> reglements = new ArrayList<>();
        Reglement reglement;
        try {
            this.statement.execute("SELECT * FROM reglement");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                reglement = this.getValuesFromResultSet(resultSet);
                reglements.add(reglement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reglements;
    }


    @Override
    public void add(Reglement value) throws SQLException {

        preparedStatementAdd.setDouble(1, value.getMontant());
        preparedStatementAdd.setString(2, value.getType());
        preparedStatementAdd.setLong(3, value.getId_v());
        preparedStatementAdd.setLong(4, value.getNumero());
        preparedStatementAdd.setString(5, value.getBanque());
        preparedStatementAdd.setString(6, value.getProprietaire());
        preparedStatementAdd.execute();
        ResultSet resultSet = preparedStatementAdd.getGeneratedKeys();

        if (resultSet.next()) {
            value.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void update(Reglement value) throws SQLException {/*
        this.statement.execute("UPDATE reglement SET " +
                "libelle='" + value.getLibelle() + "' WHERE id=" + value.getId() + "");*/
    }

    @Override
    public Reglement getById(long Id) {
        try {
            this.statement.execute("SELECT * FROM reglement WHERE id=" + Id + "");
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
        return this.statement.executeUpdate("DELETE FROM reglement WHERE id=" + Id + "");
    }

    @Override
    public Reglement getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        Reglement reglement = new Reglement();
        reglement.setId(resultSet.getInt(1));
        reglement.setMontant(resultSet.getDouble(2));
        reglement.setId_v(resultSet.getLong(3));
        reglement.setType(resultSet.getString(4));
        reglement.setNumero(resultSet.getLong(5));
        reglement.setBanque(resultSet.getString(6));
        reglement.setProprietaire(resultSet.getString(7));
        return reglement;
    }


    @Override
    public List<Reglement> getPaimentParIdCommande(long id) {
        List<Reglement> reglements = new ArrayList<>();
        Reglement reglement;
        try {
            this.statement.execute("SELECT * FROM reglement WHERE id_v=" + id);
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                reglement = this.getValuesFromResultSet(resultSet);
                reglements.add(reglement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reglements;
    }
}
