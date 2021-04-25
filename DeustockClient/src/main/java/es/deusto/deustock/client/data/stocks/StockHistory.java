package es.deusto.deustock.client.data.stocks;


import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.simulation.investment.operations.OperationType;

import java.io.Serializable;
import java.util.Date;

public class StockHistory  implements Serializable {

    long date;
    Stock stock;
    double price;
    double amount;

    OperationType operation;

    boolean isClosed;

    Wallet wallet;

    public StockHistory(Wallet wallet, Stock stock, double price, double amount, OperationType operation){
        this.stock = stock;
        this.price = price;
        this.amount = amount;
        this.operation = operation;
        this.wallet = wallet;
        this.isClosed = false;
        date =  new Date().getTime();
    }

    public Stock getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public OperationType getOperation() {
        return operation;
    }
    
    public double getAmount() {
    	return amount;
    }
    
    public boolean isClosed() {
    	return isClosed;
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
