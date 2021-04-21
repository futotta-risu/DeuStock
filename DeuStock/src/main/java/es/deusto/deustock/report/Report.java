package es.deusto.deustock.report;

import static es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum.Twitter;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.TIMES_ROMAN;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import es.deusto.deustock.util.math.Statistics;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.knowm.xchart.BitmapEncoder;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.features.SentimentExtractor;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.dataminer.visualization.TimeChart;

import yahoofinance.histquotes.HistoricalQuote;


public abstract class Report {

	PDDocument document;
	protected PDPageContentStream contentStream;

	private int actPageLine;

	private void initReport(){
		this.document = new PDDocument();
	}

	protected void setTemplateData() throws IOException {
		PDPage page = createPage();

		contentStream.setFont(TIMES_ROMAN, 14);

		// TODO Change location of generic text
		addTextAtOfssets(25, 730,
				"Este reporte se ha generado mediante DeuStock," +
						" la informacion se ha obtenido de Yahoo Finance."
		);

	}

	protected void setFrontPage() throws IOException {
		PDPage page = createPage();

		this.contentStream.setFont(TIMES_ROMAN, 70);
		addTextAtOfssets(225, 775, getTitle());
		savePage(page);
	}

	protected abstract void setMetadata();
	protected abstract String getTitle();
	protected abstract void setContent() throws IOException;

	public File generate() throws IOException{
		initReport();

		setMetadata();
		setFrontPage();
		setTemplateData();
		setContent();

		return save();
	};

	private File save() throws IOException {
		String filePath = "media/reports/" + Calendar.getInstance().getTimeInMillis() + ".pdf";

		this.document.save(filePath);

		return new File(filePath);
	}


	protected PDPage createPage() throws IOException {
		PDPage page = new PDPage(PDRectangle.A4);
		this.contentStream = new PDPageContentStream(document, page);

		this.actPageLine = 0;

		return page;
	}

	protected void savePage(PDPage page) throws IOException {
		this.contentStream.close();
		this.document.addPage(page);
	}


	protected void addTextAtOfssets( int x, int y, String text) throws IOException {
		this.contentStream.beginText();

		this.contentStream.newLineAtOffset(x, y);
		this.contentStream.showText(text);
		this.contentStream.endText();
	}

	protected void addSimpleTextLine(String text) throws IOException {
		this.contentStream.setFont(TIMES_ROMAN, 12);
		this.contentStream.beginText();
		this.contentStream.newLineAtOffset(25, 730 - 25 * this.actPageLine++);
		this.contentStream.showText(text);
		this.contentStream.endText();
	}
	

	
	
}
