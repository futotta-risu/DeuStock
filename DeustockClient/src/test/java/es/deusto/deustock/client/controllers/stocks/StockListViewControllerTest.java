package es.deusto.deustock.client.controllers.stocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
public class StockListViewControllerTest extends ApplicationTest {

	private StockListViewController controller;
	
    private TextField searchStockText;
    private Button searchStockButton;
    private Button refreshButton;
    private VBox stockList;
    private HashMap<String, StockInfoLine> stockLines;
    
    private DeustockGateway mockGateway;
    
    @BeforeAll
    public static void setupSpec() throws TimeoutException {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StockListView.fxml"));

        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        searchStockText = controller.searchStockText;
        searchStockButton = controller.searchStockButton;
        refreshButton = controller.refreshButton;
        stockList = controller.stockList;
        stockLines = controller.stockLines;


    }
    
    @BeforeEach
    void setUp(){
        this.mockGateway = mock(DeustockGateway.class);
    }
    
    @Test
    void testSearchStock(FxRobot robot) throws ForbiddenException {
        //Given
    	
    	Stock amz = new Stock();
    	amz.setAcronym("AMZ");
    	
    	when(mockGateway.getSearchedStock(anyString())).thenReturn(amz);
        controller.setDeustockGateway(mockGateway);
        
        //When
        
        searchStockText.setText("AMZ");
        robot.clickOn(searchStockButton);
        
        //Then

        assertEquals(stockList.getChildren().get(1).toString(),  "AMZ");
    }

    @Test
    void testSearchStockNoFound(FxRobot robot) throws ForbiddenException {
        //Given
        Stock amz = new Stock();
        amz.setAcronym("AMZ");

        when(mockGateway.getSearchedStock(anyString())).thenReturn(amz);
        controller.setDeustockGateway(mockGateway);

        //When

        searchStockText.setText("BTC");
        robot.clickOn(searchStockButton);

        //Then

        assertEquals(stockList.getChildren().get(1).toString(),  "** NO SE HA ENCONTRADO NINGUN STOCK CON ESE ACRONYM **");
    }

    @Test
    void testRefreshStocks(FxRobot robot) throws ForbiddenException {
        //Given
        Stock amz = new Stock();
        amz.setAcronym("AMZ");

        when(mockGateway.getStockList("big"));
        controller.setDeustockGateway(mockGateway);

        //When

        robot.clickOn(refreshButton);

        //Then

        assertEquals(stockList.getChildren().get(1).toString(),  "AMZ");
    }
}
