package es.deusto.deustock.client.controllers;

import java.util.HashMap;
import java.util.List;

import es.deusto.deustock.client.data.stocks.StockHistory;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.stocks.list.StockInfoSellLine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * @author landersanmillan
 */
public class CurrentBalanceController implements DSGenericController {
	
    private String username;
    private double balance;

	@FXML
	Label moneyLabel;
	@FXML
	Label stockQuantityLabel;
	@FXML
	VBox stockList;

    public CurrentBalanceController(){}
    
	@Override
	public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username"))
            this.username = String.valueOf(params.get("username"));

        initRoot();
	}


    private void initRoot(){
        if(this.username==null) return;

        this.balance = new DeustockGateway().getBalance(this.username);

        moneyLabel.setText(this.balance  + " €");

        refreshStocks();
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnMouseClicked(mouseEvent -> refreshStocks());
        stockList.getChildren().add(refreshButton);
        
    }
    

    
    public void refreshStocks(){
    	this.stockList.getChildren().remove(0, this.stockList.getChildren().size());
        List<StockHistory> stockHistories = new DeustockGateway().getHoldings(username);
        
        stockQuantityLabel.setText("You have a total of " + stockHistories.size() + " different stocks");

        for(StockHistory sh : stockHistories){
            StockInfoSellLine stockLine = new StockInfoSellLine(sh);
            stockList.getChildren().add(stockLine);
            stockList.getChildren().add(new Separator());
        }

        if(stockHistories.size() == 0){
            stockList.getChildren().add(new Label("You don't have stocks"));
        }

    }
    

}
