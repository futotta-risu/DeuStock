package es.deusto.deustock.client.visual.stocks.list;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Erik B. Terres
 */
public class StockInfoLine extends BorderPane {

    Stock stock;
    Label stockNameLabel, stockPriceLabel;

    public StockInfoLine(Stock stock){
        this.stock = stock;
        initPane();
    }

    private void initPane() {
        this.stockNameLabel = new Label(stock.getName());
        this.stockNameLabel.getStyleClass().add("stock-line-name");

        this.stockPriceLabel = new Label(String.valueOf(stock.getPrice()));
        this.stockPriceLabel.getStyleClass().add("stock-line-price");

        setLeft(stockNameLabel);
        setRight(stockPriceLabel);

        this.stockNameLabel.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangeScene(
                        ViewPaths.StockDetailViewPath
                )
        );

    }

}
