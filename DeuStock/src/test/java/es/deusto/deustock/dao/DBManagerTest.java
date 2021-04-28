package es.deusto.deustock.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;



import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author landersanmillan
 */
class DBManagerTest {
	
	private PersistenceManagerFactory mockPersistentManagerFactory = null;
	private PersistenceManager mockPersistentManager;
	private IDBManager dbManager;
	private Transaction mockTransaction;
	private Query mockQuery = null;


    @BeforeEach
    public void setUp(){
    	mockPersistentManagerFactory = mock(PersistenceManagerFactory.class);
    	mockPersistentManager = mock(PersistenceManager.class);
    	mockTransaction = mock(Transaction.class);
    	mockQuery = mock(Query.class);
    	dbManager = DBManager.getInstance();
    }
    
    @Test
    @DisplayName("Test updateObject function throw error")
    public void testUpdateThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Object object = new Object();

    	//When
        when(mockPersistentManager.makePersistent(any())).thenThrow(re);
        try {
        	dbManager.updateObject(object);
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage() );
		}
    }
    
    @Test
    @DisplayName("Test storeObject function throw error")
    public void testStoreThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Object object = new Object();

    	//When
    	when(mockPersistentManager.makePersistent(any())).thenThrow(re);
        try {
        	dbManager.storeObject(object);
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }

    
    @Test
    @DisplayName("Test getObjects function throw error")
    public void testGetObjectsThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.getExtent(objectClass)).thenThrow(re);
        try {
        	dbManager.getObjects(objectClass);
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test getObjects with condition function throw error")
    public void testGetObjectsWithCondtionThrowsError(){
       	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.newQuery(anyString())).thenThrow(re);
        try {
        	dbManager.getObjects(objectClass, "testConditions");
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test getObject with condition function throw error")
    public void testGetObjectWithCondtionThrowsError(){
       	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.newQuery(anyString())).thenThrow(re);
        try {
        	dbManager.getObject(objectClass, "testConditions");
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    public void testDeleteObjectThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.newQuery(anyString())).thenThrow(re);    
        try {
        	dbManager.deleteObject(objectClass);
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    public void testDeleteObjectWithConditionThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.newQuery(anyString())).thenThrow(re);    
        try {
        	dbManager.deleteObject(objectClass, "");
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    public void testDeleteObjectDoesNotThrowError(){
    	//Given
    	Class<Object> objectClass = Object.class;
    	
    	//When
    	when(mockPersistentManagerFactory.getPersistenceManager()).thenReturn(mockPersistentManager);
        doNothing().when(mockTransaction).begin();         	
        doNothing().when(mockPersistentManager).deletePersistent(objectClass);    
        doNothing().when(mockTransaction).commit();         	
        dbManager.deleteObject(objectClass);
        
		//Then
		//verify(mockTransaction, times(1)).commit();
		//verify(mockPersistentManager, times(1)).deletePersistent(objectClass);
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    public void testDeleteObjectWithConditionDoesNotThrowError(){
    	//Given
    	Class<Object> objectClass = Object.class;
    	
    	//When
    	when(mockPersistentManagerFactory.getPersistenceManager()).thenReturn(mockPersistentManager);
    	when(mockPersistentManager.newQuery(anyString())).thenReturn(mockQuery);
        doNothing().when(mockPersistentManager).deletePersistent(objectClass);    
        	
		//Then
		assertDoesNotThrow(() -> dbManager.deleteObject(objectClass, ""));
    }
    
 
}
