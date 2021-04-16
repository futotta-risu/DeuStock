package es.deusto.deustock.dataminer.gateway.stocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockQueryDataTest {

    @Test
    void testConstructor(){
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        assertNotNull(q);
    }

    @Test
    void setAcronym() {
        StockQueryData q = new StockQueryData("GOG", StockQueryData.Interval.YEARLY);
        assertEquals(q.getAcronym(),"GOG");
    }

    @Test
    void setInterval() {
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.WEEKLY);
        assertEquals(q.getInterval(), StockQueryData.Interval.WEEKLY);
    }

    @Test
    void testGetFrom(){
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        assertDoesNotThrow(q::getFrom);
    }

    @Test
    void testGetTo(){
        StockQueryData q = new StockQueryData("AMZ", StockQueryData.Interval.DAILY);
        assertDoesNotThrow(q::getTo);
    }

    @Test
    void testCannotCreateNullIntervalStockQueryData(){
        assertThrows(NullPointerException.class,
                () -> new StockQueryData("AMZ",null)
        );
    }

    @Test
    void testCannotCreateEmptyAnnotationStock(){
        assertThrows(IllegalArgumentException.class,
                () -> new StockQueryData(" ", StockQueryData.Interval.WEEKLY)
        );
    }

}