package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;

public class LongOperation extends Operation  {

	public LongOperation(DeuStock stock, double amount){
		super(stock, amount);
	}


	@Override
	public double getOpenPrice() {
		return 0;
	}

	@Override
	public double getClosePrice(double actualPrice) {
		return 0;
	}

	@Override
	public OperationType getType(){
		return OperationType.LONG;
	}
}
