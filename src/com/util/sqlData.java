package com.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlData extends SQLiteOpenHelper {
	
  static final String TABLE_LDATA = "ldata";
  static final String COLUMN_ID = "_id";
  static final String COLUMN_LAT = "latitude";
  static final String COLUMN_LON = "longitude";
  static final String COLUMN_DATE = "date";
  static final String DATABASE_LDATA = "ldata.db";
  static final int    DATABASE_VERSION = 1;
  private static final String DATABASE_CREATE_STATMENT = "CREATE TABLE IF NOT EXISTS ldata(_id INTEGER PRIMARY KEY AUTOINCREMENT, latitude TEXT NOT NULL,longitude TEXT NOT NULL, date TEXT NOT NULL );";

	public sqlData( Context context ) {
		super(context, DATABASE_LDATA, null, 1);
		// TODO Auto-generated constructor stub
	}
	public Cursor getCursor() throws SQLException, Exception
	{
	    Cursor cursor = getReadableDatabase().query( TABLE_LDATA, null, null, null, null, null, null);
	    return cursor;
	}
	public long writeData(String s1, String s2, String s3) throws Exception {
	    ContentValues cv = new ContentValues();
	    cv.put("latitude", s1);
		cv.put("longitude", s2);
		cv.put("date", s3);

		Long l1 = Long.valueOf(getWritableDatabase().insert(TABLE_LDATA, null, cv));
		return l1.longValue();
	}
	public void onCreate(SQLiteDatabase db) throws SQLException
	{
	    db.execSQL(DATABASE_CREATE_STATMENT);
	}
	int deleteData() throws NullPointerException, SQLException {
		int i = getWritableDatabase().delete(TABLE_LDATA, null, null);
	    return i;
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS ldata");
		onCreate(db);
	}

	public SQLiteDatabase getReadableDatabase()
	{
		return super.getReadableDatabase();
	}

	public SQLiteDatabase getWritableDatabase()
	{
		return super.getWritableDatabase();
	}
}
