package es.deusto.DeuStock.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;


import es.deusto.DeuStock.app.data.User;


/**
 * <strong>Pattern</strong>
 * <ul>
 *      <li>DAO</li>
 *      <li>Singleton</li>
 * </ul>
 */
public class UserDAO extends GenericDAO{
	
    private static UserDAO INSTANCE;
    
    public static UserDAO getInstance() 
	{
        if(INSTANCE == null) 
        {
            INSTANCE = new UserDAO();
        }
  
        return INSTANCE;
    }

	
	public static void storeUser(User user) {
		PersistenceManager pm = getPMF().getPersistenceManager();
	    Transaction tx=pm.currentTransaction();

		try{
	        tx.begin();
	        pm.makePersistent(user);
	        tx.commit();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    finally{
	        if (tx.isActive()){
	            tx.rollback();
	        }
	        pm.close();
	    }
	}
	
	public static List<User> getUsers() {
			PersistenceManager pm = getPMF().getPersistenceManager();
			/*
			 * By default only 1 level is retrieved from the db so if we wish to fetch more
			 * than one level, we must indicate it
			 */
			pm.getFetchPlan().setMaxFetchDepth(3);

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
			} finally {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}

				pm.close();
			}
			return users;
		
	}
	
	public static User getUser(String username) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		User user = null;
		pm.setDetachAllOnCommit(true);
		try {
			System.out.println("   * Querying a User: " + username);

			tx.begin();
			Query<?> query = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE username == '" + username +"'");
			query.setUnique(true);
			user = (User) pm.detachCopy((User) query.execute());
			tx.commit();

		} catch (Exception ex) {
			System.out.println("   $ Error Getting User: " + ex.getMessage());
		} finally {

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return user;
	}
	
	public static boolean checkPassword(String username, String password) {
		return(password.equals(UserDAO.getUser(username).getPassword()));
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
//		} finally {
//			if (tx != null && tx.isActive()) {
//				tx.rollback();
//			}
//
//			pm.close();
//		}
//	}
//	
//	public static void deleteUser(User user) {
//		PersistenceManager pm = getPMF().getPersistenceManager();
//        try{
//        	pm.deletePersistent(user);
//        } catch(Exception ex) {
//        	System.out.println("    $ Error deleting the user: " + ex.getMessage());
//        } finally{
//            pm.close();
//        }
//	}
}
