package conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {
	
   private static String BD_NOMBRE = "CumlaudeAndroid";
   private static final int DATABASE_VERSION = 1;
      
   private static DBHelper dbhelper = null;
   
   public static DBHelper getInstance(Context context, String name){
      if(dbhelper == null){
         dbhelper = new DBHelper(context, name);
      }
      return dbhelper;      
   }
   
   private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, BD_NOMBRE, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
           /*db.execSQL("CREATE TABLE IF NOT EXISTS "
               + BD_TABLA + " (nombre VARCHAR, apellido VARCHAR,"
               + " pais VARCHAR, edad INT(3));");*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
   
   private DatabaseHelper mOpenHelper;
   
   private DBHelper(Context context, String name){
	  BD_NOMBRE = name;
      mOpenHelper = new DatabaseHelper(context);
   }
   
   public SQLiteDatabase getBD(){
	   return mOpenHelper.getWritableDatabase();
   }
   
   //Se usaran luego
   public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
      SQLiteDatabase db = mOpenHelper.getWritableDatabase();
   
      return db.update(table, values, whereClause, whereArgs);
   }
   
   public int delete(String table, String whereClause, String[] whereArgs){
      SQLiteDatabase db = mOpenHelper.getWritableDatabase();
   
      return db.delete(table, whereClause, whereArgs);
   }
   
   public long insert(String table, String nullColumnHack, ContentValues values){
      SQLiteDatabase db = mOpenHelper.getWritableDatabase();
      
      return db.insert(table, nullColumnHack, values);
   }
   
   public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
      SQLiteDatabase db = mOpenHelper.getReadableDatabase();
      
      return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
   }
}
