package es.deusto.deustock.services.investment.operation.type;

/**
 * @author Erik B. Terres
 */
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
