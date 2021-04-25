package es.deusto.deustock.data.stocks;

import com.google.common.base.Objects;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class StockHistory {
    DeuStock stock;
    double price;

    OperationType operation;

    Wallet wallet;

    public StockHistory(Wallet wallet, DeuStock stock, double price, OperationType operation){
        this.stock = stock;
        this.price = price;
        this.operation = operation;
        this.wallet = wallet;
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

}
