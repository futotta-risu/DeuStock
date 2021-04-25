package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;

public class LongOperation extends Operation  {

	public LongOperation(DeuStock stock, double amount){
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
