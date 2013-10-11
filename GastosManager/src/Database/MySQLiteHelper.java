package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	//public final static String COLUMN_ID = "_id";
	//public final static String COLUMN_NOMBRE = "nombre";
	//public final static String COLUMN_GASTO = "gasto";
	//Database Version
	private static final int DATABASE_VERSION = 1;
	//DataBase Name
	private static final String DATABASE_NAME = "IngresosYGastos.db";
	//Table names
	public final static String TABLE_USERS = "Usuarios";
	public final static String TABLE_INGRESOS_Y_GASTOS = "IngresosGastos";
	//TABLE_USERS Column names.
	public static final String USUARIO_ID = "nombre_id";
	public static final String USUARIO_NOMBRE = "nombre";
	//TABLE_INGRESOS_Y_GASTOS Column names.
	private static final String IG_ID = "ingreso_gasto_id";
	public static final String CONCEPTO = "concepto";
	private static final String FECHA = "fecha";
	public static final String INGRESO_GASTO = "ingreso_gasto";
	public static final String VALOR = "valor";
	public static final String ID_USUARIO = "nombre_id";
	//Create Tables STRING.
	private static final String CREATE_TABLE_USUARIOS = 
			"create table " + TABLE_USERS + "(" + USUARIO_ID 
			+ " integer primary key autoincrement, " + USUARIO_NOMBRE + 
			" text not null)";
	private static final String CREATE_TABLE_INGRESOS_Y_GASTOS =
			"create table " + TABLE_INGRESOS_Y_GASTOS + "(" + IG_ID + 
			" integer primary key autoincrement, " + CONCEPTO + " text not null, "
			+ FECHA + " integer, " + INGRESO_GASTO + " text not null, "+
			VALOR + " integer, " + ID_USUARIO + " integer)";
	
	private static final String DATABASE_CREATE = "create table " + TABLE_USERS
			+ "(" + USUARIO_ID + " integer primary key autoincrement, "
			+ USUARIO_NOMBRE + " text not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println(context);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL(CREATE_TABLE_USUARIOS);
		database.execSQL(CREATE_TABLE_INGRESOS_Y_GASTOS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		onCreate(db);

	}

}
