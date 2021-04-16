package es.deusto.deustock.dao;

import es.deusto.deustock.data.User;
import es.deusto.deustock.log.DeuLogger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase de acceso a datos de Usuarios en la BD.<br>
 * <strong>Patterns:</strong>
 * <ul>
 *      <li>DAO</li>
 *      <li>Singleton</li>
 * </ul>
 * 
 * @see GenericDAO
 * @author landersanmillan
 */
public class UserDAO extends GenericDAO {

	private static UserDAO INSTANCE;

	/**
	 * Se obtiene la unica instancia de la clase UserDAO
	 * 
	 * @return <strong>UserDAO</strong> -> Instancia de la clase User
	 */
	public static UserDAO getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UserDAO();
		return INSTANCE;
	}

	/**
	 * Permite almacenar un usuario en la BD
	 * 
	 * @param user -> Objeto usuario que se quiere almacenar en la BD
	 */
	public void storeUser(User user) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			pm.makePersistent(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Permite obtener una lista con todos los usuarios que se encuentran en la BD
	 * 
	 * @return <strong> List[User]</strong> -> Lista que contiene todos los usuarios
	 *         almacenados en la BD
	 */
	public List<User> getUsers() {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);

		Transaction tx = pm.currentTransaction();
		List<User> users = new ArrayList<>();

		try {
			System.out.println("   * Retrieving an Extent for User.");

			tx.begin();
			Extent<User> extent = pm.getExtent(User.class, true);

			for (User u : extent) {
				users.add((User) pm.detachCopy(u));
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error Getting users: " + ex.getMessage());
			DeuLogger.logger.error("Error getting users");
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return users;

	}

	/**
	 * Permite obtener un usuario de la BD a partir de su username
	 * 
	 * @param username -> Nombre del usuario
	 * @return <strong> User </strong> Objeto usuario solicitado
	 */
	public User getUser(String username) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		User user = null;
		pm.setDetachAllOnCommit(true);
		try {
			System.out.println("   * Querying a User: " + username);

			tx.begin();
			Query query = pm
					.newQuery("SELECT FROM " + User.class.getName() + " WHERE username == '" + username + "'");
			query.setUnique(true);
			user = (User) pm.detachCopy((User) query.execute());
			tx.commit();

		} catch (Exception ex) {
			System.out.println("   $ Error Getting User: " + ex.getMessage());
			DeuLogger.logger.error("Error getting user " + username + "" );
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return user;
	}



//	public static void updateUser(User userInfo) {
//		PersistenceManager pm = getPMF().getPersistenceManager();
//		Transaction tx = pm.currentTransaction();
//
//		try {
//			tx.begin();
//			Query<?> query = pm.newQuery("UPDATE " + User.class.getName() + " SET FULLNAME == '" + userInfo.getFullName() +"' WHERE username == '" + userInfo.getUsername() +"'" );
//			query.execute();
//			tx.commit();
//		} catch (Exception ex) {
//			System.out.println("   $ Error Updating user: " + ex.getMessage());
//			DeuLogger.logger.error("Error updating user: " + userInfo.getUsername());
//		} finally {
//			if (tx != null && tx.isActive()) {
//				tx.rollback();
//			}
//
//			pm.close();
//		}
//	}

	/**
	 * Permite eliminar un stock de la BD a partir de su acronimo
	 * 
	 * @param username -> Nombre del usuario que se quiere eliminar
	 */
	public void deleteUser(String username) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		try {
			System.out.println("   * Querying a User: " + username);

			tx.begin();
			Query query = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE username == '" + username + "'");
			query.setUnique(true);
			query.deletePersistentAll();
			tx.commit();

		} catch (Exception ex) {
			System.out.println("   $ Error Getting User: " + ex.getMessage());
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}
}
