package es.deusto.deustock.client.controllers;

import java.util.HashMap;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.data.stocks.StockHistory;
import es.deusto.deustock.client.data.stocks.Wallet;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import es.deusto.deustock.client.visual.stocks.list.StockInfoSellLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * @author landersanmillan
 */
public class ActualBalanceController implements DSGenericController{
	
    private String username = null;
    private User user;
    private Wallet wallet;
    private HashMap<String, StockInfoSellLine> stockLines;
 
	@FXML
	Label moneyLabel;
	@FXML
	Label stockCuantityLabel;
	@FXML
	VBox stockList;
	@FXML
	Button refreshButton;
	@FXML
	public void initialize(){
	    initRoot();
	}
	
    public ActualBalanceController(){}
    
	@Override
	public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username"))
            this.username = String.valueOf(params.get("username"));

        initRoot();
	}
	
    private void getUser(){
        DeustockGateway gateway = new DeustockGateway();
        this.user = gateway.getUser(this.username);
    }

    private void initRoot(){
        if(this.username==null) return;

        if(this.user == null || !this.user.getUsername().equals(this.username))
            getUser();

        if(this.user==null) return;
        
        wallet = this.user.getWallet();
        moneyLabel.setText(calculateActualBalance() + " €");
        
        int totalStocks = 0;
        for (StockHistory sh : wallet.getHistory()) {
			if(!sh.isClosed()) totalStocks++;
		}
        stockCuantityLabel.setText("Tienes un total de " + String.valueOf(totalStocks) + " stocks diferentes");
                
        stockLines = new HashMap<>();
        refreshStocks();
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnMouseClicked(mouseEvent -> refreshStocks());
        stockList.getChildren().add(refreshButton);
    }
    
    
    private double calculateActualBalance() {
    	double result = 0;
    	for (StockHistory sh : this.wallet.getHistory()) {
			if(!sh.isClosed()) result += Double.parseDouble(sh.getStock().getPrice()+"");
		}
    	result += this.wallet.getMoney();
    	return result;
    }
    
    public void refreshStocks(){
    	this.stockList.getChildren().remove(0, this.stockList.getChildren().size());
        DeustockGateway gateway = new DeustockGateway();
        int stockWalletIndex = 0;
        for(StockHistory sh : this.wallet.getHistory()){
            if(!sh.isClosed()) {
            	StockInfoSellLine stockLine = new StockInfoSellLine(sh.getStock(), this.user, stockWalletIndex);
                stockLines.put(sh.getStock().getAcronym(), stockLine);
                               
                stockList.getChildren().add(stockLine);
                stockList.getChildren().add(new Separator());
            }
            stockWalletIndex++;
        }
        if(stockWalletIndex == 0) stockList.getChildren().add(new Label("No tienes ningun stock en posesion"));
    }

   
}
