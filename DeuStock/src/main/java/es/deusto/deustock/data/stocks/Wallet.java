package es.deusto.deustock.data.stocks;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PersistenceCapable
public class Wallet {

    @NotPersistent
    private final double INITIAL_MONEY = 5000;

    List<StockHistory> history;
    List<StockHistory> holdings;
    double money;

    public Wallet(){
        this.history = new ArrayList<>();
        this.holdings = new ArrayList<>();

        this.money = INITIAL_MONEY;
    }


    public List<StockHistory> getHistory() {
        return history;
    }

    public List<StockHistory> getHoldings(){
        return this.holdings;
    }

    public double getMoney() {
        return money;
    }

    public boolean hasEnoughMoney(double money){
        return this.money >= money;
    }

    public void changeMoney(double amount)  {
        if(this.getMoney() + amount < 0)
            throw new IllegalArgumentException("Cannot subtract more money than the account has");

        this.money += amount;
    }

    public void addHistory(StockHistory history){
        this.holdings.add(history);
    }

    public void deleteStockHistory(StockHistory history){
        this.holdings.remove(history);
        this.history.add(history);
    }
}
