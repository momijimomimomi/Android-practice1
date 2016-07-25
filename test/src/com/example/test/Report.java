package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import static com.example.test.reportDBHP.info_table;
import static com.example.test.reportDBHP.row_id;
import static com.example.test.reportDBHP.result_DB;
import static com.example.test.reportDBHP.suggest_DB;

public class Report extends Activity {
	public String [] columns = {row_id, result_DB, suggest_DB};
	private ListView listview;
	private ArrayList<String> list = new ArrayList<String>();
	private ArrayAdapter<String> reportAdapter;
	
	/** report database constructor **/
	private reportDBHP reportDatabase = null;
	
	/**called when Activity is first start**/
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report);
		
		/** report database implement **/
		reportDatabase = new reportDBHP(this);
		reportDatabase.close();
		
		findviews();
		showDBdata();
		setAdapter();
		setLinsteners();
	
	}

	private Cursor getCursor() {
		SQLiteDatabase showDB = reportDatabase.getReadableDatabase();
		Cursor cursor = showDB.query(info_table, columns, null, null, null, null, null);
		return cursor;
		
	}

	private void findviews() {
		listview = (ListView) findViewById(R.id.listView1);
		
	}
	
	private void showDBdata() {
		 Cursor cursor = getCursor();
		 int rows = cursor.getCount();
		 
		 if(rows != 0) {
			 cursor.moveToFirst();
			 while(cursor.moveToNext()) {
				 String result = cursor.getString(1);
				 String suggest = cursor.getString(2);
				 
				 list.add(result);
			 }
		 }
		
	}
	
	private void setAdapter() {
		reportAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, list);
		listview.setAdapter(reportAdapter);
	}
	
	private void setLinsteners() {
		
		
		
	}
	
}
