package es.deusto.deustock.resources.investment.operation;

import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("stock/operation/close")
public class CloseOperationResource {

    private OperationService operationService;

    public CloseOperationResource(){
        operationService = new OperationService();
    }

    public void setOperationService(OperationService operationService){
        this.operationService = operationService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{stockHistoryID}")
    public Response closeOperation(
            @PathParam("stockHistoryID") String stockHistoryID
    ) throws WebApplicationException {
        DeuLogger.logger.info("Petition to close the operation " + stockHistoryID);

        try {
            operationService.closeOperation(stockHistoryID);
        } catch (OperationException | StockNotFoundException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();
    }

}
