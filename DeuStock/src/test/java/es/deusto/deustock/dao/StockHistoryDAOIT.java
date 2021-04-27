package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.simulation.investment.operations.OperationType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("dbmanager")
class StockHistoryDAOIT {

    @Test
    void store() {
        Wallet wallet = new Wallet();


        DeuStock stock = StockDAO.getInstance().getOrCreateStock("BB").setPrice(22);
        StockHistory stockHistory = StockHistoryDAO.getInstance().create(
                wallet,
                stock,
                23,
                OperationType.LONG
        );

        StockHistoryDAO.getInstance().store(stockHistory);
    }

    @Test
    void getList() {
        Wallet wallet = new Wallet();
        DeuStock stock = StockDAO.getInstance().getOrCreateStock("BB").setPrice(22);
        StockHistory stockHistory = StockHistoryDAO.getInstance().create(
                wallet,
                stock,
                23,
                OperationType.LONG
        );

        StockHistoryDAO.getInstance().store(stockHistory);
        List<StockHistory> stockHistoryGetList = StockHistoryDAO.getInstance().getStock(wallet);
        assertFalse(stockHistoryGetList.isEmpty());

        StockHistory stockHistoryGet = stockHistoryGetList.get(0);
        assertNotNull(stockHistoryGet.getStock());
        assertEquals(stockHistoryGet.getStock().getAcronym(), "BB");
        assertNotNull(stockHistoryGet.getWallet());
    }

    @Test
    public void testGet(){
        Wallet wallet = new Wallet();
        DeuStock stock = StockDAO.getInstance().getOrCreateStock("BB").setPrice(22);
        StockHistory stockHistory = StockHistoryDAO.getInstance().create(
                wallet,
                stock,
                23,
                OperationType.LONG
        );

        StockHistoryDAO.getInstance().store(stockHistory);
        stockHistory = StockHistoryDAO.getInstance().get("1");
    }

}