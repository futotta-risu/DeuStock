package es.deusto.DeuStock.app.dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Clase generia de los objetos DAO. Permite obtener la instancia de Persistence
 * Manager Factory. <br>
 * <strong>Patterns</strong>
 * <ul>
 *     <li>DAO</li>
 *     <li>Singleton</li>
 * </ul>
 * 
 * @author landersanmillan
 */
public class GenericDAO {

	private static PersistenceManagerFactory pmf = null;

	public static PersistenceManagerFactory getPMF() {
		if (pmf == null)
			pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		return pmf;
	}

	public GenericDAO() {
		if (pmf == null)
			pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

}
