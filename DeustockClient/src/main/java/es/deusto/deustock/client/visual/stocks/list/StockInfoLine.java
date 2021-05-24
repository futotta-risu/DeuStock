package es.deusto.deustock.client.visual.stocks.list;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.awt.event.MouseEvent;
import java.util.HashMap;

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
        this.stockNameLabel = new Label(stock.getAcronym());
        this.stockNameLabel.getStyleClass().add("stock-line-name");

        this.stockPriceLabel = new Label(String.valueOf(stock.getPrice()));
        this.stockPriceLabel.getStyleClass().add("stock-line-price");

        setLeft(stockNameLabel);
        setRight(stockPriceLabel);

        this.stockNameLabel.setOnMouseClicked(
            MouseEvent -> {
                MainController.getInstance().loadAndChangePaneWithParams(
                        ViewPaths.StockDetailViewPath,
                        new HashMap<>() {{
                            put("acronym", stock.getAcronym());
                        }}
                );
            }
        );
    }

    public void refreshStock(Stock stock){
        this.stock = stock;
        this.stockPriceLabel.setText(String.valueOf(this.stock.getPrice()));
    }

}
