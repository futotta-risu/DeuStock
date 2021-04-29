package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import yahoofinance.Stock;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Receives a param. Either small or Big.
 * @author Erik B. Terres
 */
@Path("stock/list/{size}")
public class StockList {

    private StockDataGatewayFactory stockDataGatewayFactory;

    public StockList(){
        stockDataGatewayFactory = StockDataGatewayFactory.getInstance();
    }

    public void setStockDataGatewayFactory(StockDataGatewayFactory factory){
        this.stockDataGatewayFactory = factory;
    }

    private final List<String> smallList = Arrays.asList("INTC","TSLA") ;
    private final List<String> bigList = Arrays.asList("INTC","TSLA","ETH","BB") ;

    private List<String> getStockNameList(String listName){
        return switch(listName){
            case "small" ->  smallList;
            case "big" ->  bigList;
            default ->  throw new IllegalArgumentException("No list named " + listName);
        };
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStock( @PathParam("size") String listSize) {

        List<String> stockList;

        try{
            stockList= getStockNameList(listSize);
        }catch (IllegalArgumentException e){
            return Response.status(401).build();
        }

        StockDataAPIGateway gateway = stockDataGatewayFactory.create(StockDataGatewayEnum.YahooFinance);

        ArrayList<DeuStock> stocks = new ArrayList<>(
                gateway.getStocksData(stockList).values()
        );

        return Response
                .status(Response.Status.OK)
                .entity(stocks)
                .build();
    }
}