package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.services.investment.OperationFactory;
import es.deusto.deustock.services.investment.WalletService;
import es.deusto.deustock.services.investment.operations.LongOperation;
import es.deusto.deustock.services.investment.operations.Operation;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static es.deusto.deustock.services.investment.operations.OperationType.LONG;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("server-resource")
class CloseOperationResourceTest {

    private StockHistoryDAO stockHistoryDAO;
    private WalletService walletService;
    private OperationFactory operationFactory;
    private StockDataAPIGateway stockDataAPIGateway;
    private WalletDAO walletDAO;

    @BeforeEach
    public void setUp(){
        stockHistoryDAO = mock(StockHistoryDAO.class);
        walletService =  new WalletService();
        operationFactory = mock(OperationFactory.class);
        stockDataAPIGateway = mock(StockDataAPIGateway.class);
        walletDAO = mock(WalletDAO.class);
    }

    public void setMocksToResource(CloseOperationResource closeOperationResource){
        closeOperationResource.setOperationFactory(operationFactory);
        closeOperationResource.setStockDataAPIGateway(stockDataAPIGateway);
        closeOperationResource.setStockHistoryDAO(stockHistoryDAO);
        closeOperationResource.setWalletService(walletService);
    }

    @Test
    void closeOperationWorksWithValidData() throws StockNotFoundException, SQLException {
        int stockAmount = 30;
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("BB").setPrice(22);

        StockHistory stockHistory = new StockHistory(
                wallet, stock, 20, stockAmount,  LONG
        );

        Operation operation = new LongOperation(stock, stockAmount);

        when(stockHistoryDAO.get(any())).thenReturn(stockHistory);
        when(stockDataAPIGateway.getStockData(any(StockQueryData.class))).thenReturn(stock);
        when(operationFactory.create(any(),any(),anyDouble())).thenReturn(operation);
        doNothing().when(stockHistoryDAO).update(any());
        doNothing().when(walletDAO).update(any(Wallet.class));

        walletService.setStockHistoryDAO(stockHistoryDAO);
        walletService.setWalletDAO(walletDAO);

        CloseOperationResource closeOperationResource = new CloseOperationResource();
        setMocksToResource(closeOperationResource);

        Response response = closeOperationResource.closeOperation("2");

        assertEquals(200, response.getStatus());
        assertNotEquals(5000, wallet.getMoney());
    }

    @Test
    void closeOperationThrowsErrorOnInvalidID() throws StockNotFoundException, SQLException {
        int stockAmount = 30;
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("BB").setPrice(22);

        StockHistory stockHistory = new StockHistory(
                wallet, stock, 20, stockAmount,  LONG
        );

        Operation operation = new LongOperation(stock, stockAmount);

        when(stockHistoryDAO.get(any())).thenReturn(null);
        when(stockDataAPIGateway.getStockData(any(StockQueryData.class))).thenReturn(stock);
        when(operationFactory.create(any(),any(),anyDouble())).thenReturn(operation);
        doNothing().when(stockHistoryDAO).update(any());
        doNothing().when(walletDAO).update(any(Wallet.class));

        walletService.setStockHistoryDAO(stockHistoryDAO);
        walletService.setWalletDAO(walletDAO);

        CloseOperationResource closeOperationResource = new CloseOperationResource();
        setMocksToResource(closeOperationResource);

        Response response = closeOperationResource.closeOperation("2");

        assertEquals(401, response.getStatus());

    }
}