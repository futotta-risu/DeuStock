package es.deusto.deustock.client.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import es.deusto.deustock.client.data.stocks.StockHistory;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.stocks.list.StockInfoSellLine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private List<StockInfoSellLine> stockLines;
 
	@FXML
	Label moneyLabel;
	@FXML
	Label stockCuantityLabel;
	@FXML
	VBox stockList;

	@FXML
	public void initialize(){
	}
	
    public CurrentBalanceController(){
        stockLines = new LinkedList<>();
    }
    
	@Override
	public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username"))
            this.username = String.valueOf(params.get("username"));

        initRoot();
	}


    private void initRoot(){
	    System.out.println("Imprimiendo el user " + this.username);
        if(this.username==null) return;

        this.balance = new DeustockGateway().getBalance(this.username);
        System.out.println("El balance es " + this.balance);
        moneyLabel.setText(this.balance  + " €");

        refreshStocks();
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnMouseClicked(mouseEvent -> refreshStocks());
        stockList.getChildren().add(refreshButton);
    }
    

    
    public void refreshStocks(){
    	this.stockList.getChildren().remove(0, this.stockList.getChildren().size());
        List<StockHistory> stockHistories = new DeustockGateway().getHoldings(username);

        stockCuantityLabel.setText("Tienes un total de " + stockHistories.size() + " stocks diferentes");

        int stockWalletIndex = 0;
        for(StockHistory sh : stockHistories){
            StockInfoSellLine stockLine = new StockInfoSellLine(sh);
            stockLines.add(stockLine);
            stockList.getChildren().add(stockLine);
            stockList.getChildren().add(new Separator());

            stockWalletIndex++;
        }
        if(stockHistories.size() == 0) stockList.getChildren().add(new Label("No tienes ningun stock en posesion"));
    }


}
