package es.deusto.deustock.client.controllers.stocks;

import es.deusto.deustock.client.controllers.DSGenericController;
import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.simulation.investment.operations.OperationType;
import es.deusto.deustock.client.visual.ViewPaths;

import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.HashMap;

/**
 * @author Erik B. Terres
 */
public class OperationController implements DSGenericController {

	private DeustockGateway gateway;
    private MainController mainController;
	
    @FXML
    Label stockNameLabel, stockPriceLabel, balanceLabel, costLabel;

    @FXML
    TextField amountTextField;

    @FXML
    ChoiceBox<OperationType> operationSelect;

    @FXML
    Button cancelButton, operateButton, calculateCostButton;

    private String username;
    private double balance;
    private Stock stock;

    public OperationController(){
    	this.gateway = new DeustockGateway();
		this.mainController = MainController.getInstance();
    }
    
    public void setDeustockGateway(DeustockGateway gateway){ this.gateway = gateway; }
    public void setMainController(MainController mainController){ this.mainController = mainController; }

    @Override
    public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username")){
            this.username = String.valueOf(params.get("username"));
        }

        if(params.containsKey("stock")){
            this.stock = (Stock) params.get("stock");
        }

        initRoot();
    }
    
    @FXML
    private void initialize() {
        operationSelect.setValue(OperationType.LONG);
        operationSelect.setTooltip(new Tooltip("Select and operation"));
        operationSelect.getItems().setAll(OperationType.values());

        calculateCostButton.setOnMouseClicked(
                e -> costLabel.setText(String.valueOf(getOpenPrice()))
        );
        cancelButton.setOnMouseClicked(
                e -> mainController.loadAndChangePane(
                        ViewPaths.StockListViewPath
                )
        );

        // TODO Security on the amount field
        operateButton.setOnMouseClicked(
                e -> gateway.openOperation(
                        operationSelect.getValue(),
                        stock,
                        mainController.getToken(),
                        // Temporaly solves the case of empty textbox
                        Double.parseDouble("0" + amountTextField.getText()))
        );
    }

    private void initRoot(){

        balance = gateway.getBalance(username);
        stockNameLabel.setText(stock.getAcronym());
        stockPriceLabel.setText(String.valueOf(stock.getPrice()));
        balanceLabel.setText(String.valueOf(balance));
        costLabel.setText("0");
    }

    public double getOpenPrice(){
        if(operationSelect.getValue() == null){
            return 0;
        }

        return switch (operationSelect.getValue()){
            case LONG, SHORT -> (Double.parseDouble(amountTextField.getText()) * this.stock.getPrice());
        };
    }

}
