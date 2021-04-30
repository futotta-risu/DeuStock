package es.deusto.deustock.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface IDAO <T>{

    boolean has(Object identity) throws SQLException;

    void store(T object) throws SQLException;
    void update(T object) throws SQLException;
    void delete(T object);

    T get(Object identity) throws SQLException;
    Collection<T> getAll();


}
