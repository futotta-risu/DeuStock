package es.deusto.deustock.report;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.resources.investment.wallet.BalanceResource;
import es.deusto.deustock.resources.investment.wallet.HoldingsListResources;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class UserReport extends Report {

    private final User user;
    private final DecimalFormat decimalFormat =  new DecimalFormat("#.##");
    private static final Logger logger = LoggerFactory.getLogger(UserReport.class);

    public UserReport(User user){
        super();
        this.user = user;
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
    protected void setContent() throws IOException, SQLException {
        System.out.println("0.0.0");
        PDPage page = createPage();
        System.out.println("0.0.1");
        addActualBalance();
        System.out.println("0.0.2");
        addHoldingsList();
        System.out.println("0.0.3");
        savePage(page);
        System.out.println("0.0.4");

    }

    private void addActualBalance() throws SQLException, IOException {
        BalanceResource balanceResource = new BalanceResource();
        Response response = balanceResource.getBalance(user.getUsername());

        String balance = response.getEntity().toString();
        addSimpleTextLine("Tu balance actual es de " + balance + " €");

    }

    private void addHoldingsList() throws IOException {
        System.out.println("0.0.0.0");
        HoldingsListResources holdingsListResource = new HoldingsListResources();
        System.out.println("0.0.0.1");
        Response response = holdingsListResource.getHoldings(user.getUsername());
        System.out.println("0.0.0.2");

        List<StockHistoryDTO> holdingsList = response.readEntity(new GenericType<>(){});
        System.out.println("0.0.0.3");

        System.out.println("Tienes un total de " + holdingsList.size());
        addSimpleTextLine("Tienes un total de " + holdingsList.size() + " inversiones");

        if(!holdingsList.isEmpty()){
            System.out.println("0.0.0.41");
            for(StockHistoryDTO sh: holdingsList){
                addSimpleTextLine(sh.getSymbol() + "x" + sh.getAmount() + " / Precio Compra = " + sh.getOpenPrice() + " / Precio Actual = "+ sh.getActualPrice());
            }
            System.out.println("0.0.0.51");
        }else{
            System.out.println("Ninguno");
            addSimpleTextLine("No tienes ningun stock en posesion");
            System.out.println("0.0.0.42");
        }


    }

}
