package es.deusto.DeuStock.app.jdo;

import java.util.Date;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.DeuStock.app.data.User;

public class JDOTest {

	private PersistenceManagerFactory pmf = null;
	private PersistenceManager pm = null;	
	private Transaction tx = null;


	@Before
    public void setUp() throws Exception {
        // Code executed before each test
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

        System.out.println("DataNucleus AccessPlatform with JDO");
        System.out.println("===================================");

        this.pm = this.pmf.getPersistenceManager();
        this.tx = this.pm.currentTransaction();
    }

	/**
	 * Converts to upper cases. Simple (bogus) method only created to show how to document the input parameters and output of a method.
	 * @param str String to convert to capital letters
	 * @return The string converted into capital letters
	*/
	private String convert2Upcase(String str) {
		return str.toUpperCase();
	}


	/**
	 * Tests User creation
	*/
	@Test
    public void testUserCreation() {
        try{
            tx.begin();
            System.out.println(this.convert2Upcase("Persisting Users"));

            User user1 = new User("username", "password", "fullName", new Date(1234567890), "country", "description");
            User user2 = new User("username2", "password2", "fullName2", new Date(1234567890), "country2", "description2");

            pm.makePersistent(user1);
            pm.makePersistent(user2);

            tx.commit();
            System.out.println("The user have been persisted");
        }finally{
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
	}

	

	/**
	 * Tests User queries
	*/
	@SuppressWarnings("unchecked")
	@Test
    public void testUserQuery() {
        this.pm = this.pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
        try{
            tx.begin();
            System.out.println("Executing Query for ALL Users ");
            Query<User> q=pm.newQuery(User.class);


            for (User u : (List<User>)q.execute()) {
            	System.out.println("USER -->  " + u);
            }
            tx.commit();
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Tests Product deletion
	*/
	@Test
    public void testUserDeletion() {
        pm = this.pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        try{
            tx.begin();
            System.out.println("Deleting all users from persistence");
            Query<User> q = pm.newQuery(User.class);
            long numberInstancesDeleted = q.deletePersistentAll();
            System.out.println("Deleted " + numberInstancesDeleted + " users");
            tx.commit();
        }
        finally{
            if (tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
    }


	@After
    public void tearDown() throws Exception {

        if (this.pm != null) {
			this.pm.close();
		}

    }

}
