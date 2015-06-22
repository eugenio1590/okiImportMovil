package conexion;

import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConexionDAO implements IConexionDAO{
	
	private DBHelper usdbh;
	private static ConexionDAO conex;
	
	private SQLiteDatabase db;
	private Cursor cursor;

	private ConexionDAO(Context cont) {
		super();
		usdbh = DBHelper.getInstance(cont, "CumlaudeAndroid");
		db=usdbh.getBD();
	}
	
	public static ConexionDAO getInstance(Context cont)
	{
		if(conex==null)
			return conex=new ConexionDAO(cont);
		else
			return conex;
	}
	
	@Override
	public void consultar(boolean distinct, String table, String[] columns,
			String criterio, String[] valores_criterio, String groupBy,
			String having, String orderBy, String limit) {
		// TODO Auto-generated method stub
		cursor = db.query(distinct, table, columns, criterio, valores_criterio, groupBy, having, orderBy, limit);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Vector<?> extraerValor(int position) {
		// TODO Auto-generated method stub
		Vector<String> vector = new Vector<String>();
		if(cursor.moveToPosition(position)){
			for(int i=0; i<cursor.getColumnCount(); i++)
				vector.add(cursor.getString(i));
		}
		return vector;
	}

	@Override
	public String extraerValor(String columna, int position) {
		// TODO Auto-generated method stub
		if(cursor!=null){
			int index = cursor.getColumnIndex(columna);
			if(cursor.moveToPosition(position)){
				return cursor.getString(index);
			}
		}
		return null;
	}
	
	public void crearTabla(String nombre, String[] campos){
		if(db!=null){
			String sql = "CREATE TABLE IF NOT EXISTS "+nombre+" (";
			for(int i=0; i<campos.length; i++){
				if((i+1)!=campos.length)
					sql=sql+(campos[i]+",");
				else
					sql=sql+campos[i];
			}
			sql=sql+" );";
			db.execSQL(sql);
		}
	}
	
	public void borrarTabla(String tabla){
		if(db!=null)
			db.execSQL("DROP TABLE IF EXISTS "+tabla+" ;");
	}
	
	public long insertarRegistro(String tabla, ContentValues valores){
		if(db!=null)
			return usdbh.insert(tabla, null, valores);
		return -1;
	}
	
	public long modificarRegistro(String table, ContentValues values, String whereClause, String[] whereArgs){
		if(db!=null)
			return db.update(table, values, whereClause, whereArgs);
		return -1;
	}

	public int getCantRegistro() {
		// TODO Auto-generated method stub
		if(cursor!=null)
			return cursor.getCount();
		return 0;
	}
}
