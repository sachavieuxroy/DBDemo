package com.example.dbdemo.model;

import java.io.Serializable;

import com.example.dbdemo.db.DBDemoOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Article implements Parcelable {
	
	public static final int ARTICLE_DESCRIPTION = 1;
	private Long id = 0l;
	private Long categorie_id = 0l;
	private String nom = "";
	public static final String ARTICLE_EXTRA = "article";

	public Intent createIntent(Activity ctx, Class<?> cls,Categorie c){
		Intent i = new Intent(ctx,cls);
		i.putExtra(ARTICLE_EXTRA, this);
		i.putExtra(Categorie.CATEGORIE_EXTRA, c);
		
		Articles.setCurrentArticle(this);
		
		return i;
	}

	public static Article getIntentArticle(Activity ctx){
		Categorie c = (Categorie) ctx.getIntent().getExtras()
				.getParcelable(Categorie.CATEGORIE_EXTRA);
		Article a = (Article) ctx.getIntent().getExtras()
				.getParcelable(ARTICLE_EXTRA);
		return Articles.getCurrentArticlesMatchingArticle(c, a);

//		return Articles.getCurrentArticle();
	}

	
	public Article(Cursor cursor) {
		cursor.moveToFirst();
		this.id = cursor.getLong(DBDemoOpenHelper.ARTICLE_ID_COLUMN_ID);
		this.categorie_id = cursor.getLong(DBDemoOpenHelper.ARTICLE_CATEGORIEID_COLUMN_ID);
		this.nom = cursor.getString(DBDemoOpenHelper.ARTICLE_NOM_COLUMN_ID);
	}

	
//region Parceable		
	public Article(Parcel in) {
		id = in.readLong();
		categorie_id = in.readLong();
		nom = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.categorie_id);
		dest.writeString(this.nom);
	}	
	
	
	@Override
	public int describeContents() {
		return ARTICLE_DESCRIPTION;
	}
	
	public static final Creator<Article> CREATOR = 
			new Creator<Article>(){

				@Override
				public Article createFromParcel(Parcel source) {
					return new Article(source);
				}

				@Override
				public Article[] newArray(int size) {
					return new Article[size];
				}};	
	
//endregion
				public Article(Long id,Long categorie_id,String nom) {
					this.id = id;
					this.categorie_id = categorie_id;
					this.nom = nom;
				}
				
				public static Article createArticle(Long category_id,String nom){
					Long id = Articles.getArticlesMaxId() + 1;
					return createArticle(id, category_id, nom);
				}				
				
				public static Article createArticle(Long id,Long category_id,String nom){
					Article a = new Article(id,category_id,nom);
					Articles.addArticle(category_id,a);
					return a;
				}				

				public Long getId() {
					return id;
				}

				public void setId(Long id) {
					this.id = id;
				}

				public Long getCategorie_id() {
					return categorie_id;
				}

				public void setCategorie_id(Long categorie_id) {
					this.categorie_id = categorie_id;
				}

				public String getNom() {
					return nom;
				}

				public void setNom(String nom) {
					this.nom = nom;
				}

				public void copyValues(Article a) {
					this.setCategorie_id(a.getCategorie_id());
					this.setNom(a.getNom());
				}
				
				@Override
				public boolean equals(Object obj) {
					if (this == obj) {
						return true;
					}
					if (obj == null) {
						return false;
					}
					if (!(obj instanceof Article)) {
						return false;
					}
					Article other = (Article) obj;
					if (id == null) {
						if (other.id != null) {
							return false;
						}
					} else if (!id.equals(other.id)) {
						return false;
					}
					return true;
				}
				
				@Override
				public String toString() {
					return nom;
				}
				
}
