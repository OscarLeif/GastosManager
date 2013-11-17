package com.gastosManager;

import java.util.List;

import Database.UsersDataSource;
import Database.Usuario;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

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

	datasource = new UsersDataSource(this);
	datasource.open();

	final ListView lista = (ListView) findViewById(R.id.listaInformes);
	List<Usuario> values = datasource.darTodosLosUsuario();
	ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,
		android.R.layout.simple_list_item_1, values);
	lista.setAdapter(adapter);
	// use the SimpleCursorAdapter to show the
	// elements in a ListView

	lista.setOnItemLongClickListener(new OnItemLongClickListener()
	{

	    @Override
	    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		    int arg2, long arg3)
	    {
		// TODO Auto-generated method stub
		System.out.println("Long Click working");
		showPopup(arg1);

		return false;
	    }
	});

    }

    @SuppressLint("NewApi")
    public void showPopup(View v)
    {
	PopupMenu popup = new PopupMenu(this, v);
	MenuInflater inflater = popup.getMenuInflater();
	inflater.inflate(R.menu.context_menu, popup.getMenu());
	popup.show();
    }


    @Override
    protected void onResume()
    {
	// TODO Auto-generated method stub
	super.onResume();
	datasource.open();
	ListView lista = (ListView) findViewById(R.id.listaInformes);
	// use the SimpleCursorAdapter to show the
	// elements in a ListView
	List<Usuario> values = datasource.darTodosLosUsuario();
	ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this,
		R.layout.rowlayout, R.id.labelInforme , values);
	lista.setAdapter(adapter);
	lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
	{
	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1,
		    int position, long arg3)
	    {

		// Cursor o = (Cursor) lista.getItemAtPosition(position);
		// cargaGestionCuenta(o.getString(o.getColumnIndex("_id")),o.getString(o.getColumnIndex("desCuenta")));
		List<Usuario> listaUsuarios = datasource.darTodosLosUsuario();
		Usuario tmp = listaUsuarios.get(position);
		int user_ID = (int) tmp.getId();
		crearNuevaActividadUsuario(arg0, user_ID);
		Log.d("Usuario ID: ", Integer.toString(user_ID));

	    }
	});

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
	    ListView lista = (ListView) findViewById(R.id.listaInformes);
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
	    if (posicionListView < 0)
		break;
	case R.id.AcercaDeGM:
	    Dialog d = new Dialog(MainActivity.this);
	    d.setContentView(R.layout.acerca_de_gm);
	    d.setTitle("Acerca de Gastos Manager");
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

    public void crearNuevaActividadUsuario(View v, int user_ID)
    {
	if (user_ID >= 0)
	{
	    datasource.open();
	    Intent i = new Intent(this, TablayoutActivity.class);
	    // String posicionString = Integer.toString(posicionListView);
	    // List<Usuario> listaUsuarios = datasource.darTodosLosUsuario();
	    // Usuario tmp = listaUsuarios.get(user_ID);
	    // int user_ID1 = (int) tmp.getId();
	    String position = Integer.toString(user_ID);
	    i.putExtra("Position", position);
	    datasource.close();
	    startActivity(i);

	}
	if (user_ID <= -1)
	{
	    System.out
		    .println("El indice es negativo seguro, no hay elementos en la base de datos.");
	}
    }

}
