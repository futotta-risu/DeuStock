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
        if(gateway.deleteUser(this.username, user.getPassword())){
            MainController.getInstance().setUser(null);
            MainController.getInstance().loadAndChangeScene(
                    ViewPaths.LoginViewPath
            );
        }
    }

    private void initRoot(){
        System.out.println("LLLLLL - Init t-1");
        if(this.username==null){
            System.out.println("Init t-11");
            return;
        }
        System.out.println("Init t-12");
        if(this.user == null || !this.user.getUsername().equals(this.username)) {
            System.out.println("Init t-13");
            getUser();
        }
        System.out.println("Init t-14");
        // Error on retrieving user
        if(this.user==null){
            System.out.println("Init t-15");
            return;
        }
        System.out.println("Init t-16");

        this.usernameLabel.setText(user.getUsername());
        //this.sexLabel.setText(String.valueOf(user.isSex()));
        this.descriptionLabel.setText(user.getDescription());
        System.out.println("Init t-17");
        this.accountDeleteButton.setOnMouseClicked(
                mouseEvent -> deleteUser()
        );
        System.out.println("Init t-18");
        this.editProfileButton.setOnMouseClicked(
        		moseEvent -> {
        		    System.out.println("Intentando cambiar");
                    MainController.getInstance().loadAndChangePaneWithParams(
                            ViewPaths.ChangeUserDetailViewPath,
                            new HashMap<>() {{ put("username", username ); }}
                    );
                }

        );
        System.out.println("Init t-19");
    	this.resetWalletButton.setOnMouseClicked(
    			mouseEvent -> resetAccountWallet()
    	);
    	
    }
    
    public void resetAccountWallet() {
    	DeustockGateway gateway = new DeustockGateway();
    	boolean succesfullyReseted = gateway.resetHoldings(this.user.getUsername());
    	if(!succesfullyReseted) {
    		System.out.println("No se ha podido resetear");
    	}
    }
}
