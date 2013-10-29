package com.example.gastosManager;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DummySectionFragment extends Fragment
{

    
    public static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listaMiscelanea;
    private long user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {
	View rootView = inflater.inflate(R.layout.fragment_section_dummy,
		container, false);
	ArrayList<String> opciones = new ArrayList<String>();
	opciones.add("Informe de ingresos");
	opciones.add("Informe de gastos");
	opciones.add("Informe de movimientos");
	
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, opciones);
	listaMiscelanea = (ListView)rootView.findViewById(R.id.lista_miscelanea);
	listaMiscelanea.setAdapter(adapter);

	return rootView;
    }

    public long darUserID(long userKey)
    {
	// TODO Auto-generated method stub
	return user_id = userKey;
    }
}