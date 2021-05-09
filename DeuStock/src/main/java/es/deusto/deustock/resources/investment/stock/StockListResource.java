package es.deusto.deustock.resources.investment.stock;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.services.investment.stock.StockService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Receives a param. Either small or Big.
 *
 * @author Erik B. Terres
 */
@Path("stock/list/{size}")
public class StockListResource {

    private StockService stockService;

    public StockListResource(){
        stockService = new StockService();
    }

    public void setStockService(StockService stockService){
        this.stockService = stockService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStock( @PathParam("size") String listSize) throws WebApplicationException{

        List<DeuStock> stockList;
        try {
            stockList = stockService.getStockListData(listSize);
        }catch(IllegalArgumentException e){
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response
                .status(Response.Status.OK)
                .entity(stockList)
                .build();
    }
}