package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Link;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Erik B. Terres
 */
public class StockDAOTest {

    private IDBManager dbManager;
    private StockDAO stockDAO;

    @BeforeEach
    public void setUp(){
        dbManager = mock(DBManager.class);
        stockDAO = StockDAO.getInstance();
        stockDAO.setDbManager(dbManager);
    }

    @Test
    @DisplayName("Test has function returns true on existing object")
    public void testHasOnExistentObject(){
        // Given
        DeuStock stock = new DeuStock("Test");
        when(dbManager.getObject(eq(DeuStock.class), anyString())).thenReturn(stock);

        // When
        final boolean result = stockDAO.has("Test");

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Test has function returns false on existing object")
    public void testHasOnNonExistentObject(){
        // Given
        when(dbManager.getObject(eq(DeuStock.class), anyString())).thenReturn(null);

        // When
        final boolean result = stockDAO.has("Test");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Test store function does not throw error")
    public void testStore(){
        // Given
        DeuStock stock = new DeuStock("TestSymbol");
        doNothing().when(dbManager).storeObject(any());

        // When

        // Then
        assertDoesNotThrow( () -> stockDAO.store(stock));
    }

    @Test
    @DisplayName("Test get function returns true on existing object")
    public void testGetOnExistentObject(){
        // Given
        DeuStock stock = new DeuStock("Test");
        when(dbManager.getObject(eq(DeuStock.class), anyString())).thenReturn(stock);

        // When
        final DeuStock result = stockDAO.get("Test");

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test get function returns false on existing object")
    public void testGetOnNonExistentObject(){
        // Given
        when(dbManager.getObject(eq(DeuStock.class), anyString())).thenReturn(null);

        // When
        final DeuStock result = stockDAO.get("Test");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test update function does not throw error")
    public void testUpdate(){
        // Given
        DeuStock stock = new DeuStock("TestSymbol");
        doNothing().when(dbManager).updateObject(any());

        // When

        // Then
        assertDoesNotThrow( () -> stockDAO.update(stock));
    }

    @Test
    @DisplayName("Test delete function does not throw error")
    public void testDelete(){
        // Given
        DeuStock stock = new DeuStock("TestSymbol");
        doNothing().when(dbManager).deleteObject(eq(DeuStock.class), anyString());

        // When

        // Then
        assertDoesNotThrow( () -> stockDAO.delete(stock));
    }

    @Test
    @DisplayName("Test delete by acronym function does not throw error")
    public void testDeleteByAcronym(){
        // Given
        doNothing().when(dbManager).deleteObject(eq(DeuStock.class), anyString());

        // When

        // Then
        assertDoesNotThrow( () -> stockDAO.deleteBySymbol("Test"));
    }

    @Test
    @DisplayName("Test getAll returns list of stocks")
    public void testGetAllReturnsListOfStocks(){
        // Given
        DeuStock stock = new DeuStock("Test");
        DeuStock stock2 = new DeuStock("Test2");
        List<Object> stocks = new LinkedList<>();
        stocks.add(stock);
        stocks.add(stock2);

        when(dbManager.getObjects(eq(DeuStock.class))).thenReturn(stocks);

        // When
        final List<DeuStock> result = (List<DeuStock>) stockDAO.getAll();

        // Then
        assertNotNull(result);
        assertEquals(result.size(),2);
    }
    
    @Test
    @DisplayName("Test getOrCreateStock function returns stock")
    public void testGetOrCreateStock(){
        DeuStock stock = new DeuStock("Test");

        when(dbManager.getObject(eq(DeuStock.class), anyString())).thenReturn(stock);

        DeuStock stockObtained = stockDAO.getOrCreateStock("acronymTest");
        
        assertNotNull(stockObtained);
        assertEquals(stockObtained.getAcronym(), "Test");
    }

}
