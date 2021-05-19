package es.deusto.deustock.dao;

import java.util.HashMap;
import java.util.List;

public interface IDBManager {

	void store(Object object);
	List<Object> getAll(Class entityClass);
	List<Object> getList(Class entityClass, String conditions, HashMap<String, String> params);
	Object get(Class entityClass, String conditions, HashMap<String, String> params);
	void delete(Class entityClass, String conditions, HashMap<String, String> params);
	void delete(Object object);
	void update(Object object);
}
