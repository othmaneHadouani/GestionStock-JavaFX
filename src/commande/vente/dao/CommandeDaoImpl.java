package commande.vente.dao;

import client.model.Client;
import dao.MyConnection;
import commande.vente.model.Vente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommandeDaoImpl implements CommandeDao {
    private Connection connection;
    private Statement statement;
    LigneCommandeDao ligneCommandeDao = new LigneCommandeDaoImpl();

    public CommandeDaoImpl() {
        connection = MyConnection.getMyConnection().getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Vente> get() {
        List<Vente> categories = new ArrayList<>();
        categories.addAll(getIsNotNullIdCli());
        categories.addAll(getIsNullIdCli());
        return categories;
    }

    @Override
    public List<Vente> getIsNotNullIdCli() {
        List<Vente> categories = new ArrayList<>();
        Vente vente;
        try {
            this.statement.execute("SELECT * FROM vente,personne WHERE id_cl=personne.id");
            ResultSet resultSet = this.statement.getResultSet();
            while (resultSet.next()) {
                vente = this.getValuesFromResultSet(resultSet);
                vente.setLigneCommandes(ligneCommandeDao.getByCmdId(vente.getId()));
                categories.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public List<Vente> getIsNullIdCli() {
        List<Vente> categories = new ArrayList<>();
        Vente vente;
        try {
            this.statement.execute("SELECT * FROM vente WHERE id_cl is NULL");
            ResultSet resultSet = this.statement.getResultSet();
            while (resultSet.next()) {
                vente = this.getValuesFromResultSet(resultSet);
                vente.setLigneCommandes(ligneCommandeDao.getByCmdId(vente.getId()));
                categories.add(vente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }


    @Override
    public void add(Vente value) throws SQLException {

        if (value.getPersonne() != null) {
            this.statement.execute("insert into vente ( dateCommande, total, id_cl) VALUES " +
                            "(CURRENT_TIMESTAMP," +
                            "" + value.getTotal() + "," +
                            "" + value.getPersonne().getId() +
                            ")"
                    , Statement.RETURN_GENERATED_KEYS);
            System.out.println("value jaa= [" + value + "]");
        } else
            this.statement.execute("insert into vente ( dateCommande, total,id_cl) VALUES " +
                            "(CURRENT_TIMESTAMP," +
                            "" + value.getTotal() + ",null" +
                            ")"
                    , Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = this.statement.getGeneratedKeys();
        if (resultSet.next()) {
            value.setId(resultSet.getInt(1));
        }

    }

    @Override
    public void update(Vente value) throws SQLException {
        if (value.getPersonne() != null)
            this.statement.execute("UPDATE vente SET " +
                    "total=" + value.getTotal() + ", " +
                    "id_cl=" + value.getPersonne().getId() +
                    " WHERE id=" + value.getId() + "");
        else
            this.statement.execute("UPDATE vente SET " +
                    "total=" + value.getTotal() + ", " +
                    "id_cl=null" +
                    " WHERE id=" + value.getId() + "");
    }

    @Override
    public Vente getById(long Id) {
        try {
            this.statement.execute("SELECT * FROM vente WHERE id=" + Id + "");
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
        return this.statement.executeUpdate("DELETE FROM vente WHERE id=" + Id + "");
    }

    @Override
    public Vente getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        Vente vente = new Vente();
        vente.setId(resultSet.getLong(1));
//        commande.setCode(resultSet.getLong(2));
        vente.setDate(resultSet.getDate(3));
        vente.setTotal(resultSet.getDouble(4));


        try {
            Client client = new Client();
            client.setId(resultSet.getLong(5));
            client.setCode(resultSet.getLong(7));
            client.setNom(resultSet.getString(8));

            vente.setPersonne(client);
        } catch (SQLException e) {

        }


        return vente;
    }

}
