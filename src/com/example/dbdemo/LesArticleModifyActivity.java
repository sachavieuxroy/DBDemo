package com.example.dbdemo;

import com.example.dbdemo.model.Article;
import com.example.dbdemo.model.Articles;
import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.Categories;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LesArticleModifyActivity extends Activity {
	private Categorie categorie;
	private Article article;
	private TextView nom_de_la_article_tbx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.les_article_modify_activity);

		categorie = Categorie.getIntentCategorie(this);
		article = Article.getIntentArticle(this);
		
		nom_de_la_article_tbx = (TextView) findViewById(R.id.nom_de_la_article_tbx);
		nom_de_la_article_tbx.setText(article.getNom());
	}

	public void SauveGarderButtonClick(View view) {
		article.setNom(nom_de_la_article_tbx.getText().toString());
		Articles.getDatasource().modify(categorie, article);
		setResult(RESULT_OK);
		finish();	
	}	

	public void DeleteButtonClick(View view) {
		Articles.getDatasource().remove(categorie.getId(), article);
		setResult(RESULT_OK);
		finish();	
	}

	public void AnnuleButtonClick(View view) {
		setResult(RESULT_CANCELED);
		finish();	
	}	
}
