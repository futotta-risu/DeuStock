package es.deusto.deustock.client.data.stocks;


import es.deusto.deustock.client.simulation.investment.operations.OperationType;

import java.io.Serializable;

/**
 * @author Erik B. Terres
 */
public class StockHistory  implements Serializable {

    long id;
    String symbol;
    double openPrice;
    double actualPrice;
    double amount;
    double openValue;
    double actualValue;
    OperationType operation;

    public StockHistory(){}

    public long getId() {
        return id;
    }

    public StockHistory setId(long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public StockHistory setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public StockHistory setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public double getOpenValue() {
        return openValue;
    }

    public StockHistory setOpenValue(double openValue) {
        this.openValue = openValue;
        return this;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public StockHistory setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public StockHistory setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public double getActualValue() {
        return actualValue;
    }

    public StockHistory setActualValue(double actualValue) {
        this.actualValue = actualValue;
        return this;
    }

    public OperationType getOperation() {
        return operation;
    }

    public StockHistory setOperation(OperationType operation) {
        this.operation = operation;
        return this;
    }

}
