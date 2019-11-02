package commande.achat.dao;

import categorie.model.Categorie;
import produit.dao.ImplProduitDao;
import produit.dao.ProduitDao;
import produit.model.Produit;
import commande.achat.model.Achat;
import dao.MyConnection;
import fournisseur.model.Fournisseur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AchatDaoImpl implements AchatDao {
    private Connection connection;
    private Statement statement;
    ProduitDao produitDao = new ImplProduitDao();

    public AchatDaoImpl() {
        connection = MyConnection.getMyConnection().getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Achat> get() {
        List<Achat> categories = new ArrayList<>();
        Achat achat;
        try {
            this.statement.execute("SELECT * FROM achat,personne,produit WHERE id_fou=personne.id and id_pro=produit.id ");
            ResultSet resultSet = this.statement.getResultSet();

            while (resultSet.next()) {
                achat = this.getValuesFromResultSet(resultSet);
                categories.add(achat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }


    @Override
    public void add(Achat value) throws SQLException {
        this.statement.execute("insert into achat (quantite, dateAchat, total, id_fou, id_pro) VALUES " +
                        "(" +
                        "" + value.getQuantite() + ",CURRENT_TIMESTAMP," +
                        "" + value.getTotal() + "," +
                        "" + value.getPersonne().getId() + "," +
                        "" + value.getProduit().getId() + "" +
                        ")"
                , Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = this.statement.getGeneratedKeys();
        if (resultSet.next()) {
            value.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void update(Achat value) throws SQLException {
        this.statement.execute("UPDATE achat SET " +
                "quantite=" + value.getQuantite() + ", " +
                "total=" + value.getTotal() + ", " +
                "id_fou=" + value.getPersonne().getId() + ", " +
                "id_pro=" + value.getProduit().getId() + " " +
                "WHERE id=" + value.getId() + "");

    }

    @Override
    public Achat getById(long Id) {
        try {
            this.statement.execute("SELECT * FROM achat WHERE id=" + Id + "");
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
        return this.statement.executeUpdate("DELETE FROM achat WHERE id=" + Id + "");
    }

    @Override
    public Achat getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        Achat achat = new Achat();
        achat.setId(resultSet.getLong(1));
        achat.setQuantite(resultSet.getInt(2));
        achat.setDate(resultSet.getDate(3));
        achat.setTotal(resultSet.getDouble(4));

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(resultSet.getLong(7));
        fournisseur.setCode(resultSet.getLong(8));
        fournisseur.setNom(resultSet.getString(9));

        achat.setPersonne(fournisseur);

        Produit produit = new Produit();

        produit.setId(resultSet.getLong(15));
        produit.setCode(resultSet.getLong(16));
        produit.setDesignation(resultSet.getString(17));
        Categorie categorie = new Categorie();
        categorie.setId(resultSet.getLong(20));
        produit.setCategorie(categorie);
        achat.setProduit(produit);

        return achat;
    }

}
