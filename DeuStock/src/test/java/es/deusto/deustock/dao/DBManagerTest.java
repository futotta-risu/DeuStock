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
    void setUp(){
    	mockPersistentManagerFactory = mock(PersistenceManagerFactory.class);
    	mockPersistentManager = mock(PersistenceManager.class);
    	mockTransaction = mock(Transaction.class);
    	mockQuery = mock(Query.class);
    	dbManager = DBManager.getInstance();
    }
    
    @Test
    @DisplayName("Test updateObject function throw error")
    void testUpdateThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Object object = new Object();

    	//When
        when(mockPersistentManager.makePersistent(any())).thenThrow(re);
        try {
        	dbManager.update(object);
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage() );
		}
    }
    
    @Test
    @DisplayName("Test storeObject function throw error")
    void testStoreThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Object object = new Object();

    	//When
    	when(mockPersistentManager.makePersistent(any())).thenThrow(re);
        try {
        	dbManager.store(object);
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }

    
    @Test
    @DisplayName("Test getObjects function throw error")
    void testGetObjectsThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.getExtent(objectClass)).thenThrow(re);
        try {
        	dbManager.getAll(objectClass);
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test getObjects with condition function throw error")
    void testGetObjectsWithCondtionThrowsError(){
       	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.newQuery(anyString())).thenThrow(re);
        try {
        	dbManager.getList(objectClass, "testConditions");
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test getObject with condition function throw error")
    void testGetObjectWithCondtionThrowsError(){
       	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.newQuery(anyString())).thenThrow(re);
        try {
        	dbManager.getList(objectClass, "testConditions");
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    void testDeleteObjectThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.newQuery(anyString())).thenThrow(re);    
        try {
        	dbManager.delete(objectClass);
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    void testDeleteObjectWithConditionThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPersistentManager.newQuery(anyString())).thenThrow(re);    
        try {
        	dbManager.delete(objectClass, "");
		} catch (Exception e) {
			//Then
			assertEquals(e.getMessage(), re.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    void testDeleteObjectDoesNotThrowError(){
    	//Given
    	Class<Object> objectClass = Object.class;
    	
    	//When
    	when(mockPersistentManagerFactory.getPersistenceManager()).thenReturn(mockPersistentManager);
        doNothing().when(mockTransaction).begin();         	
        doNothing().when(mockPersistentManager).deletePersistent(objectClass);    
        doNothing().when(mockTransaction).commit();         	
        dbManager.delete(objectClass);
        
		//Then
		//verify(mockTransaction, times(1)).commit();
		//verify(mockPersistentManager, times(1)).deletePersistent(objectClass);
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    void testDeleteObjectWithConditionDoesNotThrowError(){
    	//Given
    	Class<Object> objectClass = Object.class;
    	
    	//When
    	when(mockPersistentManagerFactory.getPersistenceManager()).thenReturn(mockPersistentManager);
    	when(mockPersistentManager.newQuery(anyString())).thenReturn(mockQuery);
        doNothing().when(mockPersistentManager).deletePersistent(objectClass);    
        	
		//Then
		assertDoesNotThrow(() -> dbManager.delete(objectClass, ""));
    }
    
 
}
