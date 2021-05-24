package es.deusto.deustock.client.visual.stocks.list;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Line of information of a stock used for insert in a list
 * 
 * @author Erik B. Terres
 */
public class StockInfoLine extends BorderPane {

    Stock stock;
    Label stockNameLabel, stockPriceLabel;

    /**
     * Constructor for class StockInfoLine
     * 
     * @param stock stock used for inserting the information on line labels
     */
    public StockInfoLine(Stock stock){
        this.stock = stock;
        initPane();
    }

    private void initPane() {
        this.stockNameLabel = new Label(stock.getAcronym());
        this.stockNameLabel.getStyleClass().add("stock-line-name");

        this.stockPriceLabel = new Label(String.valueOf(stock.getPrice()));
        this.stockPriceLabel.getStyleClass().add("stock-line-price");

        setLeft(stockNameLabel);
        setRight(stockPriceLabel);

        this.stockNameLabel.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.StockDetailViewPath
                )
        );
    }

    /**
     * Method for refreshing a stock in a line
     * 
     * @param stock The stock we want to write in the line
     */
    public void refreshStock(Stock stock){
        this.stock = stock;
        this.stockPriceLabel.setText(String.valueOf(this.stock.getPrice()));
    }

}
