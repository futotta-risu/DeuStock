package es.deusto.deustock.client.controllers.stocks;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;


/**
 * Controller class that contains functions for the control of the StockListView.fxml view
 *
 * @author Erik B. Terres
 */
public class StockListViewController {


    @FXML 
    private TextField searchStockText;
    
    @FXML
    private Button searchStockButton;

    @FXML
    private Button refreshButton;
    
    @FXML
    private VBox stockList;

    private HashMap<String, StockInfoLine> stockLines;


    /**
     * Default no-argument constructor
     */
    public StockListViewController(){}

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

        refreshStocks();
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
        
        DeustockGateway gateway = new DeustockGateway();
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
        DeustockGateway gateway = new DeustockGateway();

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
                detailButton.setOnAction(event -> MainController.getInstance().loadAndChangePaneWithParams(
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
