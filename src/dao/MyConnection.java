package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyConnection {
    private Connection connection = null;
    private static MyConnection myConnection;

    private MyConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.config");

            connection = DriverManager.getConnection("jdbc:mysql://" + resourceBundle.getString("Mysql.url"),
                    resourceBundle.getString("Mysql.Login"),
                    resourceBundle.getString("Mysql.Password"));
            String s = new String("Mysql.url");
            System.out.println(s);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static MyConnection getMyConnection() {
        while (myConnection == null)
            myConnection = new MyConnection();
        return myConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
