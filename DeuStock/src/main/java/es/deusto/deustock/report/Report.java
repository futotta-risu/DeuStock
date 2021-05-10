package es.deusto.deustock.report;

import static org.apache.pdfbox.pdmodel.font.PDType1Font.TIMES_ROMAN;

import java.io.File;
import java.io.IOException;

import java.util.Calendar;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;


/**
 * Report template abstract class for PDF report generation.
 *
 * @author Erik B. Terres
 * @author Lander San Millan
 */
public abstract class Report {

	PDDocument document;
	protected PDPageContentStream contentStream;

	private int actPageLine;


	protected Report(){}

	/**
	 * Initializes the PDDocument
	 */
	private void initReport(){
		this.document = new PDDocument();
	}


	/**
	 * Appends a page to put the generic report data.
	 */
	protected void setTemplateData() throws IOException {
		PDPage page = createPage();

		contentStream.setFont(TIMES_ROMAN, 14);

		// TODO Change location of generic text
		addTextAtOfssets(25, 730,
				"Este reporte se ha generado mediante DeuStock," +
						" la informacion se ha obtenido de Yahoo Finance."
		);

		savePage(page);
	}

	/**
	 * Appends the Front page.
	 *
	 * @see Report#getTitle()
	 */
  protected void setFrontPage() throws IOException {
		PDPage page = createPage();

		this.contentStream.setFont(TIMES_ROMAN, 70);
		addTextAtOfssets(225, 775, getTitle());
		savePage(page);
	}


	/**
	 * Sets the metadata of the file.
	 */
	protected abstract void setMetadata();

	/**
	 * Returns the title of the file.
	 */
	protected abstract String getTitle();

	/**
	 * Function to set the content of the report.
	 */
	protected abstract void setContent() throws IOException;

	/**
	 * Generates the PDF based on the template fucntions, saves it and returns the File.
	 *
	 * @return File linked to the saved report.
	 */
	public File generate() throws IOException{
		initReport();

		setMetadata();
		setFrontPage();
		setTemplateData();
		setContent();

		return save();
	}

	/**
	 * Saves the report to a file and returns a File linking to the saved file.
	 *
	 * @return File linked to the saved report.
	 */
  private File save() throws IOException {
		String filePath = "media/reports/" + Calendar.getInstance().getTimeInMillis() + ".pdf";

		this.document.save(filePath);

		return new File(filePath);
	}


	/**
	 * Creates a new PDPage and sets the new contentStream
	 *
	 * @return New PDPage
	 */
	protected PDPage createPage() throws IOException {
		var page = new PDPage(PDRectangle.A4);
		this.contentStream = new PDPageContentStream(document, page);

		this.actPageLine = 0;

		return page;
	}

	/**
	 * Saves the page to the document and closes the contentStream.
	 *
	 * @param page Page to save
	 */
	protected void savePage(PDPage page) throws IOException {
		this.contentStream.close();
		this.document.addPage(page);
	}



	/**
	 * Adds text on (x,y) coordinates.
	 */
	protected void addTextAtOfssets( int x, int y, String text) throws IOException {
		this.contentStream.beginText();

		this.contentStream.newLineAtOffset(x, y);
		this.contentStream.showText(text);
		this.contentStream.endText();
	}


	/**
	 * Adds text line dynamically.
	 */
	protected void addSimpleTextLine(String text) throws IOException {
		this.contentStream.setFont(TIMES_ROMAN, 12);
		this.contentStream.beginText();
		this.contentStream.newLineAtOffset(25,  730 - 25.0f * this.actPageLine++);
		this.contentStream.showText(text);
		this.contentStream.endText();
	}

}
