package es.deusto.deustock.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;



import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.data.User;

class DBManagerTest {
	
	private PersistenceManagerFactory mockPersistentManagerFactory = null;
	private PersistenceManager mockPersistentManager;
	private IDBManager dbManager;
	//private Transaction mockTransaction;


    @BeforeEach
    public void setUp(){
    	mockPersistentManagerFactory = mock(PersistenceManagerFactory.class);
    	mockPersistentManager = mock(PersistenceManager.class);
    	dbManager = DBManager.getInstance();
    	//mockTransaction = mock(Transaction.class);
    }
    
    @Test
    @DisplayName("Test updateObject function throw error")
    public void testUpdateThrowsError(){
        when(mockPersistentManager.makePersistent(any())).thenThrow(new RuntimeException("Exception"));
        try {
        	dbManager.updateObject(new User("Test", "Pass"));
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Exception");
		}
    }
    
    @Test
    @DisplayName("Test storeObject function throw error")
    public void testStoreThrowsError(){
        when(mockPersistentManager.makePersistent(any())).thenThrow(new RuntimeException("Exception"));
        try {
        	dbManager.storeObject(new User("Test", "Pass"));
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Exception");
		}
    }

    
    @Test
    @DisplayName("Test getObjects function throw error")
    public void testGetObjectsThrowsError(){
        when(mockPersistentManager.getExtent(Object.class)).thenThrow(new RuntimeException("Exception"));
        try {
        	dbManager.getObjects(User.class);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Exception");
		}
    }
    
    @Test
    @DisplayName("Test getObjects with condition function throw error")
    public void testGetObjectsWithCondtionThrowsError(){
        when(mockPersistentManager.newQuery(anyString())).thenThrow(new RuntimeException("Exception"));
        try {
        	dbManager.getObjects(User.class, "testConditions");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Exception");
		}
    }
    
    @Test
    @DisplayName("Test getObject with condition function throw error")
    public void testGetObjectWithCondtionThrowsError(){
        when(mockPersistentManager.newQuery(anyString())).thenThrow(new RuntimeException("Exception"));
        try {
        	dbManager.getObject(User.class, "testConditions");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Exception");
		}
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    public void testDeleteObjectThrowsError(){
        when(mockPersistentManager.newQuery(anyString())).thenThrow(new RuntimeException("Exception"));
        try {
        	dbManager.deleteObject(User.class, "Test");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Exception");
		}
    }
    
//    @Test
//    @DisplayName("Test deleteObject with condition function throw error")
//    public void testDeleteObjectWithCondtionThrowsError(){
//        when(mockPersistentManager.deletePersistent(any())).thenThrow(new RuntimeException("Exception"));
//        assertThrows(RuntimeException.class, () -> dbManager.deleteObject(new User("Test", "Pass")));
//    }
}
