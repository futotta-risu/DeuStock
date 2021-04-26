package es.deusto.deustock.client.visual.stocks.list;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.stocks.StockHistory;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * @author landersanmillan
 */
public class StockInfoSellLine extends GridPane{

    StockHistory stockHistory;
    double difference;

    Label stockNameLabel, stockBuyPriceLabel, stockActPriceLabel, priceDifferenceLabel, stockAmountLabel;
    Button sellButton;
    int indexInWallet;

	public StockInfoSellLine(StockHistory stockHistory) {
		this.stockHistory = stockHistory;
        initPane();
	}
	
    private void initPane() {
        this.stockNameLabel = new Label(stockHistory.getSymbol());
        this.stockNameLabel.getStyleClass().add("stock-line-name");

        this.stockBuyPriceLabel = new Label(stockHistory.getOpenPrice() + " €");
        this.stockBuyPriceLabel.getStyleClass().add("stock-line-price");
        
        this.stockActPriceLabel = new Label(stockHistory.getActualPrice() + " €");
        this.stockActPriceLabel.getStyleClass().add("stock-line-price");
        
        this.stockAmountLabel = new Label("X " + stockHistory.getAmount());


        this.difference = stockHistory.getActualPrice() - stockHistory.getOpenPrice();


        this.priceDifferenceLabel = new Label(this.difference + " €");
        this.priceDifferenceLabel.setTextFill(Color.RED);
        if(this.difference>0) this.priceDifferenceLabel.setTextFill(Color.GREEN);
        
        this.sellButton = new Button("Vender");
        this.sellButton.setTextFill(Color.RED);
        if(this.difference>0) this.sellButton.setTextFill(Color.GREEN);


        this.stockNameLabel.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.StockDetailViewPath
                )
        );
        
        //TODO Funcionalidad vender stock
        this.sellButton.setOnMouseClicked(null);
        
        add(stockNameLabel,1,0);
        add(stockBuyPriceLabel,2,0);
        add(stockActPriceLabel,3,0);
        add(priceDifferenceLabel,4,0);
        add(stockAmountLabel,5,0);
        add(sellButton,6,0);
    }

}
