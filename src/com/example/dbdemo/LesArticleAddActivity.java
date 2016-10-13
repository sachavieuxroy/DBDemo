package com.example.dbdemo;

import com.example.dbdemo.model.Articles;
import com.example.dbdemo.model.Categorie;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LesArticleAddActivity extends Activity {

	private Categorie categorie;
	private TextView nom_de_la_article_tbx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.les_article_add_activity);

		
		categorie = Categorie.getIntentCategorie(this);
		nom_de_la_article_tbx = (TextView) findViewById(R.id.nom_de_la_article_tbx);
		
	}

	public void AjouteButtonClick(View view) {		
		Articles.getDatasource().create(this.categorie.getId(), 
				nom_de_la_article_tbx.getText().toString());		
		setResult(RESULT_OK);
		finish();
	}

}
