package es.deusto.deustock.resources.investment.operation;

import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.operation.type.OperationType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Erik B. Terres
 */
@Path("stock/operation/open")
public class OpenOperationResource {

    private OperationService operationService;

    public OpenOperationResource(){
        operationService = new OperationService();
    }

    public void setOperationService(OperationService operationService){
        this.operationService = operationService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{operation}/{symbol}/{username}/{amount}")
    public Response openOperation(
            @PathParam("operation") String operationTypeString,
            @PathParam("symbol") String symbol,
            @PathParam("username") String username,
            @PathParam("amount") double amount
    ) throws WebApplicationException {
        DeuLogger.logger.info("Petition to open a operation from " + username);

        OperationType operationType = OperationType.valueOf(operationTypeString);

        try {
            operationService.openOperation(operationType, symbol, username, amount);
        } catch (OperationException | SQLException | StockNotFoundException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();
    }

}
