package es.deusto.deustock.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import javax.jdo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author Erik B. Terres
 */
@Tag("dbtest")
class DBManagerTest {
	
	private PersistenceManagerFactory mockPMF = null;
	private PersistenceManager mockPM;
	private DBManager dbManager;
	private Transaction mockTransaction;
	private Query mockQuery = null;


    @BeforeEach
    void setUp(){
    	mockPMF = mock(PersistenceManagerFactory.class);
    	mockPM = mock(PersistenceManager.class);
    	mockTransaction = mock(Transaction.class);
    	mockQuery = mock(Query.class);

		when(mockPMF.getPersistenceManager()).thenReturn(mockPM);
		when(mockPM.currentTransaction()).thenReturn(mockTransaction);
		doNothing().when(mockTransaction).begin();
		when(mockTransaction.isActive()).thenReturn(true);
		doNothing().when(mockTransaction).rollback();
		doNothing().when(mockTransaction).commit();

		FetchPlan mockFetchPlan = mock(FetchPlan.class);
		when(mockPM.getFetchPlan()).thenReturn(mockFetchPlan);
		when(mockFetchPlan.setMaxFetchDepth(anyInt())).thenReturn(mockFetchPlan);

		when(mockPM.newQuery(anyString(), anyString())).thenReturn(mockQuery);

    	dbManager = (DBManager) DBManager.getInstance();
    	dbManager.setPFM(mockPMF);
    }
    

    
    @Test
    @DisplayName("Test storeObject catch works")
    void testStoreCatchWorks(){
    	//Given
    	Object object = new Object();

		when(mockPM.makePersistent(any(Object.class))).thenThrow(new IllegalArgumentException("Exception"));

		FetchPlan mockFetchPlan = mock(FetchPlan.class);
		when(mockPM.getFetchPlan()).thenReturn(mockFetchPlan);
		when(mockFetchPlan.setMaxFetchDepth(anyInt())).thenReturn(mockFetchPlan);

		//When
        assertDoesNotThrow( () -> dbManager.store(object) );

    }

	@Test
	@DisplayName("Test storeObject function works")
	void testStoreWorks(){
		//Given
		Object object = new Object();

		when(mockPM.makePersistent(any())).thenReturn(object);

		//When
		assertDoesNotThrow( () -> dbManager.store(object));
	}

	@Test
	@DisplayName("Test updateObject catch works")
	void testUpdateCatchWorks(){
		//Given

		Object object = new Object();

		when(mockPM.makePersistent(any())).thenThrow(new IllegalArgumentException("Exception"));

		//When
		assertDoesNotThrow( () -> dbManager.update(object) );

	}

	@Test
	@DisplayName("Test updateObject function works")
	void testUpdateWorks(){
		//Given

		Object object = new Object();

		when(mockPM.makePersistent(any())).thenReturn(object);

		//When
		assertDoesNotThrow( () -> dbManager.update(object) );

	}

    
    @Test
    @DisplayName("Test getObjects function throw error")
    void testGetObjectsThrowsError(){
    	//Given
    	Class<Object> objectClass = Object.class;

		when(mockPM.getExtent(objectClass)).thenThrow(new IllegalArgumentException("Exception"));

    	//When

        // Then
        assertDoesNotThrow( () -> dbManager.getAll(objectClass) );
    }
    
    @Test
    @DisplayName("Test getList catch works")
    void testGetListWithConditionCatchWorks(){
       	//Given
    	Class<Object> objectClass = Object.class;

		when(mockPM.newQuery(anyString())).thenThrow(new IllegalArgumentException("Exception"));

    	//When
        assertDoesNotThrow(
        		() ->dbManager.getList(objectClass, "testConditions", new HashMap<>())
		);

    }
    
    @Test
    @DisplayName("Test get with condition catch works")
    void testGetObjectWithConditionCatchWorks(){
       	//Given

    	//When
        when(mockPM.newQuery(anyString())).thenThrow(new IllegalArgumentException("Exception"));


        assertDoesNotThrow( () -> dbManager.get(Object.class, "", new HashMap<>()));
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    void testDeleteObjectThrowsError(){
    	//Given
    	RuntimeException re = new RuntimeException("Exception");
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPM.newQuery(anyString())).thenThrow(re);

        assertDoesNotThrow( () -> dbManager.delete(objectClass) );
    }
    
    @Test
    @DisplayName("Test delete with conditions catch works")
    void testDeleteObjectWithConditionThrowsError(){
    	//Given
    	Class<Object> objectClass = Object.class;
    	
    	//When
        when(mockPM.newQuery(anyString())).thenThrow(new IllegalArgumentException("Exception"));

        assertDoesNotThrow(
				() -> dbManager.delete(objectClass, "", new HashMap<>())
		);
    }
    
    @Test
    @DisplayName("Test deleteObject function throw error")
    void testDeleteObjectDoesNotThrowError(){
    	//Given
    	Class<Object> objectClass = Object.class;
    	
    	//When
    	when(mockPMF.getPersistenceManager()).thenReturn(mockPM);
        doNothing().when(mockTransaction).begin();         	
        doNothing().when(mockPM).deletePersistent(objectClass);
        doNothing().when(mockTransaction).commit();

        assertDoesNotThrow( () -> dbManager.delete(objectClass));
    }
    
    @Test
    @DisplayName("Test delete with conditions")
    void testDeleteObjectWithConditionDoesNotThrowError(){
    	//Given
    	Class<Object> objectClass = Object.class;
    	
    	//When
    	when(mockPM.newQuery(anyString())).thenReturn(mockQuery);
        doNothing().when(mockPM).deletePersistent(objectClass);

		doNothing().when(mockPM).setDetachAllOnCommit(anyBoolean());
		doNothing().when(mockQuery).setUnique(anyBoolean());
		when(mockQuery.deletePersistentAll(anyMap())).thenReturn((long) 2);

        	
		//Then
		assertDoesNotThrow(() -> dbManager.delete(objectClass, "", new HashMap<>()));
    }
    
 
}
