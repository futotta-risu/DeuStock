package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.HashMap;

/**
 * @author Erik B. Terres
 */
public class UserDetailController implements DSGenericController{

    private String username = null;
    private User user;

    @FXML
    Label usernameLabel;

    @FXML
    Label countryLabel;

    @FXML
    TextArea descriptionLabel;

    @FXML
    Button accountDeleteButton;
    
    @FXML
    Button editProfileButton;

    @FXML
    Button resetWalletButton;

    public  UserDetailController(){}

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
        DeustockGateway gateway = new DeustockGateway();
        this.user = gateway.getUser(this.username);
    }

    private void deleteUser(){
        DeustockGateway gateway = new DeustockGateway();

        if(gateway.deleteUser(MainController.getInstance().getToken())){
            MainController.getInstance().setUser(null);
            MainController.getInstance().loadAndChangeScene(
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
        this.countryLabel.setText(user.getCountry());
        this.descriptionLabel.setText(user.getDescription());

        this.accountDeleteButton.setOnMouseClicked( mouseEvent -> deleteUser() );
        this.editProfileButton.setOnMouseClicked(
        		moseEvent ->
                    MainController.getInstance().loadAndChangePaneWithParams(
                            ViewPaths.ChangeUserDetailViewPath,
                            new HashMap<>() {{ put("username", username ); }}
                    )
        );

    	this.resetWalletButton.setOnMouseClicked(
    			mouseEvent -> resetAccountWallet()
    	);
    	
    }
    
    public void resetAccountWallet() {
    	DeustockGateway gateway = new DeustockGateway();
    	boolean succesfullyReseted = gateway.resetHoldings(this.user.getUsername());
    	if(!succesfullyReseted) {
    	    // TODO handle with exception
    		System.out.println("No se ha podido resetear");
    	}
    }
}
