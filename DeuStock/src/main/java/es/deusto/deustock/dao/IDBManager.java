package es.deusto.deustock.dao;

import java.util.ArrayList;

public interface IDBManager {

	public void storeObject(Object object);
	public ArrayList<Object> getObjects();
	public Object getObject(Class entityClass, String conditions);
	public void deleteObject(Class entityClass, String conditions);
	public void updateObject(Object object);
}
