package com.example.dbdemo;

import com.example.dbdemo.R;
import com.example.dbdemo.R.id;
import com.example.dbdemo.R.layout;
import com.example.dbdemo.db.ArticleDatasource;
import com.example.dbdemo.db.CategorieDatasource;
import com.example.dbdemo.model.Article;
import com.example.dbdemo.model.ArticleAdapter;
import com.example.dbdemo.model.Articles;
import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.CategorieAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

	public class LesArticlePourCategorieActivity extends ListActivity {
		
	public static final int REQUEST_ADD_ARTICLE = 101;
	public static final int REQUEST_MODIFY_ARTICLE = 102;
	private ArticleAdapter adapter;
	private TextView categoryTextView;
	private Categorie categorie;
	private Article article;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.les_article_pour_category);
		
		categorie = Categorie.getIntentCategorie(this);

		categoryTextView = (TextView) findViewById(R.id.categoryTextView);
		categoryTextView.setText(categorie.getNom());
		
		adapter = new ArticleAdapter(this,categorie.getArticles());
		setListAdapter(adapter);
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		article = adapter.getItem(position);
		
		startActivityForResult(article.createIntent(this, 
				LesArticleModifyActivity.class, categorie), REQUEST_MODIFY_ARTICLE);
		
	}

	public void LesArticleAddButtonClick(View view) {
		startActivityForResult(categorie.createIntent(this, 
				LesArticleAddActivity.class), REQUEST_ADD_ARTICLE);

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			adapter.notifyDataSetChanged(this.categorie);
		}
	}

}
