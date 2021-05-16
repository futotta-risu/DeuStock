package es.deusto.deustock.services.investment.operation.type;

/**
 * @author Erik B. Terres
 */
public abstract class Operation implements StockOperation{
    double stockOpenPrice;
    double amount;


    protected Operation(double stockOpenPrice, double amount){

        if(amount <= 0){
            throw new IllegalArgumentException("Cannot create operation on zero or less amount.");
        }

        this.amount = amount;
        this.stockOpenPrice = stockOpenPrice;
        System.out.println("CREADA OPERATION NORMAL");
    }

    public double getAmount() {
        return amount;
    }

    public double getStockOpenPrice() {
        return stockOpenPrice;
    }
}
