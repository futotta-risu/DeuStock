package es.deusto.deustock.client.data;

import yahoofinance.histquotes.HistoricalQuote;

import java.util.ArrayList;
import java.util.List;

/**
 * Client object of stock
 * @author Erik B. Terres
 */
public class Stock {

    double price;
    String fullName;
    String acronym;
    String description;

    List<HistoricalQuote> history = new ArrayList<>();


    /**
	 * Stock object empty constructor
	 */
    public Stock(){}
    
    /**
     * Stock object constructor
     * @param acronym String with the acronym of the stock
     * @param price Double with the price of the stock
     */
    public Stock(String acronym, double price){
        this.acronym =acronym;
        this.price = price;
    }

    /**
	 * Stock price getter
	 * @return A double with the stock price
	 */
    public double getPrice() { return price; }
    /**
     * Stock price setter
     * @param price Double for the stock price
     * @return The stock we are setting the price
     */
    public Stock setPrice(double price) {
        this.price = price; return this;
    }
    
    /**
	 * Stock full name getter
	 * @return A string with the stock full name
	 */
    public String getFullName() { return fullName; }
    /**
     * Stock full name setter
     * @param fullName String for the stock full name
     * @return The stock we are setting the full name
     */
    public Stock setFullName(String fullName) {
        this.fullName = fullName; return this;
    }
    
    /**
	 * Stock acronym getter
	 * @return A string with the stock acronym
	 */
    public String getAcronym() { return acronym; }
    /**
     * Stock acronym setter
     * @param acronym String for the stock acronym
     * @return The stock we are setting the acronym
     */
    public Stock setAcronym(String acronym) {
        this.acronym = acronym; return this;
    }
    
    /**
	 * Stock description getter
	 * @return A string with the stock description
	 */
    public String getDescription() { return description; }
    /**
     * Stock description setter
     * @param description String for the stock description
     * @return The stock we are setting the description
     */
    public Stock setDescription(String description) {
        this.description = description; return this;
    }
    
    /**
	 * Stock history getter
	 * @return A list with the stock history
	 */
    public List<HistoricalQuote> getHistory() { return history; }
    /**
     * Stock history setter
     * @param history String for the stock history
     * @return The stock we are setting the history
     */
    public Stock setHistory(List<HistoricalQuote> history) {
        this.history = history; return this;
    }

    /**
     * Calculates the average of the stock price taking notice of the history
     * @return A double with the stock average price
     */
	public double calcularMediaPrecio() {
		double result = 0;
		for (HistoricalQuote hq : this.history) {
			result += Double.parseDouble(hq.getClose().toString());
		}
		result = result / this.history.size();
		return result;
	}

	/**
	 * Calculates the standard deviation of the stock taking notice of the history
	 * @return A double with the stock standard deviation
	 */
	public double calcularSD(){
        double result = 0.0, standardDeviation = 0.0;

        for(HistoricalQuote hq : this.history) {
            result += Double.parseDouble(hq.getClose().toString());
        }
        double mean = result/this.history.size();
        for(HistoricalQuote hq : this.history) {
            standardDeviation += Math.pow(Double.parseDouble(hq.getClose().toString()) - mean, 2);
        }

        return Math.sqrt(standardDeviation/this.history.size());
    }
	
}
