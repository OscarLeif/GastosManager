package com.example.gastosManager;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class XY_PlotActivity extends Activity
{

    /** The main dataset that includes all the series that go into a chart. */
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    /** The main renderer that includes all the renderers customizing a chart. */
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    /** The most recently added series. */
    private XYSeries mCurrentSeries;
    /** The most recently created renderer, customizing the current series. */
    private XYSeriesRenderer mCurrentRenderer;
    /** Button for creating a new series of data. */
    private Button mNewSeries;
    /** Button for adding entered data to the current series. */
    private Button mAdd;
    /** Edit text field for entering the X value of the data to be added. */
    private EditText mX;
    /** Edit text field for entering the Y value of the data to be added. */
    private EditText mY;
    /** The chart view that displays the data. */
    private GraphicalView mChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_xy__plot);

	mRenderer.setApplyBackgroundColor(true);
	mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
	mRenderer.setAxisTitleTextSize(16);
	mRenderer.setChartTitleTextSize(20);
	mRenderer.setLabelsTextSize(15);
	mRenderer.setLegendTextSize(15);
	mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
	mRenderer.setZoomButtonsVisible(true);
	mRenderer.setPointSize(5);
	newSeries();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.xy__plot, menu);
	return true;
    }

    /** Este metodo crea una nueva serie para nuestro chart */
    public void newSeries()
    {
	String seriesTitle = "Series " + (mDataset.getSeriesCount() + 1);
	// create a new series of data
	XYSeries series = new XYSeries(seriesTitle);
	mDataset.addSeries(series);
	mCurrentSeries = series;
	// create a new renderer for the new series
	XYSeriesRenderer renderer = new XYSeriesRenderer();
	mRenderer.addSeriesRenderer(renderer);
	// set some renderer properties
	renderer.setPointStyle(PointStyle.CIRCLE);
	renderer.setFillPoints(true);
	renderer.setDisplayChartValues(true);
	renderer.setDisplayChartValuesDistance(10);
	mCurrentRenderer = renderer;
	//setSeriesWidgetsEnabled(true); do nothing method Only in this class XY_PLOTACTIVITY
	mChartView.repaint();

    }

}
