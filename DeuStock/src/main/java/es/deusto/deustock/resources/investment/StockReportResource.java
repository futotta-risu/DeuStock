package es.deusto.deustock.resources.investment;


import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData.Interval;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;

import es.deusto.deustock.report.StockReport;


@Path("reports")
public class StockReportResource {

    private StockDataAPIGateway stockGateway;
    
    public StockReportResource() {
    	this.stockGateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YAHOO_FINANCE);
    }
    
    public void setStockGateway(StockDataAPIGateway stockGateway) { this.stockGateway = stockGateway; }


    @GET
    @Produces("application/pdf")
    @Path("/{stock}/{interval}")
    public Response createSimplePdfWithChart(@PathParam("stock") String stockAcronym, @PathParam("interval") String interval) throws IOException {

        StockQueryData queryData = new StockQueryData(
                stockAcronym,
                Interval.valueOf(interval)
        ).setWithHistoric(true);


        DeuStock stock;
        try {
            stock = stockGateway.getStockData(queryData);
        } catch (StockNotFoundException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(stock== null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        var report = new StockReport(stock);

        var reportFile = report.generate();

        Response.ResponseBuilder response = Response.ok(reportFile);
        response.header("Content-Disposition","attachment; filename=" + stockAcronym + "_report.pdf");


        return response.build();
    }

}