package es.deusto.deustock.data.stocks;


import com.googlecode.javaewah.IntIteratorOverIteratingRLW;
import es.deusto.deustock.data.DeuStock;

import javax.jdo.annotations.PersistenceCapable;
import java.util.ArrayList;
import java.util.List;

@PersistenceCapable
public class Wallet {

    private final double INITIAL_MONEY = 5000;

    List<StockHistory> history;
    double money;

    public Wallet(){
        this.history = new ArrayList<>();
        this.money = INITIAL_MONEY;
    }


    public List<StockHistory> getHistory() {
        return history;
    }

    public double getMoney() {
        return money;
    }

    public void changeMoney(double amount) throws Exception {
        if(this.getMoney() + amount < 0)
            throw new Exception("Cannot subtract more money than the account has");

        this.money += amount;
    }

    public void operation(DeuStock stock, OperationType operation){

    }

}
