package es.deusto.deustock.report;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class UserReport extends Report {

    private final User user;
    private WalletService walletService;
    private static final Logger logger = LoggerFactory.getLogger(UserReport.class);

    public UserReport(User user){
        super();
        this.user = user;
        this.walletService = new WalletService();
    }
    @Override
    protected void setMetadata() {
        // TODO Add Metadata
    }

    @Override
    protected String getTitle() {
        return user.getUsername();
    }

    @Override
    protected void setContent() throws IOException {
        PDPage page = createPage();
        try {
            addActualBalance();
            page = addHoldingsList(page);
        } catch (WalletException e) {
            e.printStackTrace();
        }
        savePage(page);
    }

    private void addActualBalance() throws IOException, WalletException {
        addSimpleTextLine("Tu balance actual es de " + walletService.getBalance(user.getUsername()) + " €");
    }

    private PDPage addHoldingsList(PDPage page) throws IOException, WalletException {

        PDPage actualPage = page;
        List<StockHistoryDTO> holdingsList = walletService.getHoldings(user.getUsername());

        addSimpleTextLine("Tienes un total de " + holdingsList.size() + " inversiones");
        int counter = 0;

        if(!holdingsList.isEmpty()){
            for(StockHistoryDTO sh: holdingsList){
                counter++;
                if(counter % 25 == 0){
                    savePage(actualPage);
                    PDPage newPage = createPage();
                    actualPage = newPage;
                }
                double roi = (sh.getActualPrice() - sh.getOpenPrice()) * sh.getAmount();
                addSimpleTextLine(sh.getSymbol() + "  x " + sh.getAmount() + "  /  Precio Compra = " + sh.getOpenPrice() + "  /  Precio Actual = "+ sh.getActualPrice()
                                   + "  /  ROI = " + roi);
            }
        }else{
            addSimpleTextLine("No tienes ningun stock en posesion");
        }

        return actualPage;
    }

}
