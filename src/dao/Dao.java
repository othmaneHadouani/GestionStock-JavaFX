package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    List<T> get();

    void add(T value) throws SQLException;

    void update(T value) throws SQLException;

    T getById(long Id);

    long deleteById(long Id) throws SQLException;

    T getValuesFromResultSet(ResultSet resultSet) throws SQLException;
}