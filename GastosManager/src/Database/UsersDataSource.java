package Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsersDataSource
{
    private String TAG = "Clase UsersDataSource";
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns_tableUsers = { MySQLiteHelper.USUARIO_ID,
	    MySQLiteHelper.USUARIO_NOMBRE };

    private String[] allColumns_tableIngresosGastos = { MySQLiteHelper.IG_ID,
	    MySQLiteHelper.CONCEPTO, MySQLiteHelper.FECHA,
	    MySQLiteHelper.INGRESO_GASTO, MySQLiteHelper.VALOR,
	    MySQLiteHelper.ID_USUARIO };

    public UsersDataSource(Context context)
    {
	dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException
    {
	database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
	dbHelper.close();
    }

    /**
     * Agrega un nuevo usuario, a la base de datos dentro de la tabla usuarios.
     * Al agregar se le asigna un ID automatico, ademas aqui dentro creamos un
     * nuevo. Usuario, y se guarda en la base de datos
     * 
     * @param nombre
     * @return un nuevoUsuario que anteriormente se agrego a la base de datos.
     */

    public Usuario agregarUsuario(String nombre)
    {
	ContentValues values = new ContentValues();
	values.put(MySQLiteHelper.USUARIO_NOMBRE, nombre);
	// Insertar Fila
	long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
		values);
	Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
		allColumns_tableUsers, MySQLiteHelper.USUARIO_ID + " = "
			+ insertId, null, null, null, null);
	cursor.moveToFirst();
	Usuario newUsuario = cursorToUsuario(cursor);
	cursor.close();
	Log.d(TAG, " : agregado nuevo usuario, " + newUsuario.getNombre());
	return newUsuario;

    }

    /**
     * Basado en el ID del usuario actual es donde se agregara, la nueva
     * informacion para la base de datos.
     * 
     * @param usuario
     * @return
     */
    public GastoIngreso crearNuevoGastoIngreso(GastoIngreso gastoIngreso)
    {
	ContentValues values = new ContentValues();
	// Posiblemente aqui este el error
	values.put(MySQLiteHelper.CONCEPTO, gastoIngreso.getConcepto());
	values.put(MySQLiteHelper.FECHA, gastoIngreso.getFecha());
	values.put(MySQLiteHelper.INGRESO_GASTO, gastoIngreso.getIngresoGasto());
	values.put(MySQLiteHelper.VALOR, gastoIngreso.getValor());
	values.put(MySQLiteHelper.ID_USUARIO, gastoIngreso.getId_usuario());

	// insertar fila
	long insertar_gasto_ingreso = database.insert(
		MySQLiteHelper.TABLE_INGRESOS_Y_GASTOS, null, values);
	Cursor cursor = database.query(MySQLiteHelper.TABLE_INGRESOS_Y_GASTOS,
		allColumns_tableIngresosGastos, MySQLiteHelper.IG_ID + " = "
			+ insertar_gasto_ingreso, null, null, null, null);
	cursor.moveToFirst();
	GastoIngreso nuevoGasto = cursorToGasto(cursor);
	cursor.close();
	return nuevoGasto;
    }

    public void borrarUsuario(Usuario usuario)
    {
	long id = usuario.getId();
	System.out.println("Comment deleted with id: " + id);
	database.delete(MySQLiteHelper.TABLE_USERS, MySQLiteHelper.USUARIO_ID
		+ " = " + id, null);
	database.delete(MySQLiteHelper.TABLE_INGRESOS_Y_GASTOS,
		MySQLiteHelper.USUARIO_ID + " = " + id, null);

    }

    public List<Usuario> darTodosLosUsuario()
    {
	List<Usuario> usuarios = new ArrayList<Usuario>();
	Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,
		allColumns_tableUsers, null, null, null, null, null);

	cursor.moveToFirst();
	while (!cursor.isAfterLast())
	{
	    Usuario usuario = cursorToUsuario(cursor);
	    usuarios.add(usuario);
	    cursor.moveToNext();
	}
	// make sure to close the cursor
	cursor.close();
	return usuarios;
    }

    public List<GastoIngreso> darTodosLosGastoIngreso()
    {
	List<GastoIngreso> listaGastosIngresos = new ArrayList<GastoIngreso>();
	String selectQuery = "select  * from "
		+ MySQLiteHelper.TABLE_INGRESOS_Y_GASTOS;
	// System.out.println("Select Querry: " + selectQuery);

	Cursor c = database.rawQuery(selectQuery, null);

	// Looping through all rows and adding to list
	if (c.moveToFirst())
	{
	    do
	    {
		GastoIngreso ig = new GastoIngreso();
		ig.setId_usuario(c.getLong(c
			.getColumnIndex(MySQLiteHelper.USUARIO_ID)));
		ig.setConcepto(c.getString(c
			.getColumnIndex(MySQLiteHelper.CONCEPTO)));
		ig.setFecha(c.getString(c.getColumnIndex(MySQLiteHelper.FECHA)));
		ig.setIngresoGasto(c.getString(c
			.getColumnIndex(MySQLiteHelper.INGRESO_GASTO)));
		ig.setValor(c.getInt(c.getColumnIndex(MySQLiteHelper.VALOR)));

		listaGastosIngresos.add(ig);

	    } while (c.moveToNext());

	}
	c.close();
	return listaGastosIngresos;
    }

    public List<GastoIngreso> darIngresos30DiasAntes()
    {
	SimpleDateFormat dateFormatToday = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	dateFormatToday.format(date);// Here now we have actual Date.

	int x = -31;
	Calendar cal = GregorianCalendar.getInstance();
	cal.add(Calendar.DAY_OF_YEAR, x);
	Date tenDaysAgo = cal.getTime();
	SimpleDateFormat dateFormatOld = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	dateFormatOld.format(tenDaysAgo);// Aqui tengo la fecha de hace 30 dias
					 // antes.
	String fechaActual = dateFormatToday.toString();
	String fechaVieja = dateFormatOld.toString();

	List<GastoIngreso> listaGastosIngresos = new ArrayList<GastoIngreso>();

	SimpleDateFormat dateFormatYouWant = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	String sCertDate = dateFormatYouWant.format(date);

	SimpleDateFormat dateFormatYouWant1 = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	String sCertDate1 = dateFormatYouWant.format(tenDaysAgo);

	String selectQuery = "select * from "
		+ MySQLiteHelper.TABLE_INGRESOS_Y_GASTOS
		+ " where FECHA between " + "'" + sCertDate1 + "'" + " and "
		+ "'" + sCertDate + "'";
	System.out.println(selectQuery);

	Cursor c = database.rawQuery(selectQuery, null);

	// Looping through all rows and adding to list
	if (c.moveToFirst())
	{
	    do
	    {
		GastoIngreso ig = new GastoIngreso();
		ig.setId_usuario(c.getLong(c
			.getColumnIndex(MySQLiteHelper.USUARIO_ID)));
		ig.setConcepto(c.getString(c
			.getColumnIndex(MySQLiteHelper.CONCEPTO)));
		ig.setFecha(c.getString(c.getColumnIndex(MySQLiteHelper.FECHA)));
		ig.setIngresoGasto(c.getString(c
			.getColumnIndex(MySQLiteHelper.INGRESO_GASTO)));
		ig.setValor(c.getInt(c.getColumnIndex(MySQLiteHelper.VALOR)));

		listaGastosIngresos.add(ig);

	    } while (c.moveToNext());

	}
	c.close();
	return listaGastosIngresos;

    }
    
    public List<GastoIngreso> darIngresosIntervalo2Fechas(Date fechaInicial, Date fechaFinal)
    {
	SimpleDateFormat dateFormatStart = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormatFinish = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	dateFormatStart.format(fechaInicial);// Here now we have the starDate
	dateFormatFinish.format(fechaFinal);//Here now we have the finish date.
	List<GastoIngreso> listaGastosIngresos = new ArrayList<GastoIngreso>();

	String primero = dateFormatStart.format(fechaInicial);
	String segundo = dateFormatStart.format(fechaFinal);
	primero.replace("00:00:00", "");
	segundo.replace("00:00:00", "");
	
	String selectQuery = "select * from "
		+ MySQLiteHelper.TABLE_INGRESOS_Y_GASTOS
		+ " where FECHA between " + "'" + primero+ "'" + " and "
		+ "'" + segundo + "'";
	System.out.println(selectQuery);
	
	Cursor c = database.rawQuery(selectQuery, null);

	// Looping through all rows and adding to list
	if (c.moveToFirst())
	{
	    do
	    {
		GastoIngreso ig = new GastoIngreso();
		ig.setId_usuario(c.getLong(c
			.getColumnIndex(MySQLiteHelper.USUARIO_ID)));
		ig.setConcepto(c.getString(c
			.getColumnIndex(MySQLiteHelper.CONCEPTO)));
		ig.setFecha(c.getString(c.getColumnIndex(MySQLiteHelper.FECHA)));
		ig.setIngresoGasto(c.getString(c
			.getColumnIndex(MySQLiteHelper.INGRESO_GASTO)));
		ig.setValor(c.getInt(c.getColumnIndex(MySQLiteHelper.VALOR)));

		listaGastosIngresos.add(ig);

	    } while (c.moveToNext());

	}
	c.close();
	return listaGastosIngresos;
	
    }

    public List<GastoIngreso> darTodosLosGastoIngresoPorUsuario(long userID)
    {
	return null;

    }

    public List<GastoIngreso> darIngresosPorUsuario(long userID)
    {
	return null;
    }

    private Usuario cursorToUsuario(Cursor cursor)
    {
	Usuario usuario = new Usuario();
	usuario.setId(cursor.getLong(0));
	usuario.setNombre(cursor.getString(1));
	return usuario;
    }

    private GastoIngreso cursorToGasto(Cursor cursor)
    {
	// TODO Auto-generated method stub
	GastoIngreso gastoIngreso = new GastoIngreso();
	gastoIngreso.setId_usuario(cursor.getLong(0));
	gastoIngreso.setConcepto(cursor.getString(1));
	gastoIngreso.setFecha(cursor.getString(2));
	gastoIngreso.setIngresoGasto(cursor.getString(3));
	gastoIngreso.setValor(cursor.getInt(4));
	gastoIngreso.setId_usuario(cursor.getLong(5));

	return gastoIngreso;
    }
}
