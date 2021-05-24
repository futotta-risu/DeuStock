package es.deusto.deustock.client.controllers;


import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
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
	private VBox p1;

	@FXML
	private HBox p2;

	@FXML
    private TextField usernameTxt;
	
	@FXML
    private TextField passwordTxt;

	@FXML
	private Button loginButton;
	
	@FXML
	private Button registerBtn;

	@FXML
	private Button pruebaButton;

	/**
	 * Default no-argument constructor
	 */
	public LoginController(){}

	/**
	 * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the
	 * login and register button.
	 */
	@FXML
	private void initialize(){
		Dialog<String> dialog = new Dialog<>();
	    dialog.setTitle("ERROR");
	    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(type);

		loginButton.setOnMouseClicked(
				mouseEvent -> {
					String token;
					try{
						token = login();
					}catch (ForbiddenException e){
						dialog.setContentText("DATOS ERRONEOS");
						dialog.showAndWait();
						return;
					}

					MainController.getInstance().initGenericStage(usernameTxt.getText(), token);
					MainController.getInstance().loadAndChangePane(
							ViewPaths.HomeViewPath
					);
				}
		);
		
		registerBtn.setOnMouseClicked(
				mouseEvent -> MainController.getInstance().loadAndChangeScene(
							ViewPaths.RegisterViewPath
					)
			);
	}

	/**
	 * Login method, uses the text added by the user in the view and by a gateway function checks if the login is correct
	 * @return if the login is correct, the function returns a token in a String
	 * in case of incorrect login, the function returns a dialog with a warning
	 * @throws ForbiddenException in case the server forbids the access to the server
	 */
	@FXML
	private String login() throws ForbiddenException {
		Dialog<String> dialog = new Dialog<>();
	    dialog.setTitle("ERROR");
	    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(type);
	    
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();

		DeustockGateway dg = new DeustockGateway();
		if(username.isEmpty() || password.isEmpty()) {
			dialog.setContentText("CAMPOS NULOS");
	        dialog.showAndWait();
		}
			
		return dg.login(username, password);
	}


}
