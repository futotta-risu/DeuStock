package es.deusto.deustock.services.investment.operation.type;

import es.deusto.deustock.data.DeuStock;

public class LongOperation extends Operation  {

	public LongOperation(double stockOpenPrice, double amount){
		super(stockOpenPrice, amount);
	}

	@Override
	public double getOpenPrice() {
		return getAmount()*getStockOpenPrice();
	}

	@Override
	public double getClosePrice(double actualStockPrice) {
		return actualStockPrice * getAmount();
	}

	@Override
	public OperationType getType(){
		return OperationType.LONG;
	}
}
