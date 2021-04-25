package es.deusto.deustock.client.visual.stocks.list;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.data.stocks.StockHistory;
import es.deusto.deustock.client.data.stocks.Wallet;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * @author landersanmillan
 */
public class StockInfoSellLine extends GridPane{
	
    Stock stock;
    User user;
    Wallet wallet;
    Label stockNameLabel, stockBuyPriceLabel, stockActPriceLabel, priceDiferenceLabel, stockCuantityLabel;
    Button sellButton;
    int indexInWallet;
    double profits, buyPrice;

	public StockInfoSellLine(Stock stock, User user, int indexInWallet) {	
		this.stock = stock;
		this.user = user;
		this.wallet = user.getWallet();
		this.indexInWallet = indexInWallet;
		this.buyPrice = wallet.getHistory().get(indexInWallet).getPrice();
		//Pueden ser beneficios negativos
		this.profits = calculateProfits();
        initPane();
	}
	
    private void initPane() {
        this.stockNameLabel = new Label(stock.getAcronym());
        this.stockNameLabel.getStyleClass().add("stock-line-name");
        
        double buyPrice = this.buyPrice;
        this.stockBuyPriceLabel = new Label(String.valueOf(buyPrice) + " €");
        this.stockBuyPriceLabel.getStyleClass().add("stock-line-price");
        
        this.stockActPriceLabel = new Label(String.valueOf(stock.getPrice()) + " €");
        this.stockActPriceLabel.getStyleClass().add("stock-line-price");
        
        this.stockCuantityLabel = new Label("X " + String.valueOf(this.wallet.getHistory().get(indexInWallet).getAmount()));
                        
        this.priceDiferenceLabel = new Label(this.profits + " €");
        this.priceDiferenceLabel.setTextFill(Color.RED);
        if(this.profits>0) this.priceDiferenceLabel.setTextFill(Color.GREEN);
        
        this.sellButton = new Button("Vender");
        this.sellButton.setTextFill(Color.RED);
        if(this.profits>0) this.sellButton.setTextFill(Color.GREEN);


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
        add(priceDiferenceLabel,4,0);
        add(stockCuantityLabel,5,0);
        add(sellButton,6,0);
    }
    
    public void refreshLines(Stock stock){
        this.stock = stock;
        this.stockActPriceLabel.setText(String.valueOf(this.stock.getPrice()));
        this.priceDiferenceLabel.setText(this.profits + " €");
    }
    
    private double calculateProfits() {
        double actPrice = Double.parseDouble(String.valueOf(this.stock.getPrice()));
        return this.buyPrice-actPrice;
    }
    
    

}
