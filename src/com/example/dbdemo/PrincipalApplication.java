package com.example.dbdemo;


import android.app.Application;

import com.example.dbdemo.db.ArticleDatasource;
import com.example.dbdemo.db.CategorieDatasource;
import com.example.dbdemo.db.DBDemoOpenHelper;

public class PrincipalApplication extends Application {

	public static PrincipalApplication INSTANCE;
	private CategorieDatasource categorie_datasource;
	private ArticleDatasource article_datasource;
	private DBDemoOpenHelper dbhelper;



	public DBDemoOpenHelper getDbhelper() {
		return dbhelper;
	}


	public void setDbhelper(DBDemoOpenHelper dbhelper) {
		this.dbhelper = dbhelper;
	}

	public ArticleDatasource getArticledatasource() {
		return article_datasource;
	}
	

	public CategorieDatasource getCategorieDatasource() {
		return categorie_datasource;
	}

	private void setCategorieDatasource(CategorieDatasource categorie_datasource) {
		this.categorie_datasource = categorie_datasource;
		this.setArticledatasource(new ArticleDatasource(categorie_datasource.getCategories(), this.getDbhelper()));
		
	}

	private void setArticledatasource(ArticleDatasource article_datasource) {
		this.article_datasource = article_datasource;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		PrincipalApplication instance = new PrincipalApplication();
		PrincipalApplication.INSTANCE =  instance;
		instance.setDbhelper(new DBDemoOpenHelper(this.getApplicationContext(), DBDemoOpenHelper.DB_NAME, null, DBDemoOpenHelper.DB_VERSION));
		instance.setCategorieDatasource(new CategorieDatasource(instance.getDbhelper()));
		
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
