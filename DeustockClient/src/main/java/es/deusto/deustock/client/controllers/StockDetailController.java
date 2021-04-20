package es.deusto.deustock.client.controllers;

import java.util.HashMap;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.stocks.model.StockModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StockDetailController implements DSGenericController{
	

    private String acronym = null;
    private Stock stock = null;
    
    @FXML
    Label acronymLabel;
    
    @FXML
    TableView stockTable;
    
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
        
        this.acronymLabel.setText("Informacion sobre " + stock.getAcronym());
        
       
        TableColumn<StockModel, String> priceCol = new TableColumn("Precio [€]");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        TableColumn<StockModel, String> mediaCol = new TableColumn("Media [€]");
        mediaCol.setCellValueFactory(new PropertyValueFactory<>("media"));
        
        TableColumn<StockModel, String> standardDesvCol = new TableColumn("Desviacion estandar [€]");
        standardDesvCol.setCellValueFactory(new PropertyValueFactory<>("desviacionEstandar"));
        
        stockTable.getItems().add( new StockModel(stock.getPrice().doubleValue() + " €", stock.calcularMediaPrecio()+" €", stock.calcularSD() + " €"));
        stockTable.getColumns().addAll(priceCol, mediaCol, standardDesvCol);

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
