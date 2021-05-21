package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ResetException;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author Erik B. Terres
 */
public class UserDetailController implements DSGenericController{

    private static DeustockGateway gateway = new DeustockGateway();
    private String username = null;
    private User user;
    private MainController mainController;
    private DeustockGateway gateway;

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
            mainController.loadAndChangeScene(ViewPaths.LoginViewPath);
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
                mouseEvent -> {
                    try {
                        resetAccountWallet();
                    } catch (ResetException re) {
                        re.printStackTrace();
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
    
    public void resetAccountWallet() throws ResetException {
    	boolean succesfullyReseted = gateway.resetHoldings(this.username);
    	if(!succesfullyReseted) throw new ResetException("No se ha podido resetear");
    }
}
