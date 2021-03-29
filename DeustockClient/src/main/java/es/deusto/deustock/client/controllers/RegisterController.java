package es.deusto.deustock.client.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.swing.JOptionPane;

import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RegisterController {
	
	@FXML
    private TextField usernameTxt;
	
	@FXML
    private TextField passwordTxt;
	
	@FXML
    private TextField fullNameTxt;
	
	@FXML
    private TextField birthDateTxt;
	
	@FXML
    private TextArea aboutMeTxt;
	
	@FXML
    private ComboBox<String> countryCombo;
	
	@FXML
	private Button registerBtn;
	
	@FXML
	private Button cancelBtn;
	
	public RegisterController(){};
	 
	@FXML
	private void initialize(){
		countryCombo = new ComboBox<String>();
		for (CountryEnum country : CountryEnum.values()) {
			countryCombo.getItems().add(country.toString());
		}
		
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
				mouseEvent -> {
					cancel();
				}
		);
	}
	
	@FXML
	private void register() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		String fullName = fullNameTxt.getText();
		String[] dateText = birthDateTxt.getText().split("/");
		Date birthDate = new Date(Integer.parseInt(dateText[0]),Integer.parseInt(dateText[1]),Integer.parseInt(dateText[2]));
		String aboutMe = aboutMeTxt.getText();
		String country = countryCombo.getValue();
		
		DeustockGateway dg = new DeustockGateway();
		if(!username.equals("") && !password.equals("") && !fullName.equals("") && !birthDate.equals(null) && !aboutMe.equals("") && !country.equals("Seleciona un pais")) {
			if(dg.register(username, password, fullName, birthDate, aboutMe, country)) {
				MainController.getInstance().loadAndChangeScene(ViewPaths.LoginViewPath);
			}else {
				JOptionPane.showInternalConfirmDialog(null, "REGISTRO INVALIDO", "ERROR", 0);
			}
		}else {
			JOptionPane.showInternalConfirmDialog(null, "CAMPOS VACIOS", "ERROR", 0);
		}
	}
	
	@FXML
	private void cancel() {
		MainController.getInstance().loadAndChangeScene(ViewPaths.LoginViewPath);
	}

	

}
