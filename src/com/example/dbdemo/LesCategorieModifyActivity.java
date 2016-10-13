package com.example.dbdemo;

import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.Categories;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LesCategorieModifyActivity extends Activity {
	private Categorie categorie;
	private TextView nom_de_la_categorie_tbx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.les_categorie_modify_activity);

		categorie = Categorie.getIntentCategorie(this);
		
		nom_de_la_categorie_tbx = (TextView) findViewById(R.id.nom_de_la_categorie_tbx);
		nom_de_la_categorie_tbx.setText(categorie.getNom());
	}

	public void SauveGarderButtonClick(View view) {
		categorie.setNom(nom_de_la_categorie_tbx.getText().toString());
		Categories.getDatasource().modify(categorie);
		setResult(RESULT_OK);
		finish();	
	}

	public void DeleteButtonClick(View view) {
		Categories.getDatasource().remove(categorie);
		setResult(RESULT_OK);
		finish();	
	}
	
	public void AnnuleButtonClick(View view) {
		setResult(RESULT_CANCELED);
		finish();	
	}	
}
