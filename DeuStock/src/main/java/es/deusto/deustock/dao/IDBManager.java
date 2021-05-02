package es.deusto.deustock.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDBManager {

	void store(Object object) throws SQLException;
	List<Object> getAll(Class entityClass);
	List<Object> getList(Class entityClass, List<String> filters) throws SQLException;
	Object get(Class entityClass, List<String> filters) throws SQLException;
	Object getByID(Class entityClass, String id) throws SQLException;
	void delete(Class entityClass, List<String> filters) throws SQLException;
	void delete(Object object);
	void update(Object object) throws SQLException;
}
