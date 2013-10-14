package com.example.gastosManager;

import java.util.ArrayList;
import java.util.List;

import Database.UsersDataSource;
import android.content.Intent;
import android.hardware.Camera.Area;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gastosManager.logica.GastoIngreso;

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

	lista = (ListView) rootView.findViewById(R.id.listaGastos);
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

	rootView.findViewById(R.id.botonNuevoIngreso).setOnClickListener(
		new View.OnClickListener()
		{
		    @Override
		    public void onClick(View view)
		    {
			Intent intent = new Intent(getActivity(),
				Registro_NuevoGasto.class);
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

	// use the SimpleCursorAdapter to show the
	// elements in a ListView
	List<GastoIngreso> values = datasource.darTodosLosGastoIngreso();
	ArrayList<GastoIngreso> arregloG = new ArrayList<GastoIngreso>();

	arregloG = sacarElementosDeUsuarioIngresos(values);
	ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(
		getActivity(), android.R.layout.simple_list_item_1, arregloG);
	lista.setAdapter(adapter);
    }

    public ArrayList<GastoIngreso> sacarElementosDeUsuarioIngresos(
	    List<GastoIngreso> lista)
    {
	ArrayList<GastoIngreso> arregloGastoIngreso = new ArrayList<GastoIngreso>();
	ArrayList<GastoIngreso> arregloIngresos = new ArrayList<GastoIngreso>();
	for (int i = 0; i < lista.size(); i++)
	{
	    if (lista.get(i).getId_usuario() == idStatico + 1 && lista.get(i).getIngresoGasto().toString().length() == 7)
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
	    System.out.println( array.get(j).getIngresoGasto() + " Eso es un gastoIngreso");
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

}
