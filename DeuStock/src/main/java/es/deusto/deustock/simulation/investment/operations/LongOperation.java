package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.OperationType;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;

public class LongOperation implements StockOperation{

    private LongOperation(){}

    @Override
    public void operate(Wallet wallet, DeuStock stock) {
       //TODO
        return;
    }

	@Override
	public double getActualValue(StockHistory story) {
		DeuStock stock = story.getStock();
		StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
		StockDataAPIGateway gateway = factory.create(StockDataGatewayEnum.YahooFinance);
		double priceActual = gateway.getActualPrice(stock.getAcronym());
		
		double actualCapital =  priceActual * story.getStockAmmount();
		return 0;
	}
    
    
}
