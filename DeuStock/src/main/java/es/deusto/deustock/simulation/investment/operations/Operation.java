package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.log.DeuLogger;

/**
 * @author Erik B. Terres
 */
public abstract class Operation implements StockOperation{

    DeuStock stock;

    double stockOpenPrice;
    double amount;

    private Operation(){}

    public Operation(DeuStock stock, double amount){
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