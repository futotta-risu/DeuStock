package es.deusto.deustock.client.data.stocks;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.deusto.deustock.client.data.User;

public class Wallet implements Serializable {

    String id;

    private final double INITIAL_MONEY = 5000;

    List<StockHistory> history;

    double money;

    User user;

    public Wallet(){
        this.id = UUID.randomUUID().toString();
        this.history = new ArrayList<>();

        this.money = INITIAL_MONEY;
    }


    public List<StockHistory> getHistory() {
        return history;
    }


    public double getMoney() {
        return money;
    }

    public String getId() {
        return id;
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
        this.history.add(history);
    }

}
