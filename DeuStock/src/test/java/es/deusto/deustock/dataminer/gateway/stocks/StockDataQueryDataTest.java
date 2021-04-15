package es.deusto.deustock.dataminer.gateway.stocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockDataQueryDataTest {

    @Test
    void testConstructor(){
        StockDataQueryData q = new StockDataQueryData("AMZ", StockDataQueryData.Interval.DAILY);
        assertEquals(q.getAcronym(),"AMZ");
        assertEquals(q.getInterval(), StockDataQueryData.Interval.DAILY);
    }

    @Test
    void setAcronym() {
        StockDataQueryData q = new StockDataQueryData("AMZ", StockDataQueryData.Interval.DAILY);
        q.setAcronym("GOG");
        assertEquals(q.getAcronym(),"GOG");
    }

    @Test
    void setInterval() {
        StockDataQueryData q = new StockDataQueryData("AMZ", StockDataQueryData.Interval.DAILY);
        q.setInterval(StockDataQueryData.Interval.WEEKLY);
        assertEquals(q.getInterval(), StockDataQueryData.Interval.WEEKLY);
    }

}