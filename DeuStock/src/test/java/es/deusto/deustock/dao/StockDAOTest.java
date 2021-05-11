package es.deusto.deustock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.data.DeuStock;


import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Erik B. Terres
 */
class StockDAOTest {

    private IDBManager dbManager;
    private StockDAO stockDAO;

    @BeforeEach
    void setUp(){
        dbManager = mock(DBManager.class);
        stockDAO = StockDAO.getInstance();
        stockDAO.setDbManager(dbManager);
    }

    @Test
    @DisplayName("Test has function returns true on existing object")
    void testHasOnExistentObject() throws SQLException {
        // Given
        DeuStock stock = new DeuStock("Test");
        when(dbManager.get(eq(DeuStock.class), anyString(),any())).thenReturn(stock);

        // When
        final boolean result = stockDAO.has("Test");

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Test has function returns false on existing object")
    void testHasOnNonExistentObject() throws SQLException {
        // Given
        when(dbManager.get(eq(DeuStock.class), anyString(),any())).thenReturn(null);

        // When
        final boolean result = stockDAO.has("Test");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Test store function does not throw error")
    void testStore() throws SQLException {
        // Given
        DeuStock stock = new DeuStock("TestSymbol");
        doNothing().when(dbManager).store(any());

        // When

        // Then
        assertDoesNotThrow( () -> stockDAO.store(stock));
    }

    @Test
    @DisplayName("Test get function returns true on existing object")
    void testGetOnExistentObject() throws SQLException {
        // Given
        DeuStock stock = new DeuStock("Test");
        when(dbManager.get(eq(DeuStock.class), anyString(),any())).thenReturn(stock);

        // When
        final DeuStock result = stockDAO.get("Test");

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test get function returns false on existing object")
    void testGetOnNonExistentObject() throws SQLException {
        // Given
        when(dbManager.get(eq(DeuStock.class), anyString(),any())).thenReturn(null);

        // When
        final DeuStock result = stockDAO.get("Test");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test update function does not throw error")
    void testUpdate() throws SQLException {
        // Given
        DeuStock stock = new DeuStock("TestSymbol");
        doNothing().when(dbManager).update(any());

        // When

        // Then
        assertDoesNotThrow( () -> stockDAO.update(stock));
    }

    @Test
    @DisplayName("Test delete function does not throw error")
    void testDelete() throws SQLException {
        // Given
        DeuStock stock = new DeuStock("TestSymbol");
        doNothing().when(dbManager).delete(eq(DeuStock.class), anyString(), any());

        // When

        // Then
        assertDoesNotThrow( () -> stockDAO.delete(stock));
    }

    @Test
    @DisplayName("Test delete by acronym function does not throw error")
    void testDeleteByAcronym() throws SQLException {
        // Given
        doNothing().when(dbManager).delete(eq(DeuStock.class), anyString(), any());

        // When

        // Then
        assertDoesNotThrow( () -> stockDAO.deleteBySymbol("Test"));
    }

    @Test
    @DisplayName("Test getAll returns list of stocks")
    void testGetAllReturnsListOfStocks(){
        // Given
        DeuStock stock = new DeuStock("Test");
        DeuStock stock2 = new DeuStock("Test2");
        List<Object> stocks = new LinkedList<>();
        stocks.add(stock);
        stocks.add(stock2);

        when(dbManager.getAll(eq(DeuStock.class))).thenReturn(stocks);

        // When
        final List<DeuStock> result = (List<DeuStock>) stockDAO.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    
    @Test
    @DisplayName("Test getOrCreateStock function returns stock")
    void testGetOrCreateStock() throws SQLException {
        DeuStock stock = new DeuStock("Test");

        when(dbManager.get(eq(DeuStock.class), anyString(),any())).thenReturn(stock);

        DeuStock stockObtained = stockDAO.getOrCreateStock("acronymTest");
        
        assertNotNull(stockObtained);
        assertEquals("Test", stockObtained.getAcronym());
    }

}
