package es.deusto.deustock.client.controllers.stocks;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import es.deusto.deustock.client.controllers.DSGenericController;
import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import yahoofinance.histquotes.HistoricalQuote;


/**
 * @author landersanmillan
 *
 * Controller class that contains functions for the control of the StockDetailView.fxml view
 */
public class StockDetailController implements DSGenericController {
	

    private DeustockGateway gateway;
    private MainController mainController;
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
    Button backButton, buyButton;
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    CategoryAxis xAxis;   
    @FXML
    NumberAxis yAxis;
    
    public StockDetailController() {
		this.gateway = new DeustockGateway();
		this.mainController = MainController.getInstance();
    }
    
    public void setDeustockGateway(DeustockGateway gateway){ this.gateway = gateway; }
    public void setMainController(MainController mainController){ this.mainController = mainController; }
    

    /**
     * Method that sets the parameter acronym of the class
     *
     * @param params collects all the received objects with their respective key in a HashMap
     */
	@Override
	public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("acronym"))
            this.acronym = String.valueOf(params.get("acronym"));
        
        initRoot();		
	}

    /**
     * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the buttons.
     *
     * @see #getStock()
     */
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
        
        mediaLabel.setText("La media del precio de " + stock.getAcronym() + " es de " + (double)Math.round(stock.calcularMediaPrecio()*100)/100 + " €");
        SDLabel.setText("La desviacion estandar del precio de " + stock.getAcronym() + " es de " + (double)Math.round(stock.calcularSD()*100)/100 + " €");
        
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
  
        lineChart.setTitle("Precio de " + stock.getAcronym());
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Precio de " + stock.getAcronym() + " en €");
        lineChart.getData().add(series);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (HistoricalQuote hq : stock.getHistory()) {
        	Date date = hq.getDate().getTime();
        	Number num = hq.getClose().doubleValue();
		    series.getData().add(new XYChart.Data<>(dateFormat.format(date),num));
		}
      

        downloadButton.setId("hoverButton");
        downloadButton.setOnMouseClicked( 
        		MouseEvent -> {
        			Stage s = new Stage();
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    File selectedDirectory = directoryChooser.showDialog(s);
                    File f = gateway.getStockReport(this.acronymLabel.getText(), "DAILY", selectedDirectory.getAbsolutePath());

                    try {
                    	Desktop.getDesktop().open(f);
                    } catch (IOException e) {
                    	e.printStackTrace();
                    }
				}
        );

        backButton.setId("hoverButton");
        backButton.setOnMouseClicked(
                mouseevent -> mainController.loadAndChangePane(
                        ViewPaths.StockListViewPath
                )
        );

        buyButton.setId("hoverButton");
        buyButton.setOnMouseClicked(
                e -> mainController.loadAndChangePaneWithParams(
                        ViewPaths.OperationView,
                        new HashMap<>() {{
                            put("username",mainController.getUser());
                            put("stock", stock);
                        }}
                )
        );
        try{
            MainController.getInstance().getScene().getStylesheets().add("/views/button.css");
        }catch (Exception e){}
    }

    /**
     * Method that sets the stock using the function of the gateway
     */

	private void getStock(){
        stock = gateway.getStock(acronym, "MONTHLY");
        setStock(stock);
    }

    /**
     * Setter method
     * @param stock defines the stock variable from the class with the parameter received in the method, a stock
     */
	private void setStock(Stock stock) {
		this.stock = stock;
	}
    
}
