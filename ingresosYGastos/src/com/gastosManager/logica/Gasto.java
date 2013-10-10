package com.gastosManager.logica;

import java.sql.Date;

import android.widget.DatePicker;

public class Gasto {

	private int gasto;
	private Date fecha;
	private String lugar;

	public Gasto(int ingreso, Date fecha, String lugar) {
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

	public Date darFecha() {
		return fecha;
	}

	public void CambiarFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String darLugar() {
		return lugar;
	}

	public void CambiarLugar(String lugar) {
		this.lugar = lugar;
	}
}
