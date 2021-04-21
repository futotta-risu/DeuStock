package es.deusto.deustock.resources.stocks;


import java.io.IOException;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData.Interval;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.dataminer.gateway.stocks.gateways.YahooFinanceGateway;
import es.deusto.deustock.util.template.PdfGenerator;



@Path("reports")
@Stateless
public class StockReportResource {
 
 
  @GET
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @Path("/{stock}/{interval}")
  public Response createSimplePdfWithChart(@PathParam("stock") String stockAcronym, @PathParam("interval") String interval) throws IOException, InterruptedException {
	  YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
	  System.out.println("1");

	  StockQueryData queryData = new StockQueryData("MSFT", Interval.DAILY);
	  System.out.println("2");

      DeuStock stock = null;
      try {
    	  stock = gateway.getStockData(queryData);
	  } catch (StockNotFoundException e) {
		  e.printStackTrace();
	  }
      
      // RESPONSE BUILD
      Response response = null;
      if(stock!= null) {
    	  Date actualTime = Calendar.getInstance().getTime();
          response = Response.ok(PdfGenerator.getInstance().createPdfStockReport(stock)
        		  			 , MediaType.APPLICATION_OCTET_STREAM)
        		 .header("Content-Disposition", "attachment; filename=\""+stock.getAcronym()+ "-" + actualTime.toString() + ".pdf\"").build();
      }else {
    	  response = Response.status(404).build();
      }
      
      return response;     
  }
 
}