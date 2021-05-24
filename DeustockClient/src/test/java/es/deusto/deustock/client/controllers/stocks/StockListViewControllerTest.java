package es.deusto.deustock.client.controllers.stocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
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
import net.jcip.annotations.NotThreadSafe;

import org.testfx.framework.junit5.ApplicationTest;

@NotThreadSafe
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
    private MainController mockMainController;
    
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

        searchStockText = controller.searchStockText;
        searchStockButton = controller.searchStockButton;
        refreshButton = controller.refreshButton;
        stockList = controller.stockList;
        stockLines = controller.stockLines;
        
        stage.setScene(scene);
        stage.show();
        
    }
    
    @BeforeEach
    void setUp(){
        this.mockGateway = mock(DeustockGateway.class);
        this.mockMainController = mock(MainController.class);
    }
    
    @Test
    void testSearchStock(FxRobot robot) throws ForbiddenException {
        //Given
    	
    	Stock test = new Stock();
    	test.setAcronym("Test");
    	
    	when(mockGateway.getSearchedStock(anyString())).thenReturn(test);
        controller.setDeustockGateway(mockGateway);
        
        //When
        
        searchStockText.setText("Test");
        clickOn(searchStockButton);
        
        //Then

        assertEquals(stockList.getChildren().size(),  2);
    }

    @Test
    void testSearchStockNoFound(FxRobot robot) throws ForbiddenException {
        //Given
        when(mockGateway.getSearchedStock(anyString())).thenReturn(null);
        controller.setDeustockGateway(mockGateway);

        //When

        searchStockText.setText("gshdgasdu");
        clickOn(searchStockButton);

        //Then

        assertEquals(stockList.getChildren().size(),  1);
    }

    @Test
    void testRefreshStocks(FxRobot robot) throws ForbiddenException {
        //Given
        Stock stock = new Stock();
        stock.setAcronym("Test");
        
        List<Stock> listaStock = new ArrayList<Stock>();
        listaStock.add(stock);

        when(mockGateway.getStockList("big")).thenReturn(listaStock);
        doNothing().when(mockMainController).loadAndChangePaneWithParams(anyString(), any());
        controller.setDeustockGateway(mockGateway);
        controller.setMainController(mockMainController);

        // When & Then
        assertDoesNotThrow( () -> Platform.runLater(() -> clickOn(refreshButton)) );
    }
}
