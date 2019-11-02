package categorie.dao;

import dao.MyConnection;
import categorie.model.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDaoImpl implements CategorieDao {
    private Connection connection;
    private Statement statement;
    PreparedStatement preparedStatementAdd;

    public CategorieDaoImpl() {
        connection = MyConnection.getMyConnection().getConnection();
        try {
            this.statement = connection.createStatement();
            preparedStatementAdd = connection.prepareStatement("insert into categorie (libelle) VALUES (?) ", Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Categorie> get() {
        List<Categorie> categories = new ArrayList<>();
        Categorie categorie;
        try {
            this.statement.execute("SELECT * FROM categorie");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                categorie = this.getValuesFromResultSet(resultSet);
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }


    @Override
    public void add(Categorie value) throws SQLException {

        preparedStatementAdd.setString(1, value.getLibelle());
        preparedStatementAdd.execute();
        ResultSet resultSet = preparedStatementAdd.getGeneratedKeys();

      /*  this.statement.execute(
                "insert into categorie (libelle) VALUES ('" + value.getLibelle() + "')"
                , Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = this.statement.getGeneratedKeys();*/
        if (resultSet.next()) {
            value.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void update(Categorie value) throws SQLException {
        this.statement.execute("UPDATE categorie SET " +
                "libelle='" + value.getLibelle() + "' WHERE id=" + value.getId() + "");
    }

    @Override
    public Categorie getById(long Id) {
        try {
            this.statement.execute("SELECT * FROM categorie WHERE id=" + Id + "");
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
        return this.statement.executeUpdate("DELETE FROM categorie WHERE id=" + Id + "");
    }

    @Override
    public Categorie getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setId(resultSet.getInt(1));
        categorie.setLibelle(resultSet.getString(2));
        return categorie;
    }


    @Override
    public List<Categorie> getByLibelle(String libelle) {
        List<Categorie> categories = new ArrayList<>();
        Categorie categorie;
        try {
            this.statement.execute("SELECT * FROM categorie WHERE libelle  LIKE '%" + libelle + "%'");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                categorie = this.getValuesFromResultSet(resultSet);
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
