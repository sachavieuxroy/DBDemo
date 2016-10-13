package com.example.dbdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dbdemo.db.CategoriesProvider;
import com.example.dbdemo.db.DBDemoOpenHelper;
import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.Categories;

import java.util.Date;

public class MainActivity extends Activity {

	public static final String LIST_MODE = "ListMode";
	public static final String CATEGORIE_INSERT_URI = CategoriesProvider.contentUri + "/categories";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}

	
	public void LesCategorieClick(View view) {
		Intent i = new Intent(this, LesCategorieActivity.class); //TODO  Replace 'ActivityToCall' with the class name of the activity being called
		i.putExtra(LIST_MODE, LesCategorieActivity.ListMode.ShowCategoryList);
		startActivity(i);	
	}
	
	public void LesArticlesPourCategorieClick(View view) {
		Intent i = new Intent(this, LesCategorieActivity.class);
		i.putExtra(LIST_MODE, LesCategorieActivity.ListMode.ShowArticleListForCategory);
		startActivity(i);	
	}


	private void recupRecord() {
		Cursor cursor=getContentResolver().query(Uri.parse(CategoriesProvider.contentUri+"/categories"), new String[] {DBDemoOpenHelper.CATEGORIE_ID_COLUMN,DBDemoOpenHelper.CATEGORIE_NOM_COLUMN}, null, null, null);

		Categories categories = new Categories(cursor);
		String msg = "";
		for (Categorie c:
			 categories) {
			msg += "\n" + c.getId().toString()+ " - " +c.getNom();
		}
		Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
	}

	public void recuprecordclick(View view) {
		recupRecord();
	}

	private void insertRecords(){
		Uri result;
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(DBDemoOpenHelper.CATEGORIE_NOM_COLUMN, "Insert test 1 - " + String.valueOf((new Date()).getTime()));
		result = getContentResolver().insert(Uri.parse(CATEGORIE_INSERT_URI), contentvalues);

		Toast.makeText(this, "Insert: " + result, Toast.LENGTH_SHORT).show();


	}

	public void insertbuttonclick(View view) {
//		Toast.makeText(this, "insertbuttonclick", Toast.LENGTH_SHORT).show();
		insertRecords();
	}

	private void deleteRecords(){
		String delete_uri = CATEGORIE_INSERT_URI + "/Insert test";
		int result = getContentResolver().delete(Uri.parse(delete_uri),null,null);
		String msg;
		msg = "Delete uri: " + delete_uri + "\nresult: " + result;
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	public void deletebuttonclick(View view) {
		deleteRecords();
	}

	private void updateRecords(){
		String delete_uri = CATEGORIE_INSERT_URI + "/Insert test";
		ContentValues values=new ContentValues();
		values.put(DBDemoOpenHelper.CATEGORIE_NOM_COLUMN,  "NEW Instert Test");
		getContentResolver().update(Uri.parse(CATEGORIE_INSERT_URI), values, null, null);
	}

	public void updatebuttonclick(View view) {
		updateRecords();
	}
}
