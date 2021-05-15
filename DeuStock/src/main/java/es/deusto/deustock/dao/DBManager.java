package es.deusto.deustock.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jdo.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBManager implements IDBManager{

	private static final String SQL_TYPE = "javax.jdo.query.JDOQL";

    private static IDBManager instance = null;
	private PersistenceManagerFactory pmf;

	private final Logger logger = LoggerFactory.getLogger(DBManager.class);

	private static final  String SELECT_CONDITION_QUERY = "SELECT FROM %s WHERE %s";

	private DBManager(){
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	public void setPFM(PersistenceManagerFactory pmf){
		this.pmf = pmf;
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
	public void store(Object object) {
		var pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		var tx = pm.currentTransaction();
		
		try {
			tx.begin();
			logger.info("Storing object");
			pm.makePersistent(object); //object?
			tx.commit();
		} catch (Exception e) {
			logger.error("Could not store object: {}. {}", object, e.getMessage());
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
	
	public List<Object> getAll(Class entityClass) {
		
			var pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(-1);

			var tx = pm.currentTransaction();
			ArrayList<Object> objects = new ArrayList<>();

			try {
				logger.info("   * Retrieving an Extent for Objects.");

				tx.begin();

				var extent = pm.getExtent(entityClass, true);

				for (Object s : extent) {
					objects.add(pm.detachCopy(s));
				}
				tx.commit();
			} catch (Exception ex) {
				logger.error("Error getting objects from class {}", entityClass.getName() );
			} finally {
				if (tx != null && tx.isActive())
					tx.rollback();
				pm.close();
			}
			return objects;
	}

	@Override
	public List<Object> getList(Class entityClass, String conditions, HashMap<String, String> params) {
		var pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		var tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		List<Object> object = null;
		try {
			logger.info("Querying list of {}", entityClass);

			tx.begin();
			var query = pm.newQuery(
					SQL_TYPE,
					String.format(SELECT_CONDITION_QUERY, entityClass.getName(), conditions)
			);
			query.setUnique(false);
			object = (List<Object>) query.executeWithMap(params);
			tx.commit();

		} catch (Exception ex) {
			logger.error("Error getting Object: {}", ex.getMessage());
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return object;
	}

	public Object get(Class entityClass, String conditions, HashMap<String, String> params) {
		var pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		var tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		Object object = null;
		try {
			tx.begin();
			var query = pm.newQuery(
					SQL_TYPE,
					String.format(SELECT_CONDITION_QUERY, entityClass.getName(), conditions)
			);
			query.compile();
			query.setUnique(true);
			object = pm.detachCopy(query.executeWithMap(params));
			tx.commit();

		} catch (Exception ex) {
			logger.error("Error getting Object from class {}", entityClass.getName());
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return object;
	}

	public void update(Object object) {
		var pm = pmf.getPersistenceManager();
		var tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(object);
			tx.commit();
		} catch (Exception ex) {
			logger.error("   $ Error retreiving an extent: {}", ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	@Override
	public void delete(Class entityClass, String conditions, HashMap<String, String> params) {
		var pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		var tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		try {

			tx.begin();
			pm.flush();
			var query = pm.newQuery(
					SQL_TYPE,
					String.format(SELECT_CONDITION_QUERY, entityClass.getName(), conditions)
			);

			query.setUnique(true);
			query.deletePersistentAll(params);
			tx.commit();

		} catch (Exception ex) {
			logger.error("Error getting object for deleting: {}", ex.getMessage());
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}	
	
	@Override
	public void delete(Object object) {
		var pm = pmf.getPersistenceManager();
		var tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.flush();
			pm.deletePersistent(object);
			tx.commit();
		} catch (Exception ex) {
			logger.error("   $ Error deleting object: {}", ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}	
}
