package aChart.Core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import Database.GastoIngreso;
import Database.UsersDataSource;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gastosManager.R;

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
    
    private long user_id;
    
    private UsersDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_xy__plot);
	Bundle extras = getIntent().getExtras();
	user_id = extras.getLong("Key");

	mRenderer.setApplyBackgroundColor(true);
	mRenderer.setBackgroundColor(Color.argb(85, 125, 255, 255));

	mRenderer.setAxisTitleTextSize(16);
	mRenderer.setChartTitleTextSize(20);
	mRenderer.setXTitle("Fecha");
	mRenderer.setYTitle("Ingreso");
	mRenderer.setXAxisMin(0);
	mRenderer.setXAxisMax(30);
	mRenderer.setYAxisMin(0);
	mRenderer.setYAxisMax(15);
	// mRenderer.setLabelsTextSize(15);
	// mRenderer.setLegendTextSize(15);
	mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
	mRenderer.setZoomButtonsVisible(true);
	mRenderer.setPointSize(5);
	mRenderer.setXLabels(12);
	mRenderer.setYLabels(10);
	//mRenderer.addXTextLabel(1, "Jan");
	newSeries();
	agregarValorXY(2, 1);
	agregarValorXY(3, 2);
	recogerDatos30dias();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
	super.onSaveInstanceState(outState);
	// save the current data, for instance when changing screen orientation
	outState.putSerializable("dataset", mDataset);
	outState.putSerializable("renderer", mRenderer);
	outState.putSerializable("current_series", mCurrentSeries);
	outState.putSerializable("current_renderer", mCurrentRenderer);
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
	// setSeriesWidgetsEnabled(true); do nothing method Only in this class
	// XY_PLOTACTIVITY
	// mChartView.repaint();

    }

    public void agregarValorXY(long x, long y)
    {

	mCurrentSeries.add(x, y);
    }
    /**
     * Debemos comparar las fechas, desde el dia de hoy y decirle que.
     * Desde la fecha actual o menos tomar los ultimos 30 dias.
     */
    public void recogerDatos30dias()
    {
	datasource = new UsersDataSource(this);
	datasource.open();
	Calendar c = Calendar.getInstance();
	System.out.println("Current time => " + c.getTime());
	ArrayList<Integer> dias = new ArrayList<Integer>();
	for(int i = 0 ;i<=30; i++){dias.add(i);}// Creo un arreglo donde existen 30 elementos cada uno representa un dia.
	
	Date todayDate = c.getTime();
	c.add(Calendar.DAY_OF_MONTH, -30);
	
	//if(todayDate.after(historyDate) && todayDate.before(futureDate)) {	   } // In between	}
	
	ArrayList<GastoIngreso> ingresos = (ArrayList<GastoIngreso>) datasource.darTodosLosGastoIngreso();
	ArrayList<GastoIngreso> soloIngresos = darIngresos(ingresos);
	ArrayList<GastoIngreso> solo30DiasIngresos = new ArrayList<GastoIngreso>();
	
	for(int i = 0;i<=soloIngresos.size();i++)
	{
	    //if(solo30DiasIngresos.get(i).getFecha())
	}
	

    }
    
    public ArrayList<GastoIngreso> darIngresos(ArrayList<GastoIngreso> array)
    {
	ArrayList<GastoIngreso> tmp = new ArrayList<GastoIngreso>();
	for (int j = 0; j < array.size(); j++)
	{
	    System.out.println(array.get(j).getIngresoGasto()
		    + " Eso es un gastoIngreso");
	    if (array.get(j).getIngresoGasto().toString() == "gasto")
	    {
		tmp.add(array.get(j));
	    }
	}
	return tmp;
    }

    @Override
    protected void onResume()
    {
	super.onResume();
	if (mChartView == null)
	{
	    LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
	    mChartView = ChartFactory.getLineChartView(this, mDataset,
		    mRenderer);
	    // enable the chart click events
	    mRenderer.setClickEnabled(true);
	    mRenderer.setSelectableBuffer(10);
	    mChartView.setOnClickListener(new View.OnClickListener()
	    {
		public void onClick(View v)
		{
		    // handle the click event on the chart
		    SeriesSelection seriesSelection = mChartView
			    .getCurrentSeriesAndPoint();
		    if (seriesSelection == null)
		    {
			Toast.makeText(XY_PlotActivity.this,
				"No chart element", Toast.LENGTH_SHORT).show();
		    } else
		    {
			// display information of the clicked point
			Toast.makeText(
				XY_PlotActivity.this,
				"Chart element in series index "
					+ seriesSelection.getSeriesIndex()
					+ " data point index "
					+ seriesSelection.getPointIndex()
					+ " was clicked"
					+ " closest point value X="
					+ seriesSelection.getXValue() + ", Y="
					+ seriesSelection.getValue(),
				Toast.LENGTH_SHORT).show();
		    }
		}
	    });
	    layout.addView(mChartView, new LayoutParams(
		    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	    boolean enabled = mDataset.getSeriesCount() > 0;
	    // setSeriesWidgetsEnabled(enabled);
	} else
	{
	    mChartView.repaint();
	}
    }

}
