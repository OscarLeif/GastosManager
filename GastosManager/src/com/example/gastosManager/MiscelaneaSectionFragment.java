package com.example.gastosManager;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidplot.xy.XYPlot;

public class MiscelaneaSectionFragment extends Fragment
{

    public static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listaMiscelanea;
    private long user_id;
    private XYPlot graficoBarras;
    int numInforme;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {
	View rootView = inflater.inflate(R.layout.fragment_section_miscelanea,
		container, false);
	ArrayList<String> opciones = new ArrayList<String>();
	opciones.add("Informe de ingresos");
	opciones.add("Informe de gastos");
	opciones.add("Informe de movimientos");

	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		android.R.layout.simple_list_item_1, opciones);
	listaMiscelanea = (ListView) rootView
		.findViewById(R.id.lista_miscelanea);
	listaMiscelanea.setAdapter(adapter);
	// Creamos el primer listener de esta clase Fragment
	// Con esto crearemos el primer reporte de ingresos y gastos, con
	// movimientos.
	listaMiscelanea.setOnItemClickListener(new OnItemClickListener()
	{

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1,
		    int position, long arg3)
	    {

		if (position == 0)
		{
		    Log.d("Metodo que hace reporte: ingreso = Id= ",
			    Integer.toString((int) user_id));
		    // IDemoChart p= new AverageTemperatureChart(user_id);

		}
		if (position == 1)
		{
		    Log.d("Metodo que hace reporte: ", "gastos");
		}
		if (position == 2)
		{
		    Log.d("Metodo que hace reporte: ", "movimientos");
		}

	    }
	});

	return rootView;
    }

    /**
     * Metodo de que existe en todas las clases Fragment. Recibe el parametro
     * para poder editar, ver toda la informacion del ID que esta en la base de
     * datos
     * 
     * @param userKey
     * @return
     */

    public long darUserID(long userKey)
    {
	// TODO Auto-generated method stub
	return user_id = userKey;
    }

    public void generarReporteIngresos()
    {

    }

    public void generarReporteGastos()
    {

    }
}