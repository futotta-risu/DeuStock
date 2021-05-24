package es.deusto.deustock.client.controllers;




import es.deusto.deustock.client.controllers.exceptions.EmptyFieldsException;
import es.deusto.deustock.client.data.User;

import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;

import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import javafx.scene.layout.*;
import javafx.scene.control.ButtonBar.ButtonData;


/**
 * Controller class that contains functions for the control of the LoginView.fxml view
 *
 * @author landersanmillan
 */
public class LoginController {


	@FXML
    public TextField usernameTxt;
	
	@FXML
	public TextField passwordTxt;

	@FXML
	public Button loginButton;
	
	@FXML
	public Button registerBtn;

	@FXML
	private Button pruebaButton;

	@FXML
	public Label loginErrorLabel;

	private MainController mainController;

	private DeustockGateway gateway;

	public LoginController(){
		mainController = MainController.getInstance();
		gateway = new DeustockGateway();
	}

	public void setMainController(MainController mainController){
		this.mainController = mainController;
	}

	public void setGateway(DeustockGateway gateway){
		this.gateway = gateway;
	}


	/**
	 * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the
	 * login and register button.
	 */
	@FXML
	private void initialize(){
		loginButton.setOnMouseClicked( mouseEvent -> login() );
		
		registerBtn.setOnMouseClicked(
				mouseEvent -> mainController.loadAndChangeScene(ViewPaths.RegisterViewPath)
		);
	}

	/**
	 * Login method, uses the text added by the user in the view and by a gateway function checks if the login is correct
	 * @return if the login is correct, the function returns a token in a String
	 * in case of incorrect login, the function returns a dialog with a warning
	 * @throws ForbiddenException in case the server forbids the access to the server
	 */
	@FXML
	private void login() {
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();

		if(username.isEmpty() || password.isEmpty()) {
			loginErrorLabel.setText("Campos vacios");
			return;
		}
		String token;

		try{
			token = gateway.login(username, password);
		}catch (ForbiddenException e){
			loginErrorLabel.setText("Datos Incorrectos");
			return;
		}

		mainController.initGenericStage(usernameTxt.getText(), token);
		mainController.loadAndChangePane(ViewPaths.HomeViewPath);
	}


}
