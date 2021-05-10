package es.deusto.deustock.services.investment.operation.type;

import es.deusto.deustock.data.DeuStock;

/**
 * @author Erik B. Terres
 */
public abstract class Operation implements StockOperation{
    double stockOpenPrice;
    double amount;

    private Operation(){}

    public Operation(double stockOpenPrice, double amount){

        if(amount <= 0){
            throw new IllegalArgumentException("Cannot create operation on zero or less amount.");
        }

        this.amount = amount;
        this.stockOpenPrice = stockOpenPrice;
    }

    public double getAmount() {
        return amount;
    }

    public double getStockOpenPrice() {
        return stockOpenPrice;
    }
}
