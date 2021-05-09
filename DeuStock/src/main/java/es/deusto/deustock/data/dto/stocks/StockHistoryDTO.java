package es.deusto.deustock.data.dto.stocks;

import es.deusto.deustock.services.investment.operation.type.OperationType;

/**
 * @author Erik B. Terres
 */
public class StockHistoryDTO {

    long id;

    String symbol;
    double openPrice;
    double actualPrice;

    double amount;

    double openValue;
    double actualValue;

    OperationType operation;

    public StockHistoryDTO(){}

    public long getId() {
        return id;
    }

    public StockHistoryDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public StockHistoryDTO setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public StockHistoryDTO setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public StockHistoryDTO setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public StockHistoryDTO setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public double getOpenValue() {
        return openValue;
    }

    public StockHistoryDTO setOpenValue(double openValue) {
        this.openValue = openValue;
        return this;
    }

    public double getActualValue() {
        return actualValue;
    }

    public StockHistoryDTO setActualValue(double actualValue) {
        this.actualValue = actualValue;
        return this;
    }

    public OperationType getOperation() {
        return operation;
    }

    public StockHistoryDTO setOperation(OperationType operation) {
        this.operation = operation;
        return this;
    }
}
