package com.gastosManager;

import java.util.List;

import javax.crypto.spec.PSource;

import Database.UsersDataSource;
import Database.Usuario;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gastosManager.R;
import com.example.gastosManager.TablayoutActivity;

public class MainActivity extends Activity
{
    private UsersDataSource datasource;
    private int posicionListView = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	if (savedInstanceState == null)
	{

	}

	datasource = new UsersDataSource(this);
	datasource.open();

	final ListView lista = (ListView) findViewById(R.id.listaGastos);
	// use the SimpleCursorAdapter to show the
	// elements in a ListView
	List<Usuario> values = datasource.darTodosLosUsuario();
	ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,
		android.R.layout.simple_list_item_1, values);
	lista.setAdapter(adapter);
	indiceLista();

    }

    @Override
    protected void onResume()
    {
	// TODO Auto-generated method stub
	super.onResume();
	datasource.open();
	ListView lista = (ListView) findViewById(R.id.listaGastos);
	// use the SimpleCursorAdapter to show the
	// elements in a ListView
	List<Usuario> values = datasource.darTodosLosUsuario();
	ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,
		android.R.layout.simple_list_item_1, values);
	lista.setAdapter(adapter);

    }

    @Override
    protected void onPause()
    {
	// TODO Auto-generated method stub
	super.onPause();
	datasource.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
	// TODO Auto-generated method stub
	super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    /**
     * Metodo para agregar subMneu a un objeto de la lista. Por otro lado, no
     * funciona.
     * 
     * @param item
     * @return
     */
    public boolean crearMenuLista(Menu item)
    {
	switch (((MenuItem) item).getItemId())
	{
	case R.id.borrarActual:

	    datasource.open();
	    ListView lista = (ListView) findViewById(R.id.listaGastos);
	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    List<Usuario> values = datasource.darTodosLosUsuario();
	    Usuario usuario = values.get(posicionListView);
	    datasource.borrarUsuario(usuario);
	    break;

	default:
	    break;
	}
	return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	// TODO Auto-generated method stub

	switch (item.getItemId())
	{
	case R.id.menuAgregar:
	    Intent register = new Intent(MainActivity.this,
		    Nuevo_usuario_Activity.class);
	    startActivity(register);
	    break;
	case R.id.borrarActual:
	    if (posicionListView >= 0)
	    {
		datasource.open();
		List<Usuario> values = datasource.darTodosLosUsuario();
		Usuario usuarioEliminado = values.get(posicionListView);
		datasource.borrarUsuario(usuarioEliminado);
		onResume();
	    }
	    if (posicionListView <= -1)
		// Log cat evitamos una excepcion.
		break;
	case R.id.acerca:
	    // Show my first dialog
	    Dialog d = new Dialog(MainActivity.this);
	    d.setContentView(R.layout.acerca_de_gastosmanager);
	    d.setTitle("Acerca de Insos");
	    d.show();
	    break;
	default:
	    break;
	}
	return super.onOptionsItemSelected(item);
    }

    public void botonAgregar(View v)
    {
	Intent intent = new Intent(this, Nuevo_usuario_Activity.class);
	startActivity(intent);
    }

    public void botonOK(View v)
    {
	if (posicionListView >= 0)
	{
	    Intent i = new Intent(this, TablayoutActivity.class);
	    String posicionString = Integer.toString(posicionListView);
	    i.putExtra("Position", posicionString);
	    startActivity(i);
	}
	if (posicionListView <= -1)
	{
	    System.out
		    .println("El indice es negativo seguro, no hay elementos en la base de datos.");
	}
    }

    public void indiceLista()
    {
	ListView listaCuentas = (ListView) findViewById(R.id.listaGastos);
	listaCuentas.setClickable(true);
	listaCuentas
		.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{

		    public void onItemClick(AdapterView<?> arg0, View arg1,
			    int position, long arg3)
		    {
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
