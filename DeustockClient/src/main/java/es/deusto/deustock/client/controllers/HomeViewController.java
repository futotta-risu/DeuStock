package es.deusto.deustock.client.controllers;
import java.util.HashMap;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

import java.util.HashMap;

public class HomeViewController {

    @FXML
    private VBox stockList;
    
    public HomeViewController(){}
    
    @FXML
    private void initialize(){
        DeustockGateway gateway = new DeustockGateway();

        for(Stock stock : gateway.getStockList()){
            stockList.getChildren().add(new StockInfoLine(stock));
            stockList.getChildren().add(new Separator());
        }
        Button helpView = new Button("Goto Help");
        helpView.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangeScene(
                        ViewPaths.HelpViewPath
                )
        );
        stockList.getChildren().add(helpView);

        HashMap<String, Object> params = new HashMap<>();
        params.put("username","test");
        Button userView = new Button("User");
        userView.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangeSceneWithParams(
                        ViewPaths.UserDetailViewPath, params
                )
        );
        stockList.getChildren().add(userView);
    }
}
