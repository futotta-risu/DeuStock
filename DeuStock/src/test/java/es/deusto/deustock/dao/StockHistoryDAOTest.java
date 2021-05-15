package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.services.investment.operation.type.OperationType;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik B. Terres
 */
class StockHistoryDAOTest {

    private StockHistoryDAO stockHistoryDAO;
    private IDBManager dbManager;
    private Wallet wallet;
    private DeuStock stock;

    @BeforeEach
    void setUp(){
        dbManager = mock(DBManager.class);
        stockHistoryDAO = StockHistoryDAO.getInstance();
        stockHistoryDAO.setDbManager(dbManager);

        wallet = new Wallet();
        stock = new DeuStock("BB");
    }

    @Test
    @DisplayName("Test store StockHistory")
    void testStore() throws SQLException {
        // Given
        StockHistory stockHistory = new StockHistory(
            wallet, stock, 20, 30, OperationType.LONG
        );
        doNothing().when(dbManager).store(any());

        // When

        // Then
        assertDoesNotThrow(() -> stockHistoryDAO.store(stockHistory));
    }

    @Test
    @DisplayName("Test get on existent Object")
    void testGetOnExistentObject() throws SQLException {
        // Given
        StockHistory stockHistory = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        when(dbManager.get(eq(StockHistory.class), anyString(),any())).thenReturn(stockHistory);

        // When
        final StockHistory result = stockHistoryDAO.get("TestID");

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test get on non existent Object")
    void testGetOnNonExistentObject() throws SQLException {
        // Given
        when(dbManager.get(eq(StockHistory.class), anyString(),any())).thenReturn(null);

        // When
        final StockHistory result = stockHistoryDAO.get("TestID");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test update function does not throw error")
    void testUpdate() throws SQLException {
        // Given
        StockHistory stockHistory = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        doNothing().when(dbManager).update(any());

        // When

        // Then
        assertDoesNotThrow( () -> stockHistoryDAO.update(stockHistory));
    }

    @Test
    @DisplayName("Test getDTO returns correct DTO")
    void testGetDTO(){
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
    
    @Test
    @DisplayName("Test getList StockHistoryDTO returns correct List StockHistoryDTO")
    void testGetListDTO(){
        StockHistory stockHistory1 = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        StockHistory stockHistory2 = new StockHistory(
                wallet, stock, 10, 32, OperationType.LONG
        );
        List<StockHistory> stockHistoryList = new ArrayList<>();
        stockHistoryList.add(stockHistory1);
        stockHistoryList.add(stockHistory2);

        List<StockHistoryDTO> stockHistoryListDTO = stockHistoryDAO.getDTO(stockHistoryList);
        
        assertEquals(stockHistory1.getId(), stockHistoryListDTO.get(0).getId());
        assertEquals(stockHistory2.getId(), stockHistoryListDTO.get(1).getId());

        assertEquals("BB" , stockHistoryListDTO.get(0).getSymbol());
        assertEquals("BB" , stockHistoryListDTO.get(1).getSymbol());
        
        assertEquals(20, stockHistoryListDTO.get(0).getOpenPrice());
        assertEquals(10, stockHistoryListDTO.get(1).getOpenPrice());
       
        assertEquals(30, stockHistoryListDTO.get(0).getAmount());
        assertEquals(32, stockHistoryListDTO.get(1).getAmount());
        
        assertEquals(OperationType.LONG, stockHistoryListDTO.get(0).getOperation());
        assertEquals(OperationType.LONG, stockHistoryListDTO.get(1).getOperation());     
    }
    
    @Test
    @DisplayName("Test create returns correct DTO")
    void testCreateStockHistory(){
    	stock.setPrice(20);
    	double amount = 30;
    	OperationType operationType = OperationType.LONG;

        StockHistory stockHistoryActual  = stockHistoryDAO.create(wallet, stock, amount, operationType);

        assertEquals(wallet, stockHistoryActual.getWallet());
        assertEquals(stock, stockHistoryActual.getStock());
        assertEquals(20, stockHistoryActual.getPrice());
        assertEquals(amount, stockHistoryActual.getAmount());
        assertEquals(operationType, stockHistoryActual.getOperation());
    }
    
    @Test
    @DisplayName("Test getting StockHistoryList object from a walletID")
    void testGetStockHistoryFromWalletId() throws SQLException {
        StockHistory stockHistory1 = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        StockHistory stockHistory2 = new StockHistory(
                wallet, stock, 10, 32, OperationType.LONG
        );
        List<Object> stockHistoryList = new ArrayList<>();
        stockHistoryList.add(stockHistory1);
        stockHistoryList.add(stockHistory2);
       
        when(dbManager.getList(eq(StockHistory.class), anyString(),any())).thenReturn(stockHistoryList);

        List<StockHistory> stockHistoryListActual  = stockHistoryDAO.getStockHistory("Test");

        assertEquals(stockHistory1.getId(), stockHistoryListActual.get(0).getId());
        assertEquals(stockHistory2.getId(), stockHistoryListActual.get(1).getId());

        assertEquals("BB" , stockHistoryListActual.get(0).getStock().getAcronym());
        assertEquals("BB" , stockHistoryListActual.get(1).getStock().getAcronym());
        
        assertEquals(20, stockHistoryListActual.get(0).getPrice());
        assertEquals(10, stockHistoryListActual.get(1).getPrice());
       
        assertEquals(30, stockHistoryListActual.get(0).getAmount());
        assertEquals(32, stockHistoryListActual.get(1).getAmount());
        
        assertEquals(OperationType.LONG, stockHistoryListActual.get(0).getOperation());
        assertEquals(OperationType.LONG, stockHistoryListActual.get(1).getOperation());    
    }
    
    @Test
    @DisplayName("Test getting StockHistoryList object from a wallet")
    void testGetStockHistoryFromWallet() throws SQLException {
        StockHistory stockHistory1 = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        StockHistory stockHistory2 = new StockHistory(
                wallet, stock, 10, 32, OperationType.LONG
        );
        List<Object> stockHistoryList = new ArrayList<>();
        stockHistoryList.add(stockHistory1);
        stockHistoryList.add(stockHistory2);
       
        when(dbManager.getList(eq(StockHistory.class), anyString(), any())).thenReturn(stockHistoryList);

        List<StockHistory> stockHistoryListActual  = stockHistoryDAO.getStockHistory(wallet.getId());

        assertEquals(stockHistory1.getId(), stockHistoryListActual.get(0).getId());
        assertEquals(stockHistory2.getId(), stockHistoryListActual.get(1).getId());

        assertEquals("BB" , stockHistoryListActual.get(0).getStock().getAcronym());
        assertEquals("BB" , stockHistoryListActual.get(1).getStock().getAcronym());
        
        assertEquals(20, stockHistoryListActual.get(0).getPrice());
        assertEquals(10, stockHistoryListActual.get(1).getPrice());
       
        assertEquals(30, stockHistoryListActual.get(0).getAmount());
        assertEquals(32, stockHistoryListActual.get(1).getAmount());
        
        assertEquals(OperationType.LONG, stockHistoryListActual.get(0).getOperation());
        assertEquals(OperationType.LONG, stockHistoryListActual.get(1).getOperation());    
    }
    


}
