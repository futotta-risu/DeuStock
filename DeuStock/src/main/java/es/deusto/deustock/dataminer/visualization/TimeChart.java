package es.deusto.deustock.dataminer.visualization;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import es.deusto.deustock.data.DeuStock;
import yahoofinance.histquotes.HistoricalQuote;

/**
 * @author landersanmi
 */
public class TimeChart {
	
	private static TimeChart instance;
	
	public static TimeChart getInstance() {
		if (instance == null) {
			instance = new TimeChart();
		}
		return instance;
	}

	public XYChart getTimeChart(DeuStock deustock, int height, int width) {
		

	    XYChart chart = new XYChartBuilder()
				.width(width).height(height)
				.title("Precio de " + deustock.getAcronym())
				.build();


		XYStyler styler = chart.getStyler();


	    styler.setPlotGridLinesColor(Color.WHITE);
		styler.setChartBackgroundColor(Color.WHITE);
	    styler.setChartTitleBoxBackgroundColor(Color.WHITE);

		styler.setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);


	    styler.setPlotGridLinesVisible(false);
		styler.setPlotGridLinesColor(Color.LIGHT_GRAY);
		styler.setPlotGridLinesStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{10.0f}, 0));
		styler.setPlotGridHorizontalLinesVisible(true);

		styler.setChartTitleBoxVisible(false);
		styler.setPlotBorderVisible(false);
		styler.setYAxisTitleVisible(false);
		styler.setXAxisTitleVisible(false);
		styler.setLegendVisible(false);

		styler.setAxisTicksLineVisible(false);

	    styler.setAxisTickPadding(5);
	    styler.setMarkerSize(12);
	    styler.setAxisTickMarkLength(5);
	    styler.setPlotMargin(25);


		styler.setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
	    styler.setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 13));



	    styler.setDatePattern("dd-MM-YYYY");
	    styler.setDecimalPattern("#0 €");

	    styler.setLocale(Locale.CANADA);
	    styler.setAntiAlias(true);

	    List<Date> xData = new ArrayList<>();
	    List<Double> yData = new ArrayList<>();


	    for (HistoricalQuote historicalQuote : deustock.getHistory()) {
		    xData.add(historicalQuote.getDate().getTime());
		    yData.add(historicalQuote.getClose().doubleValue());
		}


	    XYSeries series = chart.addSeries("Close Price", xData, yData);

	    series.setLineColor(XChartSeriesColors.BLUE);
		series.setLineStyle(SeriesLines.SOLID);
		series.setMarker(SeriesMarkers.NONE);
		series.setFillColor(new Color(200,200,255));

	 
	    return chart;
	}

}
