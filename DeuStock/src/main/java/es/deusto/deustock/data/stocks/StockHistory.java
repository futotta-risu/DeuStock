package es.deusto.deustock.data.stocks;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.Date;

@PersistenceCapable(detachable = "true")
public class StockHistory  implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    long id;

    long date;
    double price;
    double amount;

    OperationType operation;

    boolean isClosed;

    @Persistent(defaultFetchGroup = "true")
    Wallet wallet;

    @Persistent(defaultFetchGroup = "true")
    DeuStock stock;

    public StockHistory(Wallet wallet, DeuStock stock, double price, double amount, OperationType operation){
        this.stock = stock;
        this.price = price;
        this.amount = amount;
        this.operation = operation;
        this.wallet = wallet;
        this.isClosed = false;
        date =  new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public DeuStock getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public double getAmount(){ return amount;}

    public OperationType getOperation() {
        return operation;
    }

    public Wallet getWallet(){
        return this.wallet;
    }

    public boolean isClosed(){
        return isClosed;
    }

    public StockHistory setClosed(boolean closed) {
        isClosed = closed;
        return this;
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
