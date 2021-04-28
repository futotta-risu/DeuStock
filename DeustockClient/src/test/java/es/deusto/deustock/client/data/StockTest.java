package es.deusto.deustock.client.data;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import yahoofinance.histquotes.HistoricalQuote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest{

    Stock stock;
    List<HistoricalQuote> history = new ArrayList<>();

    @Before
    public void setUp(){
        stock.setPrice(BigDecimal.valueOf(600));
        stock.setFullName("testFullNameStock");
        stock.setAcronym("AC");
        stock.setDescription("Description1");

        HistoricalQuote hq1 = new HistoricalQuote();
        hq1.setClose(BigDecimal.valueOf(600));
        HistoricalQuote hq2 = new HistoricalQuote();
        hq2.setClose(BigDecimal.valueOf(800));
        history.add(hq2);

        stock.setHistory(history);
    }

    @Test
    public void testGetPrice(){
        assertEquals(BigDecimal.valueOf(600), stock.getPrice());
    }

    @Test
    public void testSetPrice(){
        stock.setPrice(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(500), stock.getPrice());
    }

    @Test
    public void testGetFullName(){
        assertEquals("testFullNameStock", stock.getFullName());
    }

    @Test
    public void testSetFullName(){
        stock.setFullName("testFullNameStock2");
        assertEquals("testFullNameStock2", stock.getFullName());
    }

    @Test
    public void testGetAcronym(){
        assertEquals("AC", stock.getAcronym());
    }

    @Test
    public void testSetAcronym(){
        stock.setAcronym("AC2");
        assertEquals("AC2", stock.getAcronym());
    }

    @Test
    public void testGetDescription(){
        assertEquals("Description1", stock.getDescription());
    }

    @Test
    public void testSetDescription(){
        stock.setDescription("Description2");
        assertEquals("Description2", stock.getDescription());
    }

    @Test
    public void testGetHistory(){
        assertEquals(history, stock.getHistory());
    }


    @Test
    public void testSetHistory(){
        List<HistoricalQuote> history2 = new ArrayList<>();
        HistoricalQuote hq3 = new HistoricalQuote();
        history2.add(hq3);
        stock.setHistory(history2);
        assertEquals(history2, stock.getHistory());
    }

    @Test
    public void testCalculateAveragePrice(){
        double result = 0;
        for (HistoricalQuote hq : this.history){
            result += Double.parseDouble(hq.getClose().toString());
        }
        result = result / this.history.size();
        assertEquals(700, result);
        /**TODO
         * 700?
         *
         */
    }

    /**TODO
     * test calcular SD
     */

    /*
	public double calcularSD(){
        double result = 0.0, standardDeviation = 0.0;

        for(HistoricalQuote hq : this.history) {
            result += Double.parseDouble(hq.getClose().toString());
        }
        double mean = result/this.history.size();
        for(HistoricalQuote hq : this.history) {
            standardDeviation += Math.pow(Double.parseDouble(hq.getClose().toString()) - mean, 2);
        }

        return Math.sqrt(standardDeviation/this.history.size());
    }
     */

}
