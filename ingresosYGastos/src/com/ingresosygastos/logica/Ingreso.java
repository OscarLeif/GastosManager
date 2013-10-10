package com.ingresosygastos.logica;

import android.widget.DatePicker;

public class Ingreso {

	private int ingreso;
	private DatePicker fecha;
	private String lugar;
	
	public Ingreso(int ingreso, DatePicker fecha, String lugar)
	{
		this.ingreso=ingreso;
		this.fecha=fecha;
		this.lugar=lugar;
	}
	
	public int darIngreso()
	{
		return ingreso;
	}
	
	public void CambiarIngreso(int ingreso)
	{
		this.ingreso = ingreso;
	}
	
	public DatePicker darFecha()
	{
		return fecha;
	}
	
	public void CambiarFecha(DatePicker fecha)
	{
		this.fecha = fecha;
	}
	
	public String darLugar()
	{
		return lugar;
	}
	
	public void CambiarLugar(String lugar)
	{
		this.lugar = lugar;
	}
}
