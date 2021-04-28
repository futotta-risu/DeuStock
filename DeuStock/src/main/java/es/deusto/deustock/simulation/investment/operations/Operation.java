package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.log.DeuLogger;

import javax.validation.constraints.Null;

/**
 * @author Erik B. Terres
 */
public abstract class Operation implements StockOperation{

    DeuStock stock;

    double stockOpenPrice;
    double amount;

    private Operation(){}

    public Operation(DeuStock stock, double amount){
        if(stock == null){
            throw new NullPointerException("Cannot create operation on null stock.");
        }

        if(amount <= 0){
            throw new IllegalArgumentException("Cannot create operation on zero or less amount.");
        }

        this.stock = stock;
        this.amount = amount;
        this.stockOpenPrice = stock.getPrice();
    }

    public DeuStock getStock() {
        return stock;
    }

    public double getAmount() {
        return amount;
    }

    public double getStockOpenPrice() {
        return stockOpenPrice;
    }
}
