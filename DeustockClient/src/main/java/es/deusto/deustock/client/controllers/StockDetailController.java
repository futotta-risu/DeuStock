package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
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
                    byte[] bytes = null;
					try {
						bytes = gateway.getStockReport(this.acronymLabel.getText(), "DAILY");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
			        // Write to file
			        File f = new File(selectedDirectory.getAbsolutePath() + "/" + this.acronym + " " + Calendar.getInstance().getTime().toString() + ".pdf");
			        FileOutputStream fos;
					try {
						fos = new FileOutputStream(f);
				        fos.write(bytes);
				        fos.close();
				        // Open the file
				        Desktop.getDesktop().open(new File(selectedDirectory.getAbsolutePath() + "/" + this.acronym + " " + Calendar.getInstance().getTime().toString() + ".pdf"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
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
