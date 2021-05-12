package es.deusto.deustock.services.investment.stock;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.services.investment.stock.exceptions.InvalidStockQueryDataException;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("service")
class StockServiceTest {

    private StockDataAPIGateway mockGateway;
    private StockDAO mockStockDAO;

    @BeforeEach
    void setUp(){
        mockGateway = mock(StockDataAPIGateway.class);
        mockStockDAO = mock(StockDAO.class);
    }


    @Test
    void testGetStockDataReturnsStockOnValidData() throws StockNotFoundException, StockException, SQLException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");

        when(mockGateway.getStockData(any())).thenReturn(stock);
        when(mockStockDAO.has(any())).thenReturn(true);
        when(mockStockDAO.get(any())).thenReturn(stockNoPrice);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);
        service.setStockDAO(mockStockDAO);

        // When
        DeuStock result = service.getStockDetailData("BB", "DAILY");

        // Then
        assertEquals(stock.getAcronym(), result.getAcronym());
        assertEquals(stock.getPrice(), result.getPrice());
    }

    @Test
    void testGetStockDataThrowsExceptionOnSQLException() throws StockNotFoundException, StockException, SQLException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");

        when(mockGateway.getStockData(any())).thenReturn(stock);
        when(mockStockDAO.has(any())).thenThrow(new SQLException("Exception"));
        when(mockStockDAO.get(any())).thenReturn(stockNoPrice);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);
        service.setStockDAO(mockStockDAO);

        // When

        // Then
        assertThrows(
            InvalidStockQueryDataException.class,
            () -> service.getStockDetailData("BB", "DAILY")
        );
    }

    @Test
    void testGetStockThrowsExceptionOnBlankStock() throws StockNotFoundException, SQLException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");

        when(mockGateway.getStockData(any())).thenReturn(stock);
        when(mockStockDAO.has(any())).thenReturn(true);
        when(mockStockDAO.get(any())).thenReturn(stockNoPrice);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);

        // When

        // Then
        assertThrows(
                InvalidStockQueryDataException.class,
                () -> service.getStockDetailData("  ", "DAILY")
        );
    }

    @Test
    void testGetStockThrowsExceptionOnBlankInterval() throws StockNotFoundException, SQLException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");
        when(mockGateway.getStockData(any())).thenReturn(stock);
        when(mockStockDAO.has(any())).thenReturn(true);
        when(mockStockDAO.get(any())).thenReturn(stockNoPrice);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);

        // When

        // Then
        assertThrows(
                InvalidStockQueryDataException.class,
                () -> service.getStockDetailData("BB", "  ")
        );
    }

    @Test
    void testGetStockThrowsExceptionOnInvalidInterval() throws StockNotFoundException, SQLException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");
        when(mockGateway.getStockData(any())).thenReturn(stock);
        when(mockStockDAO.has(any())).thenReturn(true);
        when(mockStockDAO.get(any())).thenReturn(stockNoPrice);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);

        // When

        // Then
        assertThrows(
                InvalidStockQueryDataException.class,
                () -> service.getStockDetailData("BB", "FakeInterval")
        );
    }

    @Test
    void testGetStockThrowsExceptionOnStockNotFoundException() throws StockNotFoundException, SQLException {
        // Given
        DeuStock stockNoPrice = new DeuStock("BB");
        when(mockGateway.getStockData(any())).thenThrow(
                new StockNotFoundException(
                        new StockQueryData("BB")
                )
        );
        when(mockStockDAO.has(any())).thenReturn(true);
        when(mockStockDAO.get(any())).thenReturn(stockNoPrice);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);

        // When

        // Then
        assertThrows(
                InvalidStockQueryDataException.class,
                () -> service.getStockDetailData("Test", "DAILY")
        );
    }

    @Test
    void testGetStockThrowsExceptionOnSQLException() throws StockNotFoundException, SQLException {
        // Given
        when(mockGateway.getStockData(any())).thenThrow(
                new StockNotFoundException(
                        new StockQueryData("BB")
                )
        );
        when(mockStockDAO.has(any())).thenThrow(new SQLException("Exception"));


        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);

        // When

        // Then
        assertThrows(
                InvalidStockQueryDataException.class,
                () -> service.getStockDetailData("Test", "DAILY")
        );
    }

    @Test
    void testGetStockWithPriceReturnsCorrectData() throws StockException, SQLException, StockNotFoundException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");

        when(mockGateway.getStockData(any())).thenReturn(stock);
        when(mockStockDAO.has(any())).thenReturn(true);
        when(mockStockDAO.get(any())).thenReturn(stockNoPrice);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);
        service.setStockDAO(mockStockDAO);

        // When
        DeuStock result = service.getStockWithPrice("BB");

        // Then
        assertEquals(stock.getAcronym(), result.getAcronym());
        assertEquals(stock.getPrice(), result.getPrice());
    }

    @ParameterizedTest
    @ValueSource(booleans =  {true, false})
    void testGetStockListDataReturnsListWithSmall(boolean has) throws SQLException {
        HashMap<String,DeuStock> stocks = new HashMap<>();
        stocks.put("BB", new DeuStock("BB").setPrice(20));
        stocks.put("CC", new DeuStock("CC").setPrice(10));

        when(mockStockDAO.has(anyString())).thenReturn(has);
        doNothing().when(mockStockDAO).store(any());
        when(mockGateway.getStocksData(any())).thenReturn(stocks);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);
        service.setStockDAO(mockStockDAO);

        // When
        List<DeuStock> result = service.getStockListData("small");

        // Then
        assertEquals(2, result.size());
    }


    @Test
    void testGetStockListDataDoesNotBreakOnSQLException() throws SQLException {
        HashMap<String,DeuStock> stocks = new HashMap<>();
        stocks.put("BB", new DeuStock("BB").setPrice(20));
        stocks.put("CC", new DeuStock("CC").setPrice(10));

        when(mockStockDAO.has(anyString())).thenThrow(new SQLException("Exception"));
        when(mockGateway.getStocksData(any())).thenReturn(stocks);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);
        service.setStockDAO(mockStockDAO);

        // When
        List<DeuStock> result = service.getStockListData("big");

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void testGetStockListThrowsIllegalArgumentWithUnknownList() throws SQLException {
        HashMap<String,DeuStock> stocks = new HashMap<>();
        stocks.put("BB", new DeuStock("BB").setPrice(20));
        stocks.put("CC", new DeuStock("CC").setPrice(10));

        when(mockStockDAO.has(anyString())).thenThrow(new SQLException("Exception"));
        when(mockGateway.getStocksData(any())).thenReturn(stocks);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);
        service.setStockDAO(mockStockDAO);

        // When

        // Then
        assertThrows(IllegalArgumentException.class, () -> service.getStockListData("unknown"));
    }

    @Test
    void testGetStocksWithPriceReturnsList(){
        // Given
        HashMap<String,DeuStock> stocks = new HashMap<>();
        stocks.put("BB", new DeuStock("BB").setPrice(20));
        stocks.put("CC", new DeuStock("CC").setPrice(10));

        when(mockGateway.getStocksData(any())).thenReturn(stocks);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);

        // When
        List<DeuStock> result = service.getStocksWithPrice(new ArrayList<>(stocks.values()));

        // Then
        assertEquals(2, result.size());

    }
}
