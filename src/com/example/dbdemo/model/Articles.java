package com.example.dbdemo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.example.dbdemo.PrincipalApplication;
import com.example.dbdemo.db.ArticleDatasource;
import com.example.dbdemo.db.CategorieDatasource;
import com.example.dbdemo.db.DBDemoOpenHelper;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Articles extends ArrayList<Article> implements Parcelable {

	public static final int ARTICLES_DESCRIPTION = 2;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3564156653113984975L;

	public Articles() {
	}

	public Articles(Collection<? extends Article> collection) {
		super(collection);
	}

	public Articles(int capacity) {
		super(capacity);
	}

	// region parceable

	public Articles(Parcel in) {
		int description = in.readInt();
		ArrayList<Article> categories = in.readArrayList(Article.class.getClassLoader());
		super.addAll(categories);
	}

	public Articles(Cursor cursor) {
		Article a;
		if (cursor.getCount()>0) {
			while (cursor.moveToNext()) {
				a = new Article(
						cursor.getLong(DBDemoOpenHelper.ARTICLE_ID_COLUMN_ID),
						cursor.getLong(DBDemoOpenHelper.ARTICLE_CATEGORIEID_COLUMN_ID),
						cursor.getString(DBDemoOpenHelper.ARTICLE_NOM_COLUMN_ID)
						);
				this.add(a);
			}
		}
	}

	@Override
	public int describeContents() {
		return ARTICLES_DESCRIPTION;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.describeContents());
		dest.writeList(this);
		
	}
	
	public static final Creator<Articles> CREATOR = 
			new Creator<Articles>(){

				@Override
				public Articles createFromParcel(Parcel in) {
					return new Articles(in);
				}

				@Override
				public Articles[] newArray(int size) {
					return new Articles[size];
				}};	

	// endregion parceable

	public Long getMaxId() {
		ArrayList<Long> ids = new ArrayList<>();
		for (Article article : this) {
			ids.add(article.getId());
		}
		return Collections.max(ids);
	}
	
	public Article getArticleWithSameId(Article a) {
		for (Article article : this) {
			if(article.equals(a)) return article;
		}
		return null;
	}

	public static Article getCurrentArticle() {
		return getDatasource().getCurrentArticle();

	}
	
	public static void setCurrentArticle(Article a){
		getDatasource().setCurrentArticle(a);
	}

	public static Articles getCurrentArticles() {
		return Categories.getCurrentCategorie().getArticles();
	}
	
	public static void modifyArticle(Categorie c,Article a) {
		Article article = Articles.getCurrentArticlesMatchingArticle(c,a);
		if (article != null)article.copyValues(a);

	}

	public Article getArticleWithSameId(Categorie c,Article a) {
		return getArticleById(c.getId(),a.getId());
	}
	
	public Article getArticleById(Long categorie_id,Long article_id){
		Categorie categorie = Categories.GetCategorieById(categorie_id);
		for (Article article : categorie.getArticles()) {
			if(article.getId().equals(article_id)) return article;
		}
		return null;		
	}
	
	public static ArticleDatasource getDatasource() {
		return PrincipalApplication.INSTANCE.getArticledatasource();
	}

	public static Articles getCurrentArticles(Categorie c) {
		return Categories.getCurrentCategoriesMatchingCategory(c).getArticles();
	}

	public static Article getCurrentArticlesMatchingArticle(Categorie c,Article a) {
		return getCurrentArticles(c).getArticleWithSameId(a);
	}

	public static Articles getAll(){
		Articles articles = new Articles();
		for (Categorie categorie : Categories.getCurrentCategories()) {
			articles.addAll(categorie.getArticles());
		}
		return articles;		
	}
	
	public static Long getArticlesMaxId() {
		return Articles.getAll().getMaxId();
	}

	public static Article GetArticleById(Long categorie_id,Long article_id){
		return getCurrentArticles().getArticleById(categorie_id, article_id);
	}

	public static void addArticle(Long category_id,Article a) {
		
		a.setCategorie_id(category_id);
		Categorie c = Categories.getDatasource().getCategorieById(category_id);
		Articles.getCurrentArticles(c).add(a);
		
	}

	public static void removeArticle(Long category_id,Article a) {
		Categorie c = Categories.getDatasource().getCategorieById(category_id);
		Articles.getCurrentArticles(c).remove(a);		
	}

}
