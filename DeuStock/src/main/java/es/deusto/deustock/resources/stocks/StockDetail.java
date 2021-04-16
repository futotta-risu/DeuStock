package es.deusto.deustock.resources.stocks;

import com.google.gson.Gson;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData;
import es.deusto.deustock.log.DeuLogger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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
            @PathParam("interval") String interval) throws ParseException {
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
           /*TODO no se consigue obtener el stock data o la conexion con  el gateway?
            */
            DeuLogger.logger.error("Could not get Stock Data.");
            return new JSONObject();
        }
        String stockString = new Gson().toJson(stock);
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(stockString);
    }
}