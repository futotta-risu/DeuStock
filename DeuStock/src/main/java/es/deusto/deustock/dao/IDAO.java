package es.deusto.deustock.dao;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Generic DAO interface.
 *
 * @param <T> Class for the IDao
 *
 * @author Erik B. Terres
 */
public interface IDAO <T>{

    // TODO Delete throw

    /**
     * Returns whether an object with a certain identity is contained in the database.
     *
     * @return True if the object is in the DB
     *
     */
    boolean has(Object identity) throws SQLException;

    /**
     * Stores an object in the database.
     *
     * @throws SQLException Exception if the store failed.
     */
    void store(T object) throws SQLException;

    /**
     * Updates an object from the database.
     *
     * The object must be a detached object from the database.
     *
     * @throws SQLException Exception thrown if the update process failed.
     */
    void update(T object) throws SQLException;

    /**
     * Deletes an object from the database.
     *
     * The object must be a detached object from the database.
     *
     */
    void delete(T object);

    /**
     * Retrieves an object from the database.
     */
    T get(Object identity) throws SQLException;

    /**
     * Retrieves all the objects from a certain class.
     */
    Collection<T> getAll();


}
