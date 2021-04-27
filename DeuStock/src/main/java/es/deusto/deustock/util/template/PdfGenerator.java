package es.deusto.deustock.util.template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.Extractor;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;




public class PdfGenerator {
	
	private static PdfGenerator INSTANCE;
	
	public static PdfGenerator getInstance() {
		if (INSTANCE == null) 
			INSTANCE = new PdfGenerator();
		return INSTANCE;
	}
	
	public byte[] createPdfStockReport(DeuStock deustock) throws IOException, InterruptedException {
	    try (PDDocument document = new PDDocument()) {
	    	
	    	PDPage page1 = new PDPage(PDRectangle.A4);
	       	        
	        // TWITTER ANALISIS
		    Extractor ext = new Extractor(SocialNetworkGatewayEnum.Twitter);
		    String sentiment = String.valueOf(ext.getSentimentTendency(new SocialNetworkQueryData(deustock.getAcronym())));
	        	
		    // ADD TEXT & IMAGE TO PDF

	        document.addPage(page1);
	        
	    	PDPage page2 = new PDPage(PDRectangle.A3);
	    	page2.setRotation(90);
	    	float pageWidth = page2.getMediaBox().getWidth();
	        float pageHeight = page2.getMediaBox().getHeight();
	        System.out.println("Height" + pageHeight + "Width" + pageWidth);

	        
	        // STOCK ANALISIS


	        
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        document.save(byteArrayOutputStream);
	        
	        return byteArrayOutputStream.toByteArray();
	    }
	 
	  }
	
	
}
