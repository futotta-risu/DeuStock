package es.deusto.deustock.data.stocks;

import com.google.common.base.Objects;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.io.Serializable;
import java.util.Date;

@PersistenceCapable
public class StockHistory  implements Serializable {

    long date;
    DeuStock stock;
    double price;
    double amount;

    OperationType operation;

    boolean isClosed;

    @Persistent
    Wallet wallet;

    public StockHistory(Wallet wallet, DeuStock stock, double price, double amount, OperationType operation){
        this.stock = stock;
        this.price = price;
        this.amount = amount;
        this.operation = operation;
        this.wallet = wallet;
        this.isClosed = false;
        date =  new Date().getTime();
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
