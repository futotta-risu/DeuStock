package es.deusto.deustock.client.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.deusto.deustock.client.gateways.DeustockGateway;
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
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				}
		);
		
		cancelBtn.setOnMouseClicked(			
				mouseEvent -> MainController.getInstance().loadAndChangeScene(ViewPaths.LoginViewPath)
		);
	}

	private void register() throws UnsupportedEncodingException, NoSuchAlgorithmException {
	    Dialog<String> dialog = new Dialog<String>();
	    dialog.setTitle("ERROR");
	    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(type);
		
	    String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		String fullName = fullNameTxt.getText();
		LocalDate date = birthDatePicker.getValue();
        Date birthDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String aboutMe = aboutMeTxt.getText();
		String country = countryChoice.getValue();
		
		DeustockGateway dg = new DeustockGateway();
		if(!username.equals("") && !password.equals("") && !fullName.equals("") && !birthDate.equals(new Date()) && !aboutMe.equals("") && !country.equals("Seleciona un pais")) {
			if(dg.register(username, password, fullName, birthDate, aboutMe, country)) {
				MainController.getInstance().loadAndChangeScene(ViewPaths.LoginViewPath);
			}else {
			    dialog.setContentText("REGISTRO INVALIDO");
		        dialog.showAndWait();
			}
		}else {
		    dialog.setContentText("CAMPOS NULOS");
	        dialog.showAndWait();
		}
	}

}
