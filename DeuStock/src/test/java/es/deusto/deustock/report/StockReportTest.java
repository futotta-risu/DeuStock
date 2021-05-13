package es.deusto.deustock.report;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.features.SentimentExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("report")
class StockReportTest {

    @Test
    @DisplayName("Test generating a report.")
    void testGenerateReport() throws IOException, InterruptedException, SQLException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(22.0);

        List<HistoricalQuote> quotes = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            HistoricalQuote quote = new HistoricalQuote();
            quote.setClose(new BigDecimal(String.valueOf(20+i)));
            quote.setSymbol("BB");
            Calendar cal = Calendar.getInstance();
            cal.set(2010,i,20);
            quote.setDate(cal);
            quotes.add(quote);
        }

        stock.setHistory(quotes);

        SentimentExtractor extractor = mock(SentimentExtractor.class);
        when(extractor.getSentimentTendency(anyString())).thenReturn(2.0);

        StockReport report = new StockReport(stock);
        report.setExtractor(extractor);

        // When
        File file = report.generate();

        // Then
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Test generating a report.")
    void testGenerateReportDoesNotThrowOnInterruptedThread() throws IOException, InterruptedException, SQLException {
        // Given
        DeuStock stock = new DeuStock("BB").setPrice(22.0);

        List<HistoricalQuote> quotes = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            HistoricalQuote quote = new HistoricalQuote();
            quote.setClose(new BigDecimal(String.valueOf(20+i)));
            quote.setSymbol("BB");
            Calendar cal = Calendar.getInstance();
            cal.set(2010,i,20);
            quote.setDate(cal);
            quotes.add(quote);
        }

        stock.setHistory(quotes);

        SentimentExtractor extractor = mock(SentimentExtractor.class);
        when(extractor.getSentimentTendency(anyString())).thenThrow(new InterruptedException());

        StockReport report = new StockReport(stock);
        report.setExtractor(extractor);

        // When
        File file = report.generate();

        // Then
        assertTrue(file.exists());
    }

}