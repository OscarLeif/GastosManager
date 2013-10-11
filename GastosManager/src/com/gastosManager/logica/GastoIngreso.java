package com.gastosManager.logica;

import java.sql.Date;

public class GastoIngreso 
{
	private String concepto;
	private Date fecha;
	private String ingresoGasto;
	private int valor;
	private long id_usuario;
	
	public GastoIngreso() {	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getFecha() {
		return fecha.toGMTString();
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIngresoGasto() {
		return ingresoGasto;
	}

	public void setIngresoGasto(String ingresoGasto) {
		this.ingresoGasto = ingresoGasto;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return concepto;
	}
	
	

}
