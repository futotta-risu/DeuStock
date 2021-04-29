package es.deusto.deustock.client.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import yahoofinance.histquotes.HistoricalQuote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest{

    Stock stock;
    List<HistoricalQuote> history = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        stock = new Stock();
        stock.setPrice(600);
        stock.setFullName("testFullNameStock");
        stock.setAcronym("AC");
        stock.setDescription("Description1");

        HistoricalQuote hq1 = new HistoricalQuote("symbol", Calendar.getInstance(), BigDecimal.valueOf(300), BigDecimal.valueOf(400), BigDecimal.valueOf(100), BigDecimal.valueOf(900), BigDecimal.valueOf(350), Long.parseLong("200"));
        history.add(hq1);
        HistoricalQuote hq2 = new HistoricalQuote("symbol", Calendar.getInstance(), BigDecimal.valueOf(200), BigDecimal.valueOf(300), BigDecimal.valueOf(100), BigDecimal.valueOf(1000), BigDecimal.valueOf(450), Long.parseLong("400"));
        history.add(hq2);

        stock.setHistory(history);
    }

    @Test
    public void testGetPrice(){
        assertEquals(600, stock.getPrice());
    }

    @Test
    public void testSetPrice(){
        stock.setPrice(500);

        assertEquals(500, stock.getPrice());
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
        assertEquals(950, result);
    }


    @Test
    public void testCalculateSd(){
        double result = 0.0, standardDeviation = 0.0;
        for(HistoricalQuote hq : this.history) {
            result += Double.parseDouble(hq.getClose().toString());
        }
        double mean = result/this.history.size();
        for(HistoricalQuote hq : this.history) {
            standardDeviation += Math.pow(Double.parseDouble(hq.getClose().toString()) - mean, 2);
        }

        assertEquals(50, Math.sqrt(standardDeviation/this.history.size()));
    }

}
