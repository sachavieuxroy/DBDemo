package com.example.dbdemo;

import com.example.dbdemo.db.CategorieDatasource;
import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.Categories;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LesCategorieAddActivity extends Activity {

	private TextView nom_de_la_categorie_tbx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.les_categorie_add_activity);
		nom_de_la_categorie_tbx = (TextView) findViewById(R.id.nom_de_la_categorie_tbx);
		
	}

	public void AjouteButtonClick(View view) {
//		CategorieDatasource ds = PrincipalApplication.INSTANCE.getCategorieDatasource();
//		
//		ds.add(
//				new Categorie(ds.getMaxId(),nom_de_la_categorie_tbx.getText().toString())
//				);
//		
		Categories.getDatasource().create(nom_de_la_categorie_tbx.getText().toString());
		setResult(RESULT_OK);
		finish();

	}
}
