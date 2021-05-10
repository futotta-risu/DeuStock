package es.deusto.deustock.resources.investment.stock;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("stock/detail/{symbol}/{interval}")
public class StockDetailResource {

    private StockService stockService;

    public StockDetailResource() {
    	this.stockService = new StockService();
    }
    
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStock(
            @PathParam("symbol") String symbol,
            @PathParam("interval") String interval)
    throws WebApplicationException{

        DeuStock stock;
        try {
            System.out.println("T-1");
            stock = stockService.getStockDetailData(symbol, interval);
        } catch (StockException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response
                .status(Response.Status.OK)
                .entity(stock)
                .build();
    }
}