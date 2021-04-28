package es.deusto.deustock.dao;

import java.util.ArrayList;
import java.util.List;

public interface IDBManager {

	public void storeObject(Object object);
	public List<Object> getObjects(Class entityClass);
	public List<Object> getObjects(Class entityClass, String conditions);
	public Object getObject(Class entityClass, String conditions);
	public void deleteObject(Class entityClass, String conditions);
	public void deleteObject(Object object);
	public void updateObject(Object object);
}
