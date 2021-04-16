package es.deusto.deustock.resources.stocks;

import com.google.gson.Gson;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData;
import es.deusto.deustock.log.DeuLogger;

import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData.Interval.*;


/**
 * Receives a param. Either small or Big.
 * @author Erik B. Terres
 */
@Path("stock/list/{size}")
public class StockList {

    private final List<String> smallList = Arrays.asList("INTC","TSLA") ;
    private final List<String> bigList = Arrays.asList("INTC","TSLA","ETH","BB") ;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DeuStock> getStock( @PathParam("size") String listSize) {

        List<String> stockList;
        switch(listSize){
            case "small" -> stockList = smallList;
            case "big" -> stockList = bigList;
            default -> stockList = smallList;
        }
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        StockDataAPIGateway gateway = factory.create(StockDataGatewayEnum.YahooFinance);
        List<DeuStock> stocks = new ArrayList<>();
        for(String stockName : stockList){
            StockDataQueryData queryData = new StockDataQueryData(stockName, DAILY);
            try {
                stocks.add(gateway.getStockData(queryData, false));
            } catch (IOException e) {
                e.printStackTrace();
                /*TODO
                 * gateway?
                 */
                DeuLogger.logger.error("Could not add add Stock");
            }

        }
        return stocks;
    }
}