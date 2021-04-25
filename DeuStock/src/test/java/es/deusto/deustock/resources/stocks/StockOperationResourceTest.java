package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.simulation.investment.WalletService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static es.deusto.deustock.simulation.investment.operations.OperationType.LONG;

import javax.ws.rs.core.Response;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@Tag("server-resource")
class StockOperationResourceTest{

    @Test
    void openOperation() throws StockNotFoundException {

        Wallet wallet = new Wallet();
        WalletDAO walletDAO = mock(WalletDAO.class);
        when(walletDAO.getWallet(anyString())).thenReturn(wallet);

        WalletService walletService = new WalletService();
        walletService.setWallet(wallet);
        walletService.setWalletDAO(walletDAO);


        StockHistoryDAO stockHistoryDAO = mock(StockHistoryDAO.class);
        doNothing().when(stockHistoryDAO).store(any());
        walletService.setStockHistoryDAO(stockHistoryDAO);

        StockDataAPIGateway stockDataAPIGateway = mock(StockDataAPIGateway.class);
        when(stockDataAPIGateway.getStockData(any())).thenReturn(
                new DeuStock("BB").setPrice(22)
        );

        StockOperationResource resource = new StockOperationResource();
        resource.setStockDataAPIGateway(stockDataAPIGateway);
        resource.setWalletService(walletService);

        Response response =  resource.openOperation("LONG", "BB", "TestWallet", 34);
        assertEquals(response.getStatus(), 200);
        assertEquals(wallet.getMoney(), 4252);
    }
}