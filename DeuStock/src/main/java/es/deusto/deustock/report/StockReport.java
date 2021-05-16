package es.deusto.deustock.report;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.features.SentimentExtractor;
import es.deusto.deustock.dataminer.visualization.TimeChart;

import org.apache.pdfbox.pdmodel.PDPage;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;

import org.apache.log4j.Logger;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;

import java.util.stream.Collectors;

import static es.deusto.deustock.util.math.Financial.rsi;
import static es.deusto.deustock.util.math.Statistics.*;
import static es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum.TWITTER;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.TIMES_ROMAN;
import static org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory.createFromImage;

/**
 * @author Erik B. Terres
 */
public class StockReport extends Report {

    private final DeuStock stock;

    private final DecimalFormat decimalFormat =  new DecimalFormat("#.##");

    private static final Logger logger = Logger.getLogger(StockReport.class);

    private SentimentExtractor extractor;

    public StockReport(DeuStock stock){
        super();
        this.stock = stock;
        this.extractor = new SentimentExtractor(TWITTER);
    }

    public void setExtractor(SentimentExtractor extractor){
        this.extractor = extractor;
    }


    @Override
    protected void setMetadata() {
        // TODO Add Metadata
    }

    @Override
    protected String getTitle() {
        return stock.getAcronym();
    }

    @Override
    protected void setContent() throws IOException {
        PDPage page = createPage();

        addActualPrice();
        addMeanPrice();
        addStandardDeviationPrice();
        addSentiment();
        addRSI();
        addStockChart((int) (page.getMediaBox().getWidth()*0.7), (int) page.getMediaBox().getWidth());

        savePage(page);
    }

    private void addActualPrice() throws IOException {
        var date = Calendar.getInstance().getTime().toString();
        double price = stock.getPrice();

        addSimpleTextLine("Precio [ " + date + " ] = " + decimalFormat.format(price) + " €");
    }


    private void addMeanPrice() throws IOException {
        double mean = mean(
                stock.getHistory()
                        .stream()
                        .map(HistoricalQuote::getClose)
                        .map(BigDecimal::doubleValue)
                        .collect(Collectors.toList())
        );


        addSimpleTextLine( "Media del precio: " + decimalFormat.format(mean) + " €");
    }

    private void addStandardDeviationPrice() throws IOException {
        double std = std(stock.getHistory()
                .stream()
                .map(HistoricalQuote::getClose)
                .map(BigDecimal::doubleValue)
                .collect(Collectors.toList()));

        addSimpleTextLine("Desviacion Estandar del precio = " + decimalFormat.format(std)  + " €");

    }

    private void addSentiment() throws IOException {
        double sentiment;

        try {
            sentiment = extractor.getSentimentTendency(this.stock.getAcronym());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(String.format("Could not add sentiment of %s due to error.", stock.getAcronym()));
            addSimpleTextLine( "Error getting sentiment. Please contact us.");
            return;
        }

        contentStream.setFont(TIMES_ROMAN, 12);
        addSimpleTextLine( "Twitter sentiment: " + decimalFormat.format(sentiment));
    }

    private void addStockChart(int height, int width) throws IOException {
        XYChart timeChart = TimeChart.getInstance().getTimeChart(stock, height, width);
        var bufferedTimeChart = BitmapEncoder.getBufferedImage(timeChart);

        contentStream.drawImage(createFromImage(document,bufferedTimeChart), 0, 0);
        contentStream.close();
    }

    private void addRSI() throws IOException {
        double std = rsi(stock.getHistory()
                .stream()
                .map(HistoricalQuote::getClose)
                .map(BigDecimal::doubleValue)
                .collect(Collectors.toList()), 5);

        addSimpleTextLine("RSI(5) = " + decimalFormat.format(std)  + " €");
    }
}
