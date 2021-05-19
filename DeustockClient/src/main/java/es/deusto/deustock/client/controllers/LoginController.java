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
 * Controller para LoginView 
 * <br><strong>Pattern</strong>
 * <ul>
 *      <li>Controller</li>
 * </ul>
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

	public LoginController(){}

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
