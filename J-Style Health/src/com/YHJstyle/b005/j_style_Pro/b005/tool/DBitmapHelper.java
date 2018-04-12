package com.YHJstyle.b005.j_style_Pro.b005.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;

public class DBitmapHelper extends SQLiteOpenHelper {
	
	public DBitmapHelper(Context context) {
		super(context,"bitmapmeter.db",null,1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
//		Log.v("=====", "create tables..");
		createtab(db);
	}

	private void createtab(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS bitmaptbl(_id integer primary key autoincrement,name varchar(10),year integer,month integer ,day integer , obj BLOD)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS bitmaptbl");
		onCreate(db);
	}

}
