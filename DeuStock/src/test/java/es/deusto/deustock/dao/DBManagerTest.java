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

import java.util.*;

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
		when(mockTransaction.isActive()).thenReturn(true);

		//When
        assertDoesNotThrow( () -> dbManager.store(object) );

    }

	@Test
	@DisplayName("Test storeObject catch works with non active transaction")
	void testStoreCatchWorksIsNotActive(){
		//Given
		Object object = new Object();

		when(mockPM.makePersistent(any(Object.class))).thenThrow(new IllegalArgumentException("Exception"));

		FetchPlan mockFetchPlan = mock(FetchPlan.class);
		when(mockPM.getFetchPlan()).thenReturn(mockFetchPlan);
		when(mockFetchPlan.setMaxFetchDepth(anyInt())).thenReturn(mockFetchPlan);
		when(mockTransaction.isActive()).thenReturn(false);

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

	// GET ALL

    @Test
    @DisplayName("Test getAll catch works")
    void testGetAllCatchWorks(){
    	//Given
    	Class<Object> objectClass = Object.class;

		when(mockPM.getExtent(objectClass)).thenThrow(new IllegalArgumentException("Exception"));
		when(mockTransaction.isActive()).thenReturn(true);
    	//When

        // Then
        assertDoesNotThrow( () -> dbManager.getAll(objectClass) );
    }

	@Test
	@DisplayName("Test getAll catch works with non active transaction")
	void testGetAllCatchWorksIsNotActive(){
		//Given
		Class<Object> objectClass = Object.class;

		when(mockPM.getExtent(objectClass)).thenThrow(new IllegalArgumentException("Exception"));
		when(mockTransaction.isActive()).thenReturn(false);
		//When

		// Then
		assertDoesNotThrow( () -> dbManager.getAll(objectClass) );
	}

    // GET LIST

	@Test
	@DisplayName("Test getList  works")
	void testGetListWithConditionWorks(){
		//Given
		Class<Object> objectClass = Object.class;

		when(mockQuery.executeWithMap(anyMap())).thenReturn(new LinkedList<>());

		//When
		assertDoesNotThrow(
				() ->dbManager.getList(objectClass, "testConditions", new HashMap<>())
		);

	}

	@Test
	@DisplayName("Test getList catch works")
	void testGetListWithConditionCatchWorks(){
		//Given
		Class<Object> objectClass = Object.class;

		doThrow(new IllegalArgumentException("Exception")).when(mockQuery).executeWithMap(anyMap());
		when(mockTransaction.isActive()).thenReturn(true);

		//When
		assertDoesNotThrow(
				() ->dbManager.getList(objectClass, "testConditions", new HashMap<>())
		);

	}

	@Test
	@DisplayName("Test getList catch works with non active transaction")
	void testGetListWithConditionCatchWorksIsNotActive(){
		//Given
		Class<Object> objectClass = Object.class;

		doThrow(new IllegalArgumentException("Exception")).when(mockQuery).executeWithMap(anyMap());
		when(mockTransaction.isActive()).thenReturn(false);

		//When
		assertDoesNotThrow(
				() ->dbManager.getList(objectClass, "testConditions", new HashMap<>())
		);

	}

    // GET CONDITION

	@Test
	@DisplayName("Test get with condition works")
	void testGetObjectWithConditionWorks(){
		//Given

		//When
		when(mockPM.detachCopy(any())).thenReturn(new Object());


		assertDoesNotThrow( () -> dbManager.get(Object.class, "", new HashMap<>()));
	}


    @Test
    @DisplayName("Test get with condition catch works")
    void testGetObjectWithConditionCatchWorks(){
       	//Given

    	//When
        doThrow(new IllegalArgumentException("Exception")).when(mockPM).detachCopy(any());
		when(mockTransaction.isActive()).thenReturn(true);

        assertDoesNotThrow( () -> dbManager.get(Object.class, "", new HashMap<>()));
    }

	@Test
	@DisplayName("Test get with condition catch works with non active transaction")
	void testGetObjectWithConditionCatchWorksIsNotActive(){
		//Given

		//When
		doThrow(new IllegalArgumentException("Exception")).when(mockPM).detachCopy(any());
		when(mockTransaction.isActive()).thenReturn(false);

		assertDoesNotThrow( () -> dbManager.get(Object.class, "", new HashMap<>()));
	}

    // UPDATE

	@Test
	@DisplayName("Test update works")
	void testUpdateWorks(){
		//Given

		Object object = new Object();

		when(mockPM.makePersistent(any())).thenReturn(object);

		//When

		// Then
		assertDoesNotThrow( () -> dbManager.update(object) );

	}

	@Test
	@DisplayName("Test update catch works")
	void testUpdateCatchWorks(){
		//Given

		Object object = new Object();

		when(mockPM.makePersistent(any())).thenThrow(new IllegalArgumentException("Exception"));
		when(mockTransaction.isActive()).thenReturn(true);

		//When
		assertDoesNotThrow( () -> dbManager.update(object) );

	}

	@Test
	@DisplayName("Test update catch works with non active transaction")
	void testUpdateCatchWorksIsNotActive(){
		//Given

		Object object = new Object();

		when(mockPM.makePersistent(any())).thenThrow(new IllegalArgumentException("Exception"));
		when(mockTransaction.isActive()).thenReturn(false);

		//When
		assertDoesNotThrow( () -> dbManager.update(object) );

	}

    // DELETE CONDITION
    
    @Test
    @DisplayName("Test delete with conditions catch works")
    void testDeleteObjectWithConditionThrowsError(){
    	//Given
    	Class<Object> objectClass = Object.class;
    	
    	//When
		doThrow(new IllegalArgumentException("Exception")).when(mockQuery).deletePersistentAll(anyMap());
		when(mockTransaction.isActive()).thenReturn(true);

        assertDoesNotThrow(
				() -> dbManager.delete(objectClass, "", new HashMap<>())
		);
    }
	@Test
	@DisplayName("Test delete with conditions catch works with non active transaction")
	void testDeleteObjectWithConditionThrowsErrorIsNotActive(){
		//Given
		Class<Object> objectClass = Object.class;

		//When
		doThrow(new IllegalArgumentException("Exception")).when(mockQuery).deletePersistentAll(anyMap());
		when(mockTransaction.isActive()).thenReturn(false);

		assertDoesNotThrow(
				() -> dbManager.delete(objectClass, "", new HashMap<>())
		);
	}
    
    @Test
    @DisplayName("Test delete with conditions works")
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

    // DELETE

	@Test
	@DisplayName("Test delete object works")
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
	@DisplayName("Test delete object function catch works")
	void testDeleteObjectThrowsError(){
		//Given
		Class<Object> objectClass = Object.class;

		//When
		doThrow(new IllegalArgumentException()).when(mockPM).deletePersistent(any(Object.class));
		when(mockTransaction.isActive()).thenReturn(true);

		// Then
		assertDoesNotThrow( () -> dbManager.delete(objectClass) );
	}

	@Test
	@DisplayName("Test delete object function catch works with non active transaction")
	void testDeleteObjectThrowsErrorIsNotActive(){
		//Given
		Class<Object> objectClass = Object.class;

		//When
		doThrow(new IllegalArgumentException()).when(mockPM).deletePersistent(any(Object.class));
		when(mockTransaction.isActive()).thenReturn(false);

		// Then
		assertDoesNotThrow( () -> dbManager.delete(objectClass) );
	}
    
 
}
