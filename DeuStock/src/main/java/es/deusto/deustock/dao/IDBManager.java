package es.deusto.deustock.dao;

import java.util.List;

public interface IDBManager {

	void storeObject(Object object);
	List<Object> getObjects(Class entityClass);
	List<Object> getObjects(Class entityClass, String conditions);
	Object getObject(Class entityClass, String conditions);
	void deleteObject(Class entityClass, String conditions);
	void deleteObject(Object object);
	void updateObject(Object object);
}
