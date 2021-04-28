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

import java.util.ArrayList;
import java.util.List;

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
    
    @Test
    @DisplayName("Test getList StockHistoryDTO returns correct List StockHistoryDTO")
    public void testGetListDTO(){
        StockHistory stockHistory1 = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        StockHistory stockHistory2 = new StockHistory(
                wallet, stock, 10, 32, OperationType.LONG
        );
        List<StockHistory> stockHistoryList = new ArrayList<StockHistory>();
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
    public void testCreateStockHistory(){
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
    public void testGetStockHistoryFromWalletId(){
        StockHistory stockHistory1 = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        StockHistory stockHistory2 = new StockHistory(
                wallet, stock, 10, 32, OperationType.LONG
        );
        List<Object> stockHistoryList = new ArrayList<Object>();
        stockHistoryList.add(stockHistory1);
        stockHistoryList.add(stockHistory2);
       
        when(dbManager.getObjects(eq(StockHistory.class), anyString())).thenReturn(stockHistoryList);

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
    public void testGetStockHistoryFromWallet(){
        StockHistory stockHistory1 = new StockHistory(
                wallet, stock, 20, 30, OperationType.LONG
        );
        StockHistory stockHistory2 = new StockHistory(
                wallet, stock, 10, 32, OperationType.LONG
        );
        List<Object> stockHistoryList = new ArrayList<Object>();
        stockHistoryList.add(stockHistory1);
        stockHistoryList.add(stockHistory2);
       
        when(dbManager.getObjects(eq(StockHistory.class))).thenReturn(stockHistoryList);

        List<StockHistory> stockHistoryListActual  = stockHistoryDAO.getStock(wallet);

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
