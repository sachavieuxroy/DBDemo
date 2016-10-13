package com.example.dbdemo;

import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.CategorieAdapter;
import com.example.dbdemo.model.Categories;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

	public class LesCategorieActivity extends ListActivity {
	public static final String CATEGORIE_EXTRA = "categorie";
	public static final int REQUEST_ADD_CATEGORIE = 101;
	public static final int REQUEST_MODIFY_CATEGORIE = 102;
	public static final int REQUEST_ARTICLES_FOR_CATEGORY = 103;
	private CategorieAdapter adapter;

	private ListMode listmode;
	private Button les_categorie_add_button;
	enum ListMode {
		ShowCategoryList,
		ShowArticleListForCategory;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.les_categorie_activity);
		listmode = (ListMode) getIntent().getExtras().get(MainActivity.LIST_MODE);
		
		les_categorie_add_button = (Button) findViewById(R.id.les_categorie_add_button);
		if(listmode == ListMode.ShowCategoryList){
			les_categorie_add_button.setVisibility(View.VISIBLE);
		}
		
		if(listmode == ListMode.ShowArticleListForCategory){
			setTitle(R.string.les_category_des_article);
		}
		
		Categories categories = PrincipalApplication.INSTANCE.getCategorieDatasource().findAll();
		adapter = new CategorieAdapter(this,categories);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Categorie categorie = adapter.getItem(position);
		
		if(listmode == ListMode.ShowCategoryList) {
			startActivityForResult(categorie.createIntent(this, LesCategorieModifyActivity.class), REQUEST_MODIFY_CATEGORIE);
		}else{
			startActivityForResult(categorie.createIntent(this, LesArticlePourCategorieActivity.class), REQUEST_ARTICLES_FOR_CATEGORY);
		}
		
	}
	
	

	public void LesCategorieAddButtonClick(View view) {
		startActivityForResult(new Intent(this,LesCategorieAddActivity.class), REQUEST_ADD_CATEGORIE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			adapter.notifyDataSetChanged();
		}
	}

}
