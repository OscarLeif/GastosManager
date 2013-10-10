package com.gastosManager;

import java.util.List;

import Database.UsersDataSource;
import Database.Usuario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gastosManager.TablayoutActivity;
import com.example.ingresosygastos.R;

public class MainActivity extends Activity {
	private UsersDataSource datasource;
	private int posicionListView = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//datasource = new UsersDataSource(this);
		//datasource.open();
		//ListView lista = (ListView) findViewById(R.id.listView1);
		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		//List<Usuario> values = datasource.darTodosLosUsuario();
		//ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,
				//android.R.layout.simple_list_item_1, values);
		///lista.setAdapter(adapter);
		///indiceLista();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//datasource.open();
		//ListView lista = (ListView) findViewById(R.id.listView1);
		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		//List<Usuario> values = datasource.darTodosLosUsuario();
		//ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,
				//android.R.layout.simple_list_item_1, values);
		//lista.setAdapter(adapter);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	public void agregarLista() {
		ListView lista = (ListView) findViewById(R.id.listView1);

		// ArrayList<Usuario> actividad =
		// ModelContainer.actividad.ListaUsuarios();
		// ArrayAdapter<Usuario> adaptador = new
		// ArrayAdapter<Usuario>(this,android.R.layout.simple_list_item_1,actividad);
		// lista.setAdapter(adaptador);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.menuAgregar:
			Intent register = new Intent(MainActivity.this,
					Nuevo_usuario_Activity.class);
			startActivity(register);
			break;
		case R.id.borrarActual:

			datasource.open();
			List<Usuario> values = datasource.darTodosLosUsuario();
			Usuario usuarioEliminado = values.get(posicionListView);
			datasource.borrarUsuario(usuarioEliminado);
			onResume();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void botonAgregar(View v) {
		Intent intent = new Intent(this, Nuevo_usuario_Activity.class);
		startActivity(intent);
	}

	public void botonOK(View v) {
		Intent i = new Intent(this, TablayoutActivity.class);
		String posicionString = Integer.toString(posicionListView);
		i.putExtra("Position", posicionString);
		startActivity(i);
	}

	public void indiceLista() {
		ListView listaCuentas = (ListView) findViewById(R.id.listView1);
		listaCuentas.setClickable(true);
		listaCuentas
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// Cursor o =(Cursor)
						// listaCuentas.getItemAtPosition(position);
						// cargaGestionCuenta(o.getString(o.getColumnIndex("_id")),o.getString(o.getColumnIndex("desCuenta")));
						// Aqui debemos cargar los datos
						String numero = Integer.toString(position);
						Log.d("Pulsado item numero: ", numero);
						posicionListView = position;
					}
				});
	}

}
