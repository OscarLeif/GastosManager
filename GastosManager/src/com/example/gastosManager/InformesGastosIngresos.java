package com.example.gastosManager;

import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Database.GastoIngreso;
import Database.UsersDataSource;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InformesGastosIngresos extends Activity {

	private long id;
	private UsersDataSource dataBase;
	private ArrayList<GastoIngreso> ingresosUsuario;
	private int gastoIngreso; // Si es 0 es un ingreso, 1 es un gasto.
	private java.util.Date fechaInicial;
	private java.util.Date fechaFinal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informes_gastos_ingresos);
		Bundle extras = getIntent().getExtras();
		id = extras.getLong("Key");
		fechaInicial = (Date)this.getIntent().getExtras().get("dateI");
		//fechaInicial = new Date(getIntent().getExtras().getLong("dateI", -1));
		fechaFinal = (Date) this.getIntent().getExtras().get("dateF");
		gastoIngreso = extras.getInt("IG");
		dataBase = new UsersDataSource(this);
		ingresosUsuario = new ArrayList<GastoIngreso>();
		dataBase.open();
		ListView lista = (ListView) findViewById(R.id.listaInformes);
		
		
		if (gastoIngreso == 0) {
			List<GastoIngreso> values = sacarElementosDeUsuarioIngresosIngresos(dataBase
					.darIngresosIntervalo2Fechas(fechaInicial, fechaFinal));
			ingresosUsuario = (ArrayList<GastoIngreso>) values;
			ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(
					this, android.R.layout.simple_list_item_1, values);
			lista.setAdapter(adapter);
			
			TextView total = (TextView) findViewById(R.id.textViewInformes);
			String resultado = "Total Ingresos: "
					+ Integer.toString(darTotalGastoIngresos());
			total.setText(resultado);
		}
		if (gastoIngreso == 1) {
			List<GastoIngreso> values = sacarElementosDeUsuarioIngresosGastos(dataBase
					.darIngresos30DiasAntes());
			ingresosUsuario = (ArrayList<GastoIngreso>) values;
			ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(
					this, android.R.layout.simple_list_item_1, values);
			lista.setAdapter(adapter);
			
			TextView total = (TextView) findViewById(R.id.textViewInformes);
			String resultado = "Total Gastos: "
					+ Integer.toString(darTotalGastoIngresos());
			total.setText(resultado);

		}
		
		lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
		    public void onItemClick(AdapterView<?> arg0, View arg1,
			    int position, long arg3)
		    {
			dataBase.open();
			final List<GastoIngreso> values = dataBase.darTodosLosGastoIngreso();

			// Cursor o =(Cursor) lista.getItemAtPosition(position);
			// cargaGestionCuenta(o.getString(o.getColumnIndex("_id")),o.getString(o.getColumnIndex("desCuenta")));
			Log.d("Pulsado item: ", String.valueOf(position));
			// Mostramos la informacion del ingreso en buen detalle.
			Dialog d = new Dialog(InformesGastosIngresos.this);
			d.setContentView(R.layout.dialog_informacion_gasto_ingreso);
			if(gastoIngreso == 0)
			d.setTitle("Informacion del Ingreso");
			if(gastoIngreso == 1)
			    d.setTitle("Informacion del gasto");

			// Necesitamos la informacion de esta lista
			if(gastoIngreso ==0)
			{
			TextView t = (TextView) d
				.findViewById(R.id.textViewDIngresoGasto);
			t.setText("Ingreso:");
			TextView t1 = (TextView) d.findViewById(R.id.textViewDConcepto);
			TextView t2 = (TextView) d.findViewById(R.id.textViewDValor);
			TextView t3 = (TextView) d.findViewById(R.id.textViewDFecha);

			ArrayList<GastoIngreso> tmp = sacarElementosDeUsuarioIngresosIngresos(values);
			GastoIngreso ingreso = tmp.get(position);

			t1.setText(ingreso.getConcepto());
			t2.setText(String.valueOf(ingreso.getValor()));
			String fecha = ingreso.getFecha().replace("00:00:00", "");
			t3.setText(fecha);

			System.out.println(t.getText());
			// Log.d("Metodo activado nuevamente: ","pulsado");
			d.show();
			}
			
			if(gastoIngreso ==1)
			{
			TextView t = (TextView) d
				.findViewById(R.id.textViewDIngresoGasto);
			t.setText("Ingreso:");
			TextView t1 = (TextView) d.findViewById(R.id.textViewDConcepto);
			TextView t2 = (TextView) d.findViewById(R.id.textViewDValor);
			TextView t3 = (TextView) d.findViewById(R.id.textViewDFecha);

			ArrayList<GastoIngreso> tmp = sacarElementosDeUsuarioIngresosGastos(values);
			GastoIngreso ingreso = tmp.get(position);

			t1.setText(ingreso.getConcepto());
			t2.setText(String.valueOf(ingreso.getValor()));
			String fecha = ingreso.getFecha().replace("00:00:00", "");
			t3.setText(fecha);

			System.out.println(t.getText());
			// Log.d("Metodo activado nuevamente: ","pulsado");
			d.show();
			}
			dataBase.close();
		    }
		});
		dataBase.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.informes_gastos_ingresos, menu);
		return true;
	}

	public int darTotalGastoIngresos() {

		int sumador = 0;
		for (int i = 0; i < ingresosUsuario.size(); i++) {
			sumador = +ingresosUsuario.get(i).getValor();
		}
		return sumador;
	}

	public void darIngresosUsuarioID(long ID) {

	}

	public ArrayList<GastoIngreso> sacarElementosDeUsuarioIngresosIngresos(
			List<GastoIngreso> lista) {
		ArrayList<GastoIngreso> arregloGastoIngreso = new ArrayList<GastoIngreso>();
		ArrayList<GastoIngreso> arregloIngresos = new ArrayList<GastoIngreso>();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId_usuario() == id
					&& lista.get(i).getIngresoGasto().toString().length() == 7) {
				arregloGastoIngreso.add(lista.get(i));
			}
		}

		// return arregloGastoIngreso; Este es para devolver todo basado en la
		// base de datos
		return arregloGastoIngreso;

	}

	public ArrayList<GastoIngreso> sacarElementosDeUsuarioIngresosGastos(
			List<GastoIngreso> lista) {
		ArrayList<GastoIngreso> arregloGastoIngreso = new ArrayList<GastoIngreso>();
		ArrayList<GastoIngreso> arregloIngresos = new ArrayList<GastoIngreso>();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId_usuario() == id
					&& lista.get(i).getIngresoGasto().toString().length() == 5) {
				arregloGastoIngreso.add(lista.get(i));
			}
		}

		// return arregloGastoIngreso; Este es para devolver todo basado en la
		// base de datos
		return arregloGastoIngreso;

	}
	
	@Override
	public void onBackPressed()
	{
	// TODO Auto-generated method stub
	super.onBackPressed();
	finish();
	}


}
