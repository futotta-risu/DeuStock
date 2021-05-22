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
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.simulation.investment.operations.OperationType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
class OperationControllerTest extends ApplicationTest {
	
	private OperationController controller;
	
	private Label stockNameLabel, stockPriceLabel, balanceLabel, costLabel;
	private TextField amountTextField;
	private ChoiceBox<OperationType> operationSelect;
	private Button cancelButton, operateButton, calculateCostButton;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/stocks/OperationView.fxml"));

        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        stockNameLabel = controller.stockNameLabel;
        stockPriceLabel = controller.stockPriceLabel;
        balanceLabel = controller.balanceLabel;
        costLabel = controller.costLabel;
        
        amountTextField = controller.amountTextField;
        
        operationSelect = controller.operationSelect;
        
        cancelButton = controller.cancelButton;
        operateButton = controller.operateButton;
        calculateCostButton = controller.calculateCostButton;

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp(){
        this.mockGateway = mock(DeustockGateway.class);
        this.mockMainController = mock(MainController.class);
    }
    
    @Test
    void testOpenOnStock() {
        // Given
        Stock stock = new Stock();
        stock.setPrice(2);
        stock.setFullName("Test");
        HashMap<String, Object> params = new HashMap<>();
        params.put("acronym", "Test");

        when(mockGateway.getStock(anyString(), anyString())).thenReturn(stock);
        controller.setDeustockGateway(mockGateway);

        // When
        Platform.runLater( () -> {
            controller.setParams(params);
            Assertions.assertThat(controller.stockNameLabel).hasText("Test");
            Assertions.assertThat(controller.stockPriceLabel).hasText("2");
        });
        // Then


    }

}
