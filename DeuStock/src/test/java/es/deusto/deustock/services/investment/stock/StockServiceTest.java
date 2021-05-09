package es.deusto.deustock.services.investment.stock;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.services.investment.stock.exceptions.InvalidStockQueryDataException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("service")
public class StockServiceTest {

    private StockDataAPIGateway mockGateway;

    @BeforeEach
    void setUp(){
        mockGateway = mock(StockDataAPIGateway.class);
    }


    @Test
    void testGetStockDataReturnsStockOnValidData() throws StockNotFoundException, InvalidStockQueryDataException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        when(mockGateway.getStockData(any())).thenReturn(stock);

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);

        // When
        DeuStock result = service.getStockDetailData("BB", "DAILY");

        // Then
        assertEquals(stock, result);
    }

    @Test
    void testGetStockThrowsExceptionOnBlankStock() throws StockNotFoundException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        when(mockGateway.getStockData(any())).thenReturn(stock);

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
    void testGetStockThrowsExceptionOnBlankInterval() throws StockNotFoundException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        when(mockGateway.getStockData(any())).thenReturn(stock);

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
    void testGetStockThrowsExceptionOnInvalidInterval() throws StockNotFoundException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(20);
        when(mockGateway.getStockData(any())).thenReturn(stock);

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
    void testGetStockThrowsExceptionOnStockNotFoundException() throws StockNotFoundException {
        // Given
        when(mockGateway.getStockData(any())).thenThrow(
                new StockNotFoundException(
                        new StockQueryData("BB")
                )
        );

        StockService service = new StockService();
        service.setStockDataAPIGateway(mockGateway);

        // When

        // Then
        assertThrows(
                InvalidStockQueryDataException.class,
                () -> service.getStockDetailData("Test", "DAILY")
        );
    }
}
