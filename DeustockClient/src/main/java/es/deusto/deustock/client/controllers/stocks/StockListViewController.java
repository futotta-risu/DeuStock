package es.deusto.deustock.client.controllers.stocks;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.HashMap;


/**
 * Controller class that contains functions for the control of the StockListView.fxml view
 *
 * @author Erik B. Terres & Aritz Zugazaga
 */
public class StockListViewController {

	private DeustockGateway gateway;
	private MainController mainController;
	
    @FXML 
    TextField searchStockText;
    
    @FXML
    Button searchStockButton;

    @FXML
    Button refreshButton;
    
    @FXML
    VBox stockList;

    HashMap<String, StockInfoLine> stockLines;

    /**
     * Default no-argument constructor
     */
    public StockListViewController(){
    	this.gateway = new DeustockGateway();
		this.mainController = MainController.getInstance();
    }

    public void setDeustockGateway(DeustockGateway gateway){ this.gateway = gateway; }
    public void setMainController(MainController mainController){ this.mainController = mainController; }

    /**
     * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the
     * refresh and search buttons.
     *
     * @see #refreshStocks()
     */
    @FXML
    private void initialize(){
        stockLines = new HashMap<>();

        refreshButton.setOnMouseClicked(mouseEvent -> refreshStocks());
        searchStockButton.setOnMouseClicked(mouseEvent -> searchStock());
        try{
            refreshStocks();
        }catch (Exception e){
            return;
        }

    }

    /**
     * Method that cleans the stock list and searches the queried stock using gateway
     * It creates a list and adds the searched stock
     *
     * @see #emptyStockList()
     */
    private void searchStock(){
        emptyStockList();

        String searchQuery = searchStockText.getText();
        
        stockLines = new HashMap<>();
        Stock stock = gateway.getSearchedStock(searchQuery);

        if(stock != null) {
            StockInfoLine stockLine = new StockInfoLine(stock);
            stockLines.put(stock.getAcronym(), stockLine);


            stockList.getChildren().add(stockLine);
            stockList.getChildren().add(new Separator());
        }else {
            stockList.getChildren().add(new Label(" ** NO SE HA ENCONTRADO NINGUN STOCK CON ESE ACRONYM **"));
        }
    }

    /**
     * Method that cleans the stock list and charges again all the stocks in that list
     *
     * @see #emptyStockList()
     */
    public void refreshStocks(){
        emptyStockList();

        for(Stock stock : gateway.getStockList("big")) {
            if (!stockLines.containsKey(stock.getAcronym())) {
                StockInfoLine stockLine = new StockInfoLine(stock);
                stockLines.put(stock.getAcronym(), stockLine);

                Image image1 = new Image("file:src/main/resources/views/img/notfav.png");
                Image image2 = new Image("file:src/main/resources/views/img/fav.png");
                Button favButton = new Button();

                favButton.setGraphic(new ImageView(image1));
                favButton.setOnAction(e -> favButton.setGraphic(new ImageView(image2)));

                Button detailButton = new Button();
                detailButton.setText("More Info");
                detailButton.setOnAction(event -> mainController.loadAndChangePaneWithParams(
                        ViewPaths.StockDetailViewPath,
                        new HashMap<>() {{
                            put("acronym", stock.getAcronym());
                        }}
                ));

                stockList.getChildren().add(stockLine);
                stockList.getChildren().add(detailButton);
                stockList.getChildren().add(new Separator());
            } else {
                stockLines.get(stock.getAcronym()).refreshStock(stock);

            }
        }
    }

    /**
     * Method that cleans the stock list and leaves it empty
     */
    private void emptyStockList(){
        stockLines.clear();
        stockList.getChildren().removeIf(
                node -> (
                        node instanceof StockInfoLine ||
                                node instanceof Label ||
                                node instanceof Separator ||
                                node instanceof Button
                )
        );
    }


}
