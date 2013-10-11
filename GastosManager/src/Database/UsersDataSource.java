package Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gastosManager.logica.GastoIngreso;

public class UsersDataSource {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns_tableUsers = { MySQLiteHelper.USUARIO_ID,
			MySQLiteHelper.USUARIO_NOMBRE };

	public UsersDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	
	

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Usuario agregarUsuario(String nombre) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.USUARIO_NOMBRE, nombre);
		long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS, allColumns_tableUsers,
				MySQLiteHelper.USUARIO_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		Usuario newUsuario = cursorToUsuario(cursor);
		cursor.close();
		return newUsuario;

	}
	
	/**
	 * Basado en el ID del usuario actual es donde se agregara, la
	 * nueva informacion para la base de datos.
	 * @param usuario
	 * @return 
	 */
	public long crearNuevoGastoIngreso(GastoIngreso gastoIngreso)
	{
		ContentValues values = new ContentValues();
		
		values.put(MySQLiteHelper.INGRESO_GASTO, gastoIngreso.getIngresoGasto());
		values.put(MySQLiteHelper.VALOR, gastoIngreso.getValor());
		values.put(MySQLiteHelper.ID_USUARIO, gastoIngreso.getId_usuario());
		
		//insertar fila
		long gasto_ingreso = database.insert(MySQLiteHelper.TABLE_INGRESOS_Y_GASTOS, null, values);
		
		return gasto_ingreso;
	}

	public void borrarUsuario(Usuario usuario) {
		long id = usuario.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_USERS, MySQLiteHelper.USUARIO_ID
				+ " = " + id, null);

	}

	public List<Usuario> darTodosLosUsuario() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS, allColumns_tableUsers,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Usuario usuario = cursorToUsuario(cursor);
			usuarios.add(usuario);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return usuarios;
	}
	
	public List<GastoIngreso> darTodosLosGastoIngreso ()
	{
		List<GastoIngreso> listaGastosIngresos = new ArrayList<GastoIngreso>();
		String selectQuery = "select  * from " + MySQLiteHelper.TABLE_INGRESOS_Y_GASTOS;
		System.out.println("Select Querry: " + selectQuery);
		
		Cursor c = database.rawQuery(selectQuery, null);
		
		//Looping through all rows and ading to list
		if(c.moveToFirst() )
		{
			do
			{
				GastoIngreso ig = new GastoIngreso();
				ig.setId_usuario(c.getLong(c.getColumnIndex(MySQLiteHelper.USUARIO_ID)));
				ig.setConcepto(c.getString(c.getColumnIndex(MySQLiteHelper.CONCEPTO)));
				//ig.setFecha(c.get))
				ig.setIngresoGasto(c.getString(c.getColumnIndex(MySQLiteHelper.INGRESO_GASTO)));
				ig.setValor(c.getInt(c.getColumnIndex(MySQLiteHelper.VALOR)));
				
				listaGastosIngresos.add(ig);
				
			}while(c.moveToNext());
			
		}
		return listaGastosIngresos;
	}

	private Usuario cursorToUsuario(Cursor cursor) {
		Usuario usuario = new Usuario();
		usuario.setId(cursor.getLong(0));
		usuario.setNombre(cursor.getString(1));
		return usuario;
	}
}