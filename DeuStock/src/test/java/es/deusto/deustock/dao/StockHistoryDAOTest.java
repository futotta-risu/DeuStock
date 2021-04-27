package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Erik B. Terres
 */
public class StockHistoryDAOTest {

    private StockHistoryDAO stockHistoryDAO;
    private IDBManager dbManager;
    private Wallet wallet;
    private DeuStock stock;

    @BeforeEach
    public void setUp(){
        dbManager = mock(DBManager.class);
        stockHistoryDAO = StockHistoryDAO.getInstance();
        stockHistoryDAO.setDbManager(dbManager);

        wallet = new Wallet();
        stock = new DeuStock("BB");
    }

    @Test
    @DisplayName("Test store StockHistory")
    public void testStore(){
        // Given
        StockHistory stockHistory = new StockHistory(
            wallet, stock, 20, 30, OperationType.LONG
        );
        doNothing().when(dbManager).storeObject(any());

        // When

        // Then
        assertDoesNotThrow(() -> stockHistoryDAO.store(stockHistory));
    }

    @Test
    @DisplayName("Test get on existent Object")
    public void testGetOnExistentObject(){
        // Given
        StockHistory stockHistory = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        when(dbManager.getObject(eq(StockHistory.class), anyString())).thenReturn(stockHistory);

        // When
        final StockHistory result = stockHistoryDAO.get("TestID");

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test get on non existent Object")
    public void testGetOnNonExistentObject(){
        // Given
        when(dbManager.getObject(eq(StockHistory.class), anyString())).thenReturn(null);

        // When
        final StockHistory result = stockHistoryDAO.get("TestID");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test update function does not throw error")
    public void testUpdate(){
        // Given
        StockHistory stockHistory = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        doNothing().when(dbManager).updateObject(any());

        // When

        // Then
        assertDoesNotThrow( () -> stockHistoryDAO.update(stockHistory));
    }

    @Test
    @DisplayName("Test getDTO returns correct DTO")
    public void testGetDTO(){
        // Given
        StockHistory stockHistory = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );

        // When
        StockHistoryDTO stockHistoryDTO = stockHistoryDAO.getDTO(stockHistory);

        // Then
        assertEquals(20, stockHistoryDTO.getOpenPrice());
        assertEquals(30, stockHistoryDTO.getAmount());
        assertEquals("BB" , stockHistoryDTO.getSymbol());
        assertEquals(stockHistory.getId(), stockHistoryDTO.getId());

    }


}
