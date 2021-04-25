package es.deusto.deustock.client.simulation.investment.operations;

import es.deusto.deustock.client.data.Stock;

/**
 * @author Erik B. Terres
 */
public abstract class Operation implements StockOperation{

    Stock stock;

    double stockOpenPrice;
    double amount;

    private Operation(){}

    public Operation(Stock stock, double amount){
        this.stock = stock;
        this.amount = amount;
        this.stockOpenPrice = stock.getPrice();
    }

    public Stock getStock() {
        return stock;
    }

    public double getAmount() {
        return amount;
    }

    public double getStockOpenPrice() {
        return stockOpenPrice;
    }
}
