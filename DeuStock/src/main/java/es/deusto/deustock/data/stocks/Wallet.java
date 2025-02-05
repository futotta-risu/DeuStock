package es.deusto.deustock.data.stocks;

import es.deusto.deustock.data.User;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Wallet of a user.
 * Contains the balance and history of operations of a user.
 *
 * @author Erik B. Terres
 */
@PersistenceCapable(detachable = "true")
public class Wallet  implements Serializable {

    @PrimaryKey
    String id;

    @NotPersistent
    private static final double INITIAL_MONEY = 5000;

    @Persistent(mappedBy = "wallet", defaultFetchGroup = "true")
    private List<StockHistory> history;

    private double money;

    @Persistent(mappedBy = "wallet")
    private User user;

    public Wallet(){
        this.id = UUID.randomUUID().toString();
        this.history = new ArrayList<>();
        this.money = INITIAL_MONEY;
    }

    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public List<StockHistory> getHistory() {
        return history;
    }

    public boolean hasEnoughMoney(double money){
        return this.money >= money;
    }
    
    public void addHistory(StockHistory stockHistory){
        history.add(stockHistory);
    }
	
    public void changeMoney(double amount)  {
        if(this.getMoney() + amount < 0) {
            throw new IllegalArgumentException("Cannot subtract more money than the account has");
        }

        this.money += amount;
    }


}
