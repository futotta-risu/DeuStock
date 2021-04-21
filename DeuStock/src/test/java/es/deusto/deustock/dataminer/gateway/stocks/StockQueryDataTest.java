package es.deusto.deustock.dataminer.gateway.stocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockQueryDataTest {

    @Test
    void testConstructor(){
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        assertEquals(q.getAcronym(),"AMZ");
        assertEquals(q.getInterval(), StockQueryData.Interval.DAILY);
    }

    @Test
    void setAcronym() {
        StockQueryData q = new StockQueryData("GOG", StockQueryData.Interval.DAILY);

        assertEquals(q.getAcronym(),"GOG");
    }

    @Test
    void setInterval() {
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        q.setInterval(StockQueryData.Interval.WEEKLY);
        assertEquals(q.getInterval(), StockQueryData.Interval.WEEKLY);
    }

}