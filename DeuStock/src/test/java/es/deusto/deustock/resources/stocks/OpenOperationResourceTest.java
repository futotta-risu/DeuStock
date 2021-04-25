package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.simulation.investment.OperationFactory;
import es.deusto.deustock.simulation.investment.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@Tag("server-resource")
class OpenOperationResourceTest {

    private StockHistoryDAO stockHistoryDAO;
    private WalletService walletService;
    private OperationFactory operationFactory;
    private StockDataAPIGateway stockDataAPIGateway;
    private WalletDAO walletDAO;
    private StockDAO stockDAO;

    @BeforeEach
    public void setUp(){
        stockHistoryDAO = mock(StockHistoryDAO.class);
        walletService =  new WalletService();
        operationFactory = mock(OperationFactory.class);
        stockDataAPIGateway = mock(StockDataAPIGateway.class);
        walletDAO = mock(WalletDAO.class);
        stockDAO = mock(StockDAO.class);
    }

    public void setMocksToResource(CloseOperationResource closeOperationResource){
        closeOperationResource.setOperationFactory(operationFactory);
        closeOperationResource.setStockDataAPIGateway(stockDataAPIGateway);
        closeOperationResource.setStockHistoryDAO(stockHistoryDAO);
        closeOperationResource.setWalletService(walletService);
    }

    @Test
    void openOperation() throws StockNotFoundException {
        Wallet wallet = new Wallet();

        DeuStock stock = new DeuStock("BB").setPrice(22);

        when(walletDAO.getWallet(anyString())).thenReturn(wallet);
        doNothing().when(walletDAO).update(any(Wallet.class));
        doNothing().when(stockHistoryDAO).store(any());

        when(stockDAO.hasStock(anyString())).thenReturn(true);
        when(stockDAO.getStock(anyString())).thenReturn(stock);

        when(stockDataAPIGateway.getStockData(any(StockQueryData.class))).thenReturn(stock);


        WalletService walletService = new WalletService();
        walletService.setWallet(wallet);
        walletService.setWalletDAO(walletDAO);

        walletService.setStockHistoryDAO(stockHistoryDAO);


        OpenOperationResource resource = new OpenOperationResource();
        resource.setStockDataAPIGateway(stockDataAPIGateway);
        resource.setWalletService(walletService);
        resource.setStockDAO(stockDAO);

        Response response =  resource.openOperation("LONG", "BB", "TestWallet", 34);
        assertEquals(response.getStatus(), 200);
        assertEquals(wallet.getMoney(), 4252);
    }
}