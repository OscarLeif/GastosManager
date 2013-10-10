package com.example.gastosManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ingresosygastos.R;

public class DummySectionFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_section_dummy,
				container, false);
		Bundle args = getArguments();
		((TextView) rootView.findViewById(android.R.id.text1))
				.setText(getString(R.string.title_activity_usuario_datos_,
						args.getInt(ARG_SECTION_NUMBER)));
		return rootView;
	}
}