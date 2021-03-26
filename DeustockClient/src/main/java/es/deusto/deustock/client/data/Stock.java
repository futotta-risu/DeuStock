package es.deusto.deustock.client.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Stock {

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty change = new SimpleIntegerProperty();
    private SimpleIntegerProperty price = new SimpleIntegerProperty();


    public Stock(String name, int change, int price){
        setChange(change);
        setName(name);
        setPrice(price);
    }


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getChange() {
        return change.get();
    }

    public SimpleIntegerProperty changeProperty() {
        return change;
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setChange(int change) {
        this.change.set(change);
    }

    public void setPrice(int price) {
        this.price.set(price);
    }
}
