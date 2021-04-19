package es.deusto.deustock.dataminer.gateway.stocks.exceptions;

import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.*;

@Tag("investment")
public class StockNotFoundExceptionTest {

    @Test
    public void testCorrectMessage(){
        StockQueryData data = new StockQueryData("AMZN", StockQueryData.Interval.DAILY);

        StockNotFoundException exception = assertThrows(
                StockNotFoundException.class,
                () -> { throw new StockNotFoundException(data); }
        );

        assertEquals("Could not find stock AMZN.", exception.getMessage());
    }
}