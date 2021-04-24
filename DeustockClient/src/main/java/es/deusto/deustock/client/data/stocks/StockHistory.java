package es.deusto.deustock.client.data.stocks;

import com.google.common.base.Objects;
import es.deusto.deustock.client.data.Stock;


/**
 * @author Erik B. Terres
 */
public class StockHistory {
    private final Stock stock;
    private final double pricePurchase;
    private double priceSell;

    private boolean isSell = false;

    OperationType operation;

    public StockHistory(Stock stock, double price, OperationType operation){
        this.stock = stock;
        this.pricePurchase = price;
        this.operation = operation;
    }

    public Stock getStock() {
        return stock;
    }

    public double getPricePurchase() {
        return pricePurchase;
    }

    public OperationType getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "StockHistory{" +
                "stock=" + stock.getAcronym() +
                ", price=" + pricePurchase +
                ", operation=" + operation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockHistory that = (StockHistory) o;

        return Double.compare(that.pricePurchase, pricePurchase) == 0
                && Double.compare(that.priceSell, priceSell) == 0
                && isSell == that.isSell
                && Objects.equal(stock, that.stock)
                && operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stock, pricePurchase, priceSell, isSell, operation);
    }

	public double getStockAmmount() {
		
		return 0;
	}
}