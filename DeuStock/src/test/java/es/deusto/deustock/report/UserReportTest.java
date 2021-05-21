package es.deusto.deustock.report;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("report")
public class UserReportTest {

    @Test
    @DisplayName("Test generating a User report.")
    void testGenerateUserReportWithoutStockHistory() throws IOException, WalletException {
        // Given
        User user = new User("1TestUsername1", "TestPass");

        WalletService mockWalletService = mock(WalletService.class);
        when(mockWalletService.getBalance(anyString())).thenReturn(9999.0);


        UserReport report = new UserReport(user);
        report.setWalletService(mockWalletService);

        // When
        File file = report.generate();

        // Then
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Test generating a User report.")
    void testGenerateUserReportWithStockHistory() throws IOException, WalletException {
        User user = new User("2TestUsername", "TestPass");

        Wallet wallet = new Wallet();
        wallet.setMoney(9999);

        StockHistoryDTO sh = new StockHistoryDTO().setSymbol("BB").setOpenPrice(9.99).setAmount(3.0).setOperation(OperationType.SHORT);

        List<StockHistoryDTO> listSh = new ArrayList<StockHistoryDTO>();
        listSh.add(sh);

        WalletService mockWalletService = mock(WalletService.class);
        when(mockWalletService.getBalance(anyString())).thenReturn(9999.0);
        when(mockWalletService.getHoldings(anyString())).thenReturn(listSh);

        UserReport report = new UserReport(user);
        report.setWalletService(mockWalletService);

        // When
        File file = report.generate();

        // Then
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Test generating a User report.")
    void testGenerateUserReportWithBigStockHistory() throws IOException, WalletException {
        User user = new User("3TestUsername", "TestPass");

        Wallet wallet = new Wallet();
        wallet.setMoney(9999);

        List<StockHistoryDTO> listSh = new ArrayList<StockHistoryDTO>();
        for(int i = 0; i<=30;i++){
            StockHistoryDTO sh = new StockHistoryDTO().setSymbol("TSLA").setOpenPrice(9.99).setAmount(1.0).setOperation(OperationType.SHORT);
            listSh.add(sh);
        }

        WalletService mockWalletService = mock(WalletService.class);
        when(mockWalletService.getBalance(anyString())).thenReturn(9999.0);
        when(mockWalletService.getHoldings(anyString())).thenReturn(listSh);

        UserReport report = new UserReport(user);
        report.setWalletService(mockWalletService);

        // When
        File file = report.generate();
        PDDocument document= PDDocument.load(file);
        int pages = document.getNumberOfPages();

        // Then
        assertEquals(4, pages);
    }

}
