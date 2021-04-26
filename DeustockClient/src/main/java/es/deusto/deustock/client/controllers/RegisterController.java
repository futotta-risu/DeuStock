package es.deusto.deustock.client.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.log.DeuLogger;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * Controller para RegisterView
 * <br><strong>Pattern</strong>
 * <ul>
 *      <li>Controller</li>
 * </ul>
 * 
 * @author landersanmillan
 */
public class RegisterController {
	
	@FXML
    private TextField usernameTxt;
	
	@FXML
    private TextField passwordTxt;
	
	@FXML
    private TextField fullNameTxt;
	
	@FXML
    private DatePicker birthDatePicker;
	
	@FXML
    private TextArea aboutMeTxt;
	
	@FXML
    private ChoiceBox<String> countryChoice;
	
	@FXML
	private Button registerBtn;
	
	@FXML
	private Button cancelBtn;
	
	public RegisterController(){};
	 
	@FXML
	private void initialize(){
		List<String> countries = new ArrayList<String>();
		for (CountryEnum country : CountryEnum.values()) {
			countries.add(country.name());
		}
		countryChoice.setValue("Seleciona un pais");
		countryChoice.setTooltip(new Tooltip("Seleciona un pais"));
		countryChoice.setItems(FXCollections.observableArrayList(countries));
		
		birthDatePicker.setValue(java.time.LocalDate.now());

		
		registerBtn.setOnMouseClicked(		
				mouseEvent -> {
					try {
						register();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						 DeuLogger.logger.error("Could not register due to unsupported encoding");
					} catch (NoSuchAlgorithmException e) {
						DeuLogger.logger.error("Could not register, algorithm is not available");
						e.printStackTrace();
					}
				}
		);
		
		cancelBtn.setOnMouseClicked(			
				mouseEvent -> MainController.getInstance().loadAndChangeScene(ViewPaths.LoginViewPath)
		);
	}
	

	private void register() throws UnsupportedEncodingException, NoSuchAlgorithmException {
	    Dialog<String> dialog = new Dialog<>();
	    dialog.setTitle("ERROR");
	    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(type);
		
	    String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		String fullName = fullNameTxt.getText();
		String aboutMe = aboutMeTxt.getText();


		DeustockGateway dg = new DeustockGateway();

		if(username.isBlank() || password.isBlank() || fullName.isBlank()  || aboutMe.isBlank() ) {
			dialog.setContentText("CAMPOS NULOS");
			dialog.showAndWait();
			return;
		}

		if(dg.register(username, password, fullName,  aboutMe, "Spain")) {
			MainController.getInstance().loadAndChangeScene(ViewPaths.LoginViewPath);
		}else {
			dialog.setContentText("REGISTRO INVALIDO");
			dialog.showAndWait();
		}
	}

}
