package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("dbmanager")
class StockHistoryDAOIT {

    @Test
    void store() throws SQLException {
        // Given
        Wallet wallet = new Wallet();

        DeuStock stock = StockDAO.getInstance().getOrCreateStock("BB").setPrice(22);
        StockHistory stockHistory = StockHistoryDAO.getInstance().create(
                wallet,
                stock,
                23,
                OperationType.LONG
        );
        long id = stockHistory.getId();

        // When
        StockHistoryDAO.getInstance().store(stockHistory);

        // Then
        assertDoesNotThrow(() -> StockHistoryDAO.getInstance().get(String.valueOf(id)));

    }

    @Test
    void getList() throws SQLException {
        Wallet wallet = new Wallet();
        DeuStock stock = StockDAO.getInstance().getOrCreateStock("BB").setPrice(22);
        StockHistory stockHistory = StockHistoryDAO.getInstance().create(
                wallet,
                stock,
                23,
                OperationType.LONG
        );

        StockHistoryDAO.getInstance().store(stockHistory);
        List<StockHistory> stockHistoryGetList = StockHistoryDAO.getInstance().getStockHistory(wallet.getId());
        assertFalse(stockHistoryGetList.isEmpty());

        StockHistory stockHistoryGet = stockHistoryGetList.get(0);
        assertNotNull(stockHistoryGet.getStock());
        assertEquals(stockHistoryGet.getStock().getAcronym(), "BB");
        assertNotNull(stockHistoryGet.getWallet());
    }

    @Test
    public void testGet() throws SQLException {
        Wallet wallet = new Wallet();
        DeuStock stock = StockDAO.getInstance().getOrCreateStock("BB").setPrice(22);
        StockHistory stockHistory = StockHistoryDAO.getInstance().create(
                wallet,
                stock,
                23,
                OperationType.LONG
        );
        long id = stockHistory.getId();

        StockHistoryDAO.getInstance().store(stockHistory);

        assertDoesNotThrow( () -> StockHistoryDAO.getInstance().get(String.valueOf(id)));
    }

}