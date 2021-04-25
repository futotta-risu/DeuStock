package es.deusto.deustock.client.simulation.investment.operations;

import es.deusto.deustock.client.data.Stock;

public class LongOperation extends Operation  {

	public LongOperation(Stock stock, double amount){
		super(stock, amount);
	}


	@Override
	public double getOpenPrice() {
		return getAmount()*getStockOpenPrice();
	}

	@Override
	public double getClosePrice(double actualPrice) {
		return actualPrice * getAmount();
	}

	@Override
	public OperationType getType(){
		return OperationType.LONG;
	}
}
