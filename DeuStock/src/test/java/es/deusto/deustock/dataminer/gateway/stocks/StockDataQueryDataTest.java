package es.deusto.deustock.dataminer.gateway.stocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockDataQueryDataTest {

    @Test
    void testConstructor(){
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        assertEquals(q.getAcronym(),"AMZ");
        assertEquals(q.getInterval(), StockQueryData.Interval.DAILY);
    }

    @Test
    void setAcronym() {
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        q.setAcronym("GOG");
        assertEquals(q.getAcronym(),"GOG");
    }

    @Test
    void setInterval() {
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        q.setInterval(StockQueryData.Interval.WEEKLY);
        assertEquals(q.getInterval(), StockQueryData.Interval.WEEKLY);
    }

}