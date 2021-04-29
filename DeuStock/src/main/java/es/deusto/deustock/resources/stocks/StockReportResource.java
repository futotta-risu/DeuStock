package es.deusto.deustock.resources.stocks;


import java.io.File;
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
import es.deusto.deustock.dataminer.gateway.stocks.gateways.YahooFinanceGateway;

import es.deusto.deustock.report.StockReport;


@Path("reports")
public class StockReportResource {
	
	private StockDataGatewayFactory stockGatewayFactory;
    private YahooFinanceGateway stockGateway;
    
    public StockReportResource() {
    	this.stockGatewayFactory = StockDataGatewayFactory.getInstance();
    	this.stockGateway = (YahooFinanceGateway) this.stockGatewayFactory.create(StockDataGatewayEnum.YahooFinance);
    }
    
    public void setStockGateway(YahooFinanceGateway stockGateway) { this.stockGateway = stockGateway; }
    public void setStockGatewayFactory(StockDataGatewayFactory stockGatewayFactory) { this.stockGatewayFactory = stockGatewayFactory; }
    

    @GET
    @Produces("application/pdf")
    @Path("/{stock}/{interval}")
    public Response createSimplePdfWithChart(@PathParam("stock") String stockAcronym, @PathParam("interval") String interval) throws IOException {

        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);

        StockQueryData queryData = new StockQueryData(
                stockAcronym,
                Interval.valueOf(interval)
        ).setWithHistoric(true);


        DeuStock stock;
        try {
            stock = gateway.getStockData(queryData);
        } catch (StockNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(stock== null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        StockReport report = new StockReport(stock);


        File reportFile = report.generate();


        Response.ResponseBuilder response = Response.ok((Object) reportFile);
        response.header("Content-Disposition","attachment; filename=" + stockAcronym + "_report.pdf");


        return response.build();
    }

}