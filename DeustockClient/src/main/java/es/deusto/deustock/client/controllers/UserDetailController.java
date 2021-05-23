package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Optional;

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
        MainController.getInstance().getScene().getStylesheets().add("/views/button.css");

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

        this.accountDeleteButton.setOnMouseClicked(
                mouseEvent -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("¿Estas seguro...?");
                    alert.setHeaderText("Se eliminara tu cuenta y todos los datos relacionados con la misma");
                    alert.setContentText("¿Estas seguro de que quieres eliminar tu cuenta?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (((Optional<?>) result).get() == ButtonType.OK){
                        deleteUser();
                    } else {
                        alert.close();
                    }
                }
        );

        this.editProfileButton.setOnMouseClicked(
        		moseEvent ->
                    MainController.getInstance().loadAndChangePaneWithParams(
                            ViewPaths.ChangeUserDetailViewPath,
                            new HashMap<>() {{ put("username", username ); }}
                    )
        );

    	this.resetWalletButton.setOnMouseClicked(
    			mouseEvent -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("¿Estas seguro...?");
                    alert.setHeaderText("Perderas todas tus inversiones y los historicos relacionados a tu cuenta, " +
                                        "volveras a empezar de nuevo tu simulacion con 5000€");
                    alert.setContentText("¿Estas seguro de que quieres reiniciar tu cartera?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (((Optional<?>) result).get() == ButtonType.OK){
                        resetAccountWallet();
                    } else {
                        alert.close();
                    }
                }
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
