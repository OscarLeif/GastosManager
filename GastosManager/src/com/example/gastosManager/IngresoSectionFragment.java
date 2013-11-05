package com.example.gastosManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Database.GastoIngreso;
import Database.UsersDataSource;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class IngresoSectionFragment extends Fragment
{

    private long user_id;
    private UsersDataSource datasource;
    private ListView lista;
    private static long idStatico;
    private Bundle savedInstanceState;
    private ArrayList<GastoIngreso> arregloG;
    private ArrayList<GastoIngreso> arregloIngresos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {
	View rootView = inflater.inflate(R.layout.fragment_section_ingresos,
		container, false);

	if (savedInstanceState != null)
	{
	    this.savedInstanceState = savedInstanceState;
	    savedInstanceState.putInt("userKey", (int) idStatico);
	    idStatico = savedInstanceState.getInt("userKey");
	}
	if (savedInstanceState == null)
	{
	    idStatico = darUserID(user_id);
	}

	lista = (ListView) rootView.findViewById(R.id.listaInformes);
	datasource = new UsersDataSource(getActivity());
	datasource.open();

	// use the SimpleCursorAdapter to show the
	// elements in a ListView
	List<GastoIngreso> values = datasource.darTodosLosGastoIngreso();
	ArrayList<GastoIngreso> arregloG = new ArrayList<GastoIngreso>();

	arregloG = sacarElementosDeUsuarioIngresos(values);

	ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(
		getActivity(), android.R.layout.simple_list_item_1, arregloG);
	lista.setAdapter(adapter);

	lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
	{
	    public void onItemClick(AdapterView<?> arg0, View arg1,
		    int position, long arg3)
	    {

		// Cursor o =(Cursor) lista.getItemAtPosition(position);
		// cargaGestionCuenta(o.getString(o.getColumnIndex("_id")),o.getString(o.getColumnIndex("desCuenta")));
		Log.d("Pulsado item: ", String.valueOf(position));
		// Mostramos la informacion del ingreso en buen detalle.
		Dialog d = new Dialog(getActivity());
		d.setContentView(R.layout.dialog_informacion_gasto_ingreso);
		d.setTitle("Informacion del Ingreso");
		// Necesitamos la informacion de esta lista
		TextView t = (TextView) d.findViewById(R.id.textViewInformes);

		List<GastoIngreso> values = datasource
			.darTodosLosGastoIngreso();
		ArrayList<GastoIngreso> arregloG = new ArrayList<GastoIngreso>();

		arregloG = sacarElementosDeUsuarioIngresos(values);

		ArrayList<GastoIngreso> tmp = sacarElementosDeUsuarioIngresos(values);
		GastoIngreso ingreso = tmp.get(position);
		t.setText("Estamos funcionando");
		System.out.println(t.getText());
		// Log.d("Metodo activado nuevamente: ","pulsado");
		d.show();
	    }
	});

	rootView.findViewById(R.id.botonNuevoIngreso).setOnClickListener(
		new View.OnClickListener()
		{
		    @Override
		    public void onClick(View view)
		    {
			Intent intent = new Intent(getActivity(),
				Registro_NuevoGastoIngreso.class);
			intent.putExtra("key", user_id);
			intent.putExtra("GastoIngreso", "ingreso");
			startActivity(intent);
			System.out
				.println("el boton nuevo ingreso funciona correctamente");
		    }
		});

	return rootView;
    }

    @Override
    public void onResume()
    {
	// TODO Auto-generated method stub
	super.onResume();
	datasource = new UsersDataSource(getActivity());
	datasource.open();

	if (savedInstanceState != null)
	{
	    this.savedInstanceState = savedInstanceState;
	    savedInstanceState.putInt("userKey", (int) idStatico);
	    idStatico = savedInstanceState.getInt("userKey");
	}
	if (savedInstanceState == null)
	{
	    idStatico = darUserID(user_id);
	}

	// use the SimpleCursorAdapter to show the
	// elements in a ListView
	final List<GastoIngreso> values = datasource.darTodosLosGastoIngreso();
	ArrayList<GastoIngreso> arregloG = new ArrayList<GastoIngreso>();

	arregloG = sacarElementosDeUsuarioIngresos(values);
	ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(
		getActivity(), android.R.layout.simple_list_item_1, arregloG);
	lista.setAdapter(adapter);

	lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
	{
	    public void onItemClick(AdapterView<?> arg0, View arg1,
		    int position, long arg3)
	    {

		// Cursor o =(Cursor) lista.getItemAtPosition(position);
		// cargaGestionCuenta(o.getString(o.getColumnIndex("_id")),o.getString(o.getColumnIndex("desCuenta")));
		Log.d("Pulsado item: ", String.valueOf(position));
		// Mostramos la informacion del ingreso en buen detalle.
		Dialog d = new Dialog(getActivity());
		d.setContentView(R.layout.dialog_informacion_gasto_ingreso);
		d.setTitle("Informacion del Ingreso");

		// Necesitamos la informacion de esta lista
		TextView t = (TextView) d
			.findViewById(R.id.textViewDIngresoGasto);
		t.setText("Ingreso:");
		TextView t1 = (TextView) d.findViewById(R.id.textViewDConcepto);
		TextView t2 = (TextView) d.findViewById(R.id.textViewDValor);
		TextView t3 = (TextView) d.findViewById(R.id.textViewDFecha);

		ArrayList<GastoIngreso> tmp = sacarElementosDeUsuarioIngresos(values);
		GastoIngreso ingreso = tmp.get(position);

		t1.setText(ingreso.getConcepto());
		t2.setText(String.valueOf(ingreso.getValor()));
		String fecha = ingreso.getFecha().replace("00:00:00", "");
		t3.setText(fecha);

		System.out.println(t.getText());
		// Log.d("Metodo activado nuevamente: ","pulsado");
		d.show();
	    }
	});

    }

    public ArrayList<GastoIngreso> sacarElementosDeUsuarioIngresos(
	    List<GastoIngreso> lista)
    {
	ArrayList<GastoIngreso> arregloGastoIngreso = new ArrayList<GastoIngreso>();
	ArrayList<GastoIngreso> arregloIngresos = new ArrayList<GastoIngreso>();
	for (int i = 0; i < lista.size(); i++)
	{
	    if (lista.get(i).getId_usuario() == idStatico
		    && lista.get(i).getIngresoGasto().toString().length() == 7)
	    {
		arregloGastoIngreso.add(lista.get(i));
	    }
	}

	// return arregloGastoIngreso; Este es para devolver todo basado en la
	// base de datos
	return arregloGastoIngreso;

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

    public long darUserID(long userKey)
    {
	// TODO Auto-generated method stub
	return user_id = userKey;
    }

    @Override
    public void onDestroy()
    {
	// TODO Auto-generated method stub
	super.onDestroy();

    }

}
