package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.services.investment.WalletService;

import es.deusto.deustock.services.investment.exceptions.OperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Tag("server-resource")
class OpenOperationResourceTest {

    private StockHistoryDAO stockHistoryDAO;
    private WalletService walletService;
    private StockDataAPIGateway stockDataAPIGateway;
    private WalletDAO walletDAO;
    private StockDAO stockDAO;
    private UserDAO userDAO;

    @BeforeEach
    void setUp(){
        stockHistoryDAO = mock(StockHistoryDAO.class);
        walletService =  new WalletService();
        stockDataAPIGateway = mock(StockDataAPIGateway.class);
        walletDAO = mock(WalletDAO.class);
        stockDAO = mock(StockDAO.class);
        userDAO = mock(UserDAO.class);
    }

    @Test
    void openOperation() throws StockNotFoundException, OperationException, SQLException {
        Wallet wallet = new Wallet();
        User user = new User("TestUser","TestPass");
        user.setWallet(wallet);


        DeuStock stock = new DeuStock("BB").setPrice(22);

        when(walletDAO.getWallet(anyString())).thenReturn(wallet);
        doNothing().when(walletDAO).update(any(Wallet.class));
        doNothing().when(stockHistoryDAO).store(any());

        when(stockDAO.has(anyString())).thenReturn(true);
        when(stockDAO.get(anyString())).thenReturn(stock);

        when(userDAO.getUser(anyString())).thenReturn(user);

        when(stockDataAPIGateway.getStockData(any(StockQueryData.class))).thenReturn(stock);


        walletService = new WalletService();
        walletService.setWalletDAO(walletDAO);

        walletService.setStockHistoryDAO(stockHistoryDAO);


        OpenOperationResource resource = new OpenOperationResource();
        resource.setStockDataAPIGateway(stockDataAPIGateway);
        resource.setWalletService(walletService);
        resource.setStockDAO(stockDAO);
        resource.setUserDAO(userDAO);

        Response response =  resource.openOperation("LONG", "BB", "TestWallet", 34);
        assertEquals(response.getStatus(), 200);
        assertEquals(wallet.getMoney(), 4252);
    }
}