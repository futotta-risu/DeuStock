package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ResetException;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

/**
 * Controller class that contains functions for the control of the UserDetailView.fxml view
 *
 * @author Erik B. Terres
 */
public class UserDetailController implements DSGenericController{

    private String username = null;
    private User user;
    private MainController mainController;
    private DeustockGateway gateway;

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

    @FXML
    Button reportDownloadButton;

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
    



    /**
     * Method that initializes the controller
     *
     * @see #initRoot()
     */
    @FXML
    public void initialize(){
        initRoot();
    }

    /**
     * Method that sets the parameter username of the class
     *
     * @param params collects all the received objects with their respective key in a HashMap
     *
     * @see #initRoot()
     */
    @Override
    public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username")) {
            this.username = String.valueOf(params.get("username"));
        }

        initRoot();

    }

    /**
     * Method that sets the user by searching with a function of gateway with the username
     */
    private void getUser(){
        this.user = gateway.getUser(this.username);
    }

    /**
     * Method that deletes the User
     */
    public void deleteUser(){
        if(gateway.deleteUser(mainController.getToken())){
            mainController.setUser(null);
            mainController.loadAndChangeScene(ViewPaths.LoginViewPath);

    

        }
    }

    /**
     * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the
     * editProfile, accountDelete, resetWallet and reportDownload buttons
     *
     * @see #getUser()
     */
    private void initRoot(){
        try {
            MainController.getInstance().getScene().getStylesheets().add("/views/button.css");
        }catch (Exception e){}
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

        		mouseEvent ->
        		    mainController.loadAndChangePaneWithParams(
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
                        try {
                            resetAccountWallet();
                        } catch (ResetException e) {
                            e.printStackTrace();
                        }
                    } else {
                        alert.close();
                    }
                }
    	);

        this.reportDownloadButton.setOnMouseClicked(
                MouseEvent -> {
                    Stage s = new Stage();
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    File selectedDirectory = directoryChooser.showDialog(s);
                    File f = gateway.getUserReport(this.username, selectedDirectory.getAbsolutePath());

                    try {
                        Desktop.getDesktop().open(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    	
    }
    /**
     * Method that resets the whole wallet, sets the wallet null
     */
    public void resetAccountWallet() throws ResetException {
    	boolean succesfullyReseted = gateway.resetHoldings(this.username);
    	if(!succesfullyReseted) throw new ResetException("No se ha podido resetear");

    }
}
