package es.deusto.deustock.client.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;

import com.sun.glass.events.MouseEvent;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import es.deusto.deustock.client.visual.stocks.model.StockModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


/**
 * @author landersanmillan
 */
public class StockDetailController implements DSGenericController{
	

    private static DeustockGateway gateway = new DeustockGateway();
    private String acronym = null;
    private Stock stock = null;
    
    @FXML
    Label acronymLabel;   
    @FXML
    Label priceLabel;
    @FXML
    Label mediaLabel;   
    @FXML
    Label SDLabel;
    @FXML
    Button downloadButton;
    @FXML
    Button backButton;
    
	@Override
	public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("acronym"))
            this.acronym = String.valueOf(params.get("acronym"));
        
        initRoot();		
	}
	
	private void initRoot(){
        if(this.acronym==null) return;        
        
        getStock();
        
        // Error on retrieving user
        if(this.stock==null) return;
        
        this.acronymLabel.setText(stock.getAcronym());
              
    	priceLabel.setText(stock.getPrice()+" €");
    	priceLabel.setTextFill(Color.RED);
    	priceLabel.setTextAlignment(TextAlignment.RIGHT);
        if(Double.parseDouble(stock.getPrice()+"") > stock.calcularMediaPrecio()) {
        	priceLabel.setTextFill(Color.GREEN);
        }
        
        mediaLabel.setText("La media del precio de " + stock.getAcronym() + " es de " + stock.calcularMediaPrecio() + " €");
        SDLabel.setText("La desviacion estandar del precio de " + stock.getAcronym() + " es de " + stock.calcularSD() + " €");
        
        downloadButton.setOnMouseClicked( 
        		MouseEvent -> {
        			Stage s = new Stage();
        	        DirectoryChooser directoryChooser = new DirectoryChooser();
                    File selectedDirectory = directoryChooser.showDialog(s);
					File f = gateway.getReport(this.acronymLabel.getText(), "DAILY", selectedDirectory.getAbsolutePath());
					
				    try {
						Desktop.getDesktop().open(f);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
        );
        
        backButton.setOnMouseClicked(
                mouseevent -> MainController.getInstance().loadAndChangeScene(
                        ViewPaths.StockListViewPath
                )
        );
    }
	
	private void getStock(){
        DeustockGateway gateway = new DeustockGateway();
        stock = gateway.getStock(acronym, "DAILY");
        setStock(stock);
    }
	private void setStock(Stock stock) {
		this.stock = stock;
	}
	
	


}
