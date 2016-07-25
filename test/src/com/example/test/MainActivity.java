package com.example.test;

import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.test.reportDBHP.result_DB;
import static com.example.test.reportDBHP.suggest_DB;
import static com.example.test.reportDBHP.info_table;

@SuppressLint("ShowToast")
public class MainActivity extends Activity implements OnClickListener {  
	private static final String Pref_HEIGHT = "BMI_Height";
	private TextView result, suggest;
	private EditText fieldheight, fieldweight;
	private Button btn1, btn2;
	private reportDBHP reportDatabase = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/** report database construction **/
		reportDatabase = new reportDBHP(getBaseContext());
		reportDatabase.getReadableDatabase();
		reportDatabase.close();
		
		findviews();
		setLinsteners();
		restorePrefs();
		
		
	}
	
	private void findviews() {
		btn1 = (Button) findViewById(R.id.submit);
		btn2 = (Button) findViewById(R.id.report_url);
		result = (TextView) findViewById(R.id.result);
		suggest = (TextView) findViewById(R.id.suggest);
		fieldheight = (EditText) findViewById(R.id.height);
		fieldweight = (EditText) findViewById(R.id.weight);

	}

	/**Set Linstener**/
	private void setLinsteners() {
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
	}
	
	/**Button work**/
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.submit:
				DecimalFormat nf = new DecimalFormat("0.00");
				try {
					double height = Double.parseDouble(fieldheight.getText().toString())/100;
					double weight = Double.parseDouble(fieldweight.getText().toString());
					double BMI = weight/(height*height);
					
					result.setText("Your BMI is "+nf.format(BMI));
					if(BMI > 25) {
						suggest.setText(R.string.advice_heavy);
					} else if(BMI < 20) {
						suggest.setText(R.string.advice_light);
					} else {
						suggest.setText(R.string.advice_average);
					}
					
					String getResult = result.getText().toString();
					String getSuggst = suggest.getText().toString();
					
					insert(getResult, getSuggst);
					
				} catch(Exception obj) {
					Toast.makeText(this, "Number only", Toast.LENGTH_LONG).show();
					openDialog();
				}
			break;
			
			case R.id.report_url:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Report.class);
				startActivity(intent);
			break;
			
		}
	}

	private void openDialog() {
		new AlertDialog.Builder(this)
		.setTitle(R.string.dialog_title)
		.setMessage(R.string.dialog_massage)
		.setPositiveButton(R.string.dialog_button,
		new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		})
		.show();
	}
	
	/**Set Menu**/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.android_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_exit:
	        finish();
	        return true;
	    case R.id.action_settings:
	        Uri homepage_url = Uri.parse("http://homu.komica.org/00/src/1468177680725.gif");
	        Intent intent = new Intent(Intent.ACTION_VIEW, homepage_url);
	        startActivity(intent);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	private void restorePrefs() {
		SharedPreferences settings = getSharedPreferences(Pref_HEIGHT, 0);
		String pref_height = settings.getString(Pref_HEIGHT, "");
		if(pref_height!="") {
			fieldheight.setText(pref_height);
			fieldweight.requestFocus();
		}
	}
	
	protected void onStop() {
		super.onStop();
		SharedPreferences settings = getSharedPreferences(Pref_HEIGHT, 0);
		settings.edit().putString(Pref_HEIGHT, fieldheight.getText().toString()).commit();
		
	}
	
	private void insert(String result, String suggest) {
		SQLiteDatabase inserDB = reportDatabase.getWritableDatabase();
		ContentValues value = new ContentValues();
		value.put(result_DB, result);
		value.put(suggest_DB, suggest);
		inserDB.insert(info_table, null, value);
		
		Log.v("TAG", "insert");
		
	}
	
}
