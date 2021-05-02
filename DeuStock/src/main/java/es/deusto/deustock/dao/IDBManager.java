package es.deusto.deustock.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDBManager {

	void store(Object object) throws SQLException;
	List<Object> getAll(Class entityClass);
	List<Object> getList(Class entityClass, String conditions) throws SQLException;
	Object get(Class entityClass, String conditions) throws SQLException;
	void delete(Class entityClass, String conditions) throws SQLException;
	void delete(Object object);
	void update(Object object) throws SQLException;
}
