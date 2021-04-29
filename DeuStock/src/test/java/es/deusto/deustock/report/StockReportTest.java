package es.deusto.deustock.report;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.dataminer.gateway.stocks.gateways.YahooFinanceGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("report")
class StockReportTest {

    @Test
    @DisplayName("Test generating a report.")
    public void testGenerateReport() throws StockNotFoundException, IOException {
        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance().create(StockDataGatewayEnum.YahooFinance);

        DeuStock stock = gateway.getStockData(new StockQueryData("BB").setWithHistoric(true));

        StockReport report = new StockReport(stock);
        report.generate();
    }

}