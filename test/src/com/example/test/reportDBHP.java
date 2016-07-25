package com.example.test;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class reportDBHP extends SQLiteOpenHelper {
	
	public static final int DB_Version = 1;
	public static final String DB_Name = "myDatabase.db";
	public static final String info_table = "report_table";
	public static final String row_id = "id";
	public static final String result_DB = "result_DB";
	public static final String suggest_DB = "suggst_DB";

	public reportDBHP(Context context) {
		super(context, DB_Name, null, DB_Version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create_table = "CREATE TABLE "+info_table+" ("+
				row_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				result_DB+" TEXT, "+
				suggest_DB+" TEXT)";
		
		try {
			  db.execSQL(create_table);
			  Log.v("success", "database has be created");
			} catch (SQLException e) {
			  Log.v("error", "database not create");
			}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
