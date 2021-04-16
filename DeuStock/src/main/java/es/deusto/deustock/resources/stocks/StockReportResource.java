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
  public Response createSimplePdfWithChart() throws IOException {
	  YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
	  StockQueryData queryData = new StockQueryData("MSFT", Interval.DAILY);

      DeuStock stock = null;
	try {
		stock = gateway.getStockData(queryData, true);
	} catch (StockNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	  System.out.println("4" + stock.getAcronym());
      return Response.ok(PdfGenerator.getInstance().createPdfStockReport(stock), MediaType.APPLICATION_OCTET_STREAM)
          .header("Content-Disposition", "attachment; filename=\"simplePdf.pdf\"").build();
  }
 
}