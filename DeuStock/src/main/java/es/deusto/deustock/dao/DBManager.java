package es.deusto.deustock.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.*;

import es.deusto.deustock.log.DeuLogger;

public class DBManager implements IDBManager{
    private static IDBManager instance = null;
	private final PersistenceManagerFactory pmf;

	private DBManager(){
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	public static IDBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}		
		return instance;
	}

	/**
	 * Permite almacenar un objeto en la BD
	 * 
	 * @param object -> Objeto que se quiere almacenar en la BD
	 */
	public void storeObject(Object object) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			DeuLogger.logger.info("Storing object");
			pm.makePersistent(object); //object?
			tx.commit();
		} catch (Exception e) {
			DeuLogger.logger.error("Could not store object: " + object + ". " + e.getMessage());
		}finally {
			if(tx.isActive()) {
				tx.rollback();
			}
		}
	}


	/**
	 * Permite obtener una lista con todos los objetos que se encuentran en la BD
	 * 
	 * @return <strong> ArrayList[Object]</strong> -> Lista que contiene todos los objetos
	 *         almacenados en la BD
	 */
	
	public List<Object> getObjects(Class entityClass) {
		
			PersistenceManager pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(-1);

			Transaction tx = pm.currentTransaction();
			ArrayList<Object> objects = new ArrayList<>();

			try {
				System.out.println("   * Retrieving an Extent for Objects.");

				tx.begin();
	
				Extent<Object> extent = pm.getExtent(entityClass, true);

				for (Object s : extent) {
					objects.add(pm.detachCopy(s));
				}
				tx.commit();
			} catch (Exception ex) {
				System.out.println("   $ Error Getting objects: " + ex.getMessage());
				DeuLogger.logger.error("Error getting objects");
			} finally {
				if (tx != null && tx.isActive())
					tx.rollback();
				pm.close();
			}
			return objects;
	}

	@Override
	public List<Object> getObjects(Class entityClass, String conditions) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		List<Object> object = null;
		try {
			System.out.println("   * Querying a Object, conditions: " + conditions);

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + entityClass.getName() + " WHERE " + conditions);
			query.setUnique(false);
			object = (List<Object>) query.execute();
			tx.commit();

		} catch (Exception ex) {
			System.out.println("   $ Error Getting Object: " + ex.getMessage());
			DeuLogger.logger.error("Error getting Object: " + conditions);
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return object;
	}

	public Object getObject(Class entityClass, String conditions) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		Object object = null;
		try {
			System.out.println("   * Querying a Object, conditions: " + conditions);

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + entityClass.getName() + " WHERE " + conditions);
			query.setUnique(true);
			object = pm.detachCopy(query.execute());
			tx.commit();

		} catch (Exception ex) {
			System.out.println("   $ Error Getting Object: " + ex.getMessage());
			DeuLogger.logger.error("Error getting Object: " + conditions);
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return object;
	}

	public void updateObject(Object object) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(object);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	@Override
	public void deleteObject(Class entityClass, String conditions) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		try {
			System.out.println("   * Querying a Object, conditions:" + conditions);

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + entityClass.getName() + " WHERE " + conditions);
			query.setUnique(true);
			query.deletePersistentAll();
			tx.commit();

		} catch (Exception ex) {
			System.out.println("   $ Error Getting Object: " + ex.getMessage());
			DeuLogger.logger.error("Error getting object for deleting: ");
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}	
	
	@Override
	public void deleteObject(Object object) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.deletePersistent(object);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}	
}
