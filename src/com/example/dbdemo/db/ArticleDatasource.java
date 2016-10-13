package com.example.dbdemo.db;

import com.example.dbdemo.PrincipalApplication;
import com.example.dbdemo.model.Article;
import com.example.dbdemo.model.Articles;
import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.Categories;

public class ArticleDatasource {
	private Categories categories;
	private Article currentArticle;
	private DBDemoOpenHelper dbhelper;

	public Article getCurrentArticle() {
		return currentArticle;
	}

	public void setCurrentArticle(Article currentArticle) {
		this.currentArticle = currentArticle;
	}
	
	public ArticleDatasource(Categories categories,DBDemoOpenHelper helper) {
		super();
		this.categories = categories;
		this.dbhelper = helper;
	}

	public Long getMaxId() {
		return Articles.getArticlesMaxId();
	}
	
	public Articles getArticles(Categorie c) {
		//return Articles.getCurrentArticles(c);
		return PrincipalApplication.INSTANCE.getDbhelper().getArticles(c);
	}
	
	public static Article getArticleById(Long categorie_id,Long article_id){
		//return Articles.GetArticleById(categorie_id,article_id);
		return PrincipalApplication.INSTANCE.getDbhelper().getArticleById(categorie_id, article_id);
	}

	public void modify(Categorie c,Article a) {
		//Articles.modifyArticle(c, a);
		PrincipalApplication.INSTANCE.getDbhelper().modifyArticle(c, a);
	}
	
	public void create(Long category_id,String nom) {
		//Article.createArticle(category_id,nom);
		PrincipalApplication.INSTANCE.getDbhelper().createArticle(category_id, nom);

	}
	
	public void remove(Long category_id,Article a) {
		//Articles.removeArticle(category_id,a);
		PrincipalApplication.INSTANCE.getDbhelper().removeArticle(category_id, a);
	}	

}
