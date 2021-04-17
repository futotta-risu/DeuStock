package es.deusto.deustock.data.stocks;

import com.google.common.base.Objects;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.OperationType;

import javax.jdo.annotations.PersistenceCapable;
import java.math.BigDecimal;

@PersistenceCapable
public class StockHistory {
    DeuStock stock;
    double price;

    OperationType operation;

    public StockHistory(DeuStock stock, double price, OperationType operation){
        this.stock = stock;
        this.price = price;
        this.operation = operation;
    }

    public DeuStock getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public OperationType getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "StockHistory{" +
                "stock=" + stock.getAcronym() +
                ", price=" + price +
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
}
