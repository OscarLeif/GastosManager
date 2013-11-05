package com.example.gastosManager;

import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Database.GastoIngreso;
import Database.UsersDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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
		fechaInicial = new Date(getIntent().getExtras().getLong("dateI", -1));
		gastoIngreso = extras.getInt("IG");
		dataBase = new UsersDataSource(this);
		ingresosUsuario = new ArrayList<GastoIngreso>();
		dataBase.open();
		ListView lista = (ListView) findViewById(R.id.listaInformes);

		if (gastoIngreso == 0) {
			List<GastoIngreso> values = sacarElementosDeUsuarioIngresosIngresos(dataBase
					.darIngresos30DiasAntes());
			ingresosUsuario = (ArrayList<GastoIngreso>) values;
			ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(
					this, android.R.layout.simple_list_item_1, values);
			lista.setAdapter(adapter);
			dataBase.close();
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
			dataBase.close();
			TextView total = (TextView) findViewById(R.id.textViewInformes);
			String resultado = "Total Gastos: "
					+ Integer.toString(darTotalGastoIngresos());
			total.setText(resultado);

		}
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}

}
