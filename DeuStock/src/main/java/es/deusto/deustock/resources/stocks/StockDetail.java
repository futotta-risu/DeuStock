package es.deusto.deustock.resources.stocks;

import com.google.gson.Gson;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
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

import static es.deusto.deustock.dataminer.gateway.stocks.StockQueryData.Interval.*;

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

        if(stockName.isBlank() || interval.isBlank()){
            throw new IllegalArgumentException("Invalid arguments");
        }

        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        StockDataAPIGateway gateway = factory.create(StockDataGatewayEnum.YahooFinance);


        StockQueryData.Interval tempInterval = null;
        try{
            tempInterval = StockQueryData.Interval.valueOf(interval);
        }catch(IllegalArgumentException  | NullPointerException e){
            DeuLogger.logger.error("Invalid Interval argument with " + interval + ".");
            throw new IllegalArgumentException("Invalid Interval argument");
        }


        DeuStock stock = null;
        try {
            stock = gateway.getStockData(
                    new StockQueryData(stockName,tempInterval),
                    true
            );
        } catch (StockNotFoundException e) {
            DeuLogger.logger.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

        return (JSONObject) new JSONParser().parse(new Gson().toJson(stock));
    }
}