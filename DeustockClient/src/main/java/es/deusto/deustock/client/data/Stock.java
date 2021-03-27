package es.deusto.deustock.client.data;

public class Stock {

    private String name;
    private Integer change;
    private Integer price;


    public Stock(String name, int change, int price){
        setChange(change);
        setName(name);
        setPrice(price);
    }


    public String getName() {
        return name;
    }


    public int getChange() {
        return change;
    }


    public int getPrice() {
        return price;
    }


    public void setName(String name) {
        this.name =name;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
