package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.HashMap;

/**
 * @author Erik B. Terres
 */
public class UserDetailController implements DSGenericController{

    private String username = null;
    private User user;
    private MainController mainController;

    @FXML
    Label usernameLabel;

    @FXML
    Label birthdayLabel;

    @FXML
    Label sexLabel;

    @FXML
    Text descriptionLabel;

    @FXML
    Button accountDeleteButton;
    
    @FXML
    Button editProfileButton;

    @FXML
    Button resetWalletButton;

    private DeustockGateway gateway;

    public  UserDetailController(){
        gateway = new DeustockGateway();
        mainController = MainController.getInstance();
    }

    public void setDeustockGateway(DeustockGateway deustockGateway){
        this.gateway = deustockGateway;
    }

    public void setMainController(MainController controller){
        this.mainController = controller;
    }

    @FXML
    public void initialize(){
        initRoot();
    }

    @Override
    public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username")) {
            this.username = String.valueOf(params.get("username"));
        }

        initRoot();

    }

    private void getUser(){
        this.user = gateway.getUser(this.username);
    }

    public void deleteUser(){
        if(gateway.deleteUser(mainController.getToken())){
            mainController.setUser(null);
            mainController.loadAndChangeScene(
                    ViewPaths.LoginViewPath
            );
        }
    }

    private void initRoot(){
        if(this.username==null){
            return;
        }
        if(this.user == null || !this.user.getUsername().equals(this.username)) {
            getUser();
        }
        // Error on retrieving user
        if(this.user==null){
            return;
        }

        this.usernameLabel.setText(user.getUsername());
        //this.sexLabel.setText(String.valueOf(user.isSex()));
        this.descriptionLabel.setText(user.getDescription());

        this.accountDeleteButton.setOnMouseClicked( mouseEvent -> deleteUser() );
        this.editProfileButton.setOnMouseClicked(

        		mouseEvent ->
        		    mainController.loadAndChangePaneWithParams(

                            ViewPaths.ChangeUserDetailViewPath,
                            new HashMap<>() {{ put("username", username ); }}
                    )
        );

    	this.resetWalletButton.setOnMouseClicked(
                mouseEvent -> resetAccountWallet()
    	);
    	
    }
    
    public void resetAccountWallet() {
    	boolean succesfullyReseted = gateway.resetHoldings(this.user.getUsername());
    	if(!succesfullyReseted) {
    	    // TODO handle with exception
    		System.out.println("No se ha podido resetear");
    	}
    }
}
