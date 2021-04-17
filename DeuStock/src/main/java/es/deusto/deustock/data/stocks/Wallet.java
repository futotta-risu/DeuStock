package es.deusto.deustock.data.stocks;


import com.googlecode.javaewah.IntIteratorOverIteratingRLW;
import es.deusto.deustock.data.DeuStock;
import yahoofinance.Stock;

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

    public void changeMoney(double amount) throws Exception {
        if(this.getMoney() + amount < 0)
            throw new Exception("Cannot subtract more money than the account has");

        this.money += amount;
    }

    public void addStock(DeuStock stock, OperationType operation){
        Objects.requireNonNull(history);
        Objects.requireNonNull(operation);

        if(stock.getPrice() == null){
            // This may be processed in other way, but we don't know yet
            return;
        }


        this.holdings.add(
                new StockHistory( stock, stock.getPrice().doubleValue(), operation)
        );
    }

    public void deleteStockHistory(StockHistory history){
        this.history.add(history);
        this.holdings.remove(history);
    }
}
