package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.log.DeuLogger;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("stock/detail/{query}/{interval}")
public class StockDetail {

    private StockDataAPIGateway stockGateway;
    private StockDataGatewayFactory stockGatewayFactory;

    public StockDetail() {
    	this.stockGatewayFactory = StockDataGatewayFactory.getInstance();
    	this.stockGateway = this.stockGatewayFactory.create(StockDataGatewayEnum.YahooFinance);
    }
    
    public void setStockGateway(StockDataAPIGateway stockGateway) { this.stockGateway = stockGateway; }
    public void setStockGatewayFactory(StockDataGatewayFactory stockGatewayFactory) { this.stockGatewayFactory = stockGatewayFactory; }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStock(
            @PathParam("query") String stockName,
            @PathParam("interval") String interval){

        if(stockName.isBlank() || interval.isBlank()){
            return Response.status(401).build();
        }

        StockQueryData.Interval tempInterval;

        try{
            tempInterval = StockQueryData.Interval.valueOf(interval);
        }catch(IllegalArgumentException  | NullPointerException e){
            DeuLogger.logger.error("Invalid Interval argument with " + interval + ".");
            return Response.status(401).build();
        }

        
        DeuStock stock;
        try {
            stock = stockGateway.getStockData(
                    new StockQueryData(stockName,tempInterval).setWithHistoric(true)
            );
        } catch (StockNotFoundException e) {
            DeuLogger.logger.error(e.getMessage());
            return Response.status(401).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(stock)
                .build();
    }
}