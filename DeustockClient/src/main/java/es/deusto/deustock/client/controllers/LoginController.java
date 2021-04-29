package es.deusto.deustock.client.controllers;




import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar.ButtonData;
import org.mortbay.log.Log;

import java.lang.reflect.Method;

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

	public LoginController(){}

	@FXML
	private void initialize(){
		Dialog<String> dialog = new Dialog<>();
	    dialog.setTitle("ERROR");
	    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(type);

		loginButton.setOnMouseClicked(
				mouseEvent -> {
					User login = login();

					if(login==null) {
						dialog.setContentText("DATOS ERRONEOS");
				        dialog.showAndWait();
						return;
					}

					MainController.getInstance().initGenericStage(login);
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
	private User login(){
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
