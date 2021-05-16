package es.deusto.deustock.resources.investment.stock;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("stock/search/{symbol}")
public class StockSearchResource {

    private StockService stockService;

    public StockSearchResource(){
        stockService = new StockService();
    }

    public void setStockService(StockService stockService){
        this.stockService = stockService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStockFromSearch(@PathParam("symbol") String symbol)
            throws WebApplicationException {

        DeuStock stock;
        try {
            stock = stockService.getStockWithPrice(symbol);
            if(stock==null){
                return Response
                        .status(Response.Status.UNAUTHORIZED)
                        .build();
            }
        } catch (StockException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response
                .status(Response.Status.OK)
                .entity(stock)
                .build();
    }
}
