package es.deusto.deustock.dataminer.gateway.stocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockDataQueryDataTest {

    @Test
    void testConstructor(){
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        assertEquals("AMZ", q.getAcronym());
        assertEquals(StockQueryData.Interval.DAILY, q.getInterval());
    }

}