package Database;

import java.sql.Date;

public class GastoIngreso
{
    private int IG_PK;
    private String concepto;
    private String fecha;
    private String ingresoGasto;
    private int valor;
    private long id_usuario;

    public GastoIngreso()
    {
    }

    public String getConcepto()
    {
	return concepto;
    }

    public void setIG_PK(int IG_PK)
    {
	this.IG_PK = IG_PK;
    }

    public int getIG_PK()
    {
	return IG_PK;
    }

    public void setConcepto(String concepto)
    {
	this.concepto = concepto;
    }

    public String getFecha()
    {
	return fecha;
    }

    public void setFecha(String fecha)
    {
	this.fecha = fecha;
    }

    public String getIngresoGasto()
    {
	return ingresoGasto;
    }

    public void setIngresoGasto(String ingresoGasto)
    {
	this.ingresoGasto = ingresoGasto;
    }

    public int getValor()
    {
	return valor;
    }

    public void setValor(int valor)
    {
	this.valor = valor;
    }

    public long getId_usuario()
    {
	return id_usuario;
    }

    public void setId_usuario(long id_usuario)
    {
	this.id_usuario = id_usuario;
    }

    @Override
    public String toString()
    {
	// TODO Auto-generated method stub
	return "Usuario ID: " + id_usuario + " " + concepto + " : Valor: "
		+ valor + "Fecha: " + getFecha();
    }

}
