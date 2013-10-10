package com.gastosManager.logica;

import android.widget.DatePicker;

public class Gasto {

	private int gasto;
	private DatePicker fecha;
	private String lugar;

	public Gasto(int ingreso, DatePicker fecha, String lugar) {
		this.gasto = ingreso;
		this.fecha = fecha;
		this.lugar = lugar;
	}

	public int darGasto() {
		return gasto;
	}

	public void CambiarGasto(int ingreso) {
		this.gasto = ingreso;
	}

	public DatePicker darFecha() {
		return fecha;
	}

	public void CambiarFecha(DatePicker fecha) {
		this.fecha = fecha;
	}

	public String darLugar() {
		return lugar;
	}

	public void CambiarLugar(String lugar) {
		this.lugar = lugar;
	}
}
