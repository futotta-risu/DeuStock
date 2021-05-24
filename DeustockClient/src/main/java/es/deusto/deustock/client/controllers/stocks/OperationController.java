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
 * Controller class that contains functions for the control of the OperationView.fxml view
 *
 * @author Erik B. Terres
 */
public class OperationController implements DSGenericController {

    @FXML
    private Label stockNameLabel, stockPriceLabel, balanceLabel, costLabel;

    @FXML
    private TextField amountTextField;

    @FXML
    private ChoiceBox<OperationType> operationSelect;

    @FXML
    private Button cancelButton, operateButton, calculateCostButton;

    private String username;
    private double balance;
    private Stock stock;


    /**
     * Method that sets the parameters of the class, username and stock
     * @param params collects all the received objects with their respective key in a HashMap
     */

    @Override
    public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username")){
            this.username = (String) params.get("username");
        }

        if(params.containsKey("stock")){
            this.stock = (Stock) params.get("stock");
        }

        initRoot();
    }

    /**
     * Default no-argument constructor
     */

    public OperationController(){}

    /**
     *Method that initializes the instances corresponding to the elements in the FXML file.
     */

    @FXML
    private void initialize() {
        operationSelect.setValue(OperationType.LONG);
        operationSelect.setTooltip(new Tooltip("Select and operation"));
        operationSelect.getItems().setAll(OperationType.values());

        calculateCostButton.setOnMouseClicked(
                e -> costLabel.setText(String.valueOf(getOpenPrice()))
        );
        cancelButton.setOnMouseClicked(
                e -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.StockListViewPath
                )
        );

        // TODO Security on the amount field
        operateButton.setOnMouseClicked(
                e -> new DeustockGateway().openOperation(
                        operationSelect.getValue(),
                        stock,
                        MainController.getInstance().getToken(),
                        // Temporaly solves the case of empty textbox
                        Double.parseDouble("0" + amountTextField.getText()))
        );
    }

    /**
     * initRoot methods defines the FXML elements setting the correspondent value to each variable
     */
    private void initRoot(){

        balance = new DeustockGateway().getBalance(username);
        stockNameLabel.setText(stock.getAcronym());
        stockPriceLabel.setText(String.valueOf(stock.getPrice()));
        balanceLabel.setText(String.valueOf(balance));
        costLabel.setText("0");
    }

    /**
     * Method that gets the value of the OpenPrice variable and makes sure it is not null
     *
     * @return returns the value of the OpenPrice in a double
     * returns 0 in case it is null
     */
    public double getOpenPrice(){
        if(operationSelect.getValue() == null){
            return 0;
        }

        return switch (operationSelect.getValue()){
            case LONG, SHORT -> (Double.parseDouble(amountTextField.getText()) * this.stock.getPrice());
        };
    }

}
