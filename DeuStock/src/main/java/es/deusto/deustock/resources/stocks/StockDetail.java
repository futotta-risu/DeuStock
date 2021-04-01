package es.deusto.deustock.resources.stocks;

import com.google.gson.Gson;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData;
import org.json.simple.JSONObject;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.IOException;

import static es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData.Interval.*;

/**
 * @author Erik B. Terres
 */
@Path("stock/detail/{query}/{interval}")
public class StockDetail {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getStock(
            @PathParam("query") String stockName,
            @PathParam("interval") String interval)
    {
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        StockDataAPIGateway gateway = factory.create(StockDataGatewayEnum.YahooFinance);
        StockDataQueryData queryData;

        // TODO REST Error
        switch(interval){
            case "DAY" -> queryData = new StockDataQueryData(stockName, DAILY);
            case "WEEK" -> queryData = new StockDataQueryData(stockName, WEEKLY);
            case "YEAR" -> queryData = new StockDataQueryData(stockName, YEARLY);
            default -> {
                return new JSONObject();
            }
        }

        DeuStock stock = null;
        try {
            stock = gateway.getStockData(queryData, true);
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        }
        String stockString = new Gson().toJson(stock);
        return new JSONObject(stockString);
    }
}