package com.example.gastosManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * En esta clase es donde se asigna el contenido de las Tabs,
 * 
 * @author HammerH11
 * 
 */
public class AppSectionsPagerAdapter extends FragmentPagerAdapter
{
    private long userKey;

    public AppSectionsPagerAdapter(FragmentManager fm)
    {
	super(fm);
    }

    /**
     * Dentro de este metodo estan las asignaciones de cada actividad. Esto
     * numero i, depende del numero de tabs creadas anteriormente. Si creamos 3
     * tabs son 0,1,2 casos. el default case remplazara esos casos que no
     * existieron, si solo use un case 1, case 2 y 3 seran default. Recordar que
     * cada retorno del case es una clase Fragment. Este numero i, viene del
     * override getCount();
     */
    @Override
    public Fragment getItem(int i)
    {
	switch (i)
	{
	case 0:
	    // The first section of the app is the most interesting -- it offers
	    // a launchpad into the other demonstrations in this example
	    // application.
	    // Al retornar este fragmento creara el contenido para ubicarlo
	    // dentro
	    // de la tab.

	    IngresoSectionFragment ingresosFragment = new IngresoSectionFragment();
	    ingresosFragment.darUserID(userKey);
	    return ingresosFragment;

	case 1:

	    GastosSectionFragment gastosFragment = new GastosSectionFragment();// Esta
	    // clase
	    // hereda
	    // de
	    // Fragment.
	    gastosFragment.darUserID(userKey);
	    return gastosFragment;

	default:
	    // The other sections of the app are dummy placeholders.
	    MiscelaneaSectionFragment fragment = new MiscelaneaSectionFragment();
	    fragment.darUserID(userKey);
	    return fragment;
	}
    }

    /**
     * metodo getCount() retorna el numero, que finalmente decide, cuantos tabs,
     * o mejor Fragments se crearan. Por otro lado el nombre de cada tab se
     * designa en TblayoutActivity, esa asignacion descendente.
     */

    @Override
    public int getCount()
    {
	return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
	return "Section " + (position + 1);
    }

    public long getUserKey(long usuario_ID)
    {
	// TODO Auto-generated method stub
	return userKey = usuario_ID;
    }
}
