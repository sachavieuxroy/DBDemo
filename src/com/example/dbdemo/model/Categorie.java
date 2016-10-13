package com.example.dbdemo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import com.example.dbdemo.db.DBDemoOpenHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Categorie implements Parcelable {
	public static final int CATEGORY_DESCRIPTION = 0;
	private Long id = 0l;
	private String nom = "";
	private Articles articles = new Articles();
	public static final String CATEGORIE_EXTRA = "categorie";
	
	public Intent createIntent(Activity ctx, Class<?> cls){
		Intent i = new Intent(ctx,cls);
		i.putExtra(CATEGORIE_EXTRA, this);
		
		Categories.setCurrentCategorie(this);
		
		return i;
	}

	public static Categorie getIntentCategorie(Activity ctx){
		Categorie c = (Categorie) ctx.getIntent().getExtras().getParcelable(CATEGORIE_EXTRA);
		return Categories.getCurrentCategoriesMatchingCategory(c);

//		return Categories.getCurrentCategorie();
	}

	
	
	
//region Parceable	
	public Categorie(Parcel in) {
		id = in.readLong();
		nom = in.readString();
		articles.addAll((ArrayList<Article>) in.readArrayList(Article.class.getClassLoader()));
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.nom);
		dest.writeList((ArrayList<Article>)articles);
	}	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return CATEGORY_DESCRIPTION;
	}
	
	public static final Creator<Categorie> CREATOR = 
			new Creator<Categorie>(){

				@Override
				public Categorie createFromParcel(Parcel in) {
					return new Categorie(in);
				}

				@Override
				public Categorie[] newArray(int size) {
					return new Categorie[size];
				}};	
				
//endregion

	public Categorie(Long id,String nom,Articles articles) {
		this.id = id;
		this.nom = nom;
		if(articles != null)this.articles = articles;
		
	}
				
	public Categorie(Long id,String nom) {
		this.id = id;
		this.nom = nom;
	}
	
	public Categorie(Cursor cursor) {
		cursor.moveToFirst();
		this.id = cursor.getLong(DBDemoOpenHelper.CATEGORIE_ID_COLUMN_ID);
		this.nom = cursor.getString(DBDemoOpenHelper.CATEGORIE_NOM_COLUMN_ID);
	}

	public static Categorie createCategorie(String nom){
		Long id = Categories.getCategoriesMaxId() + 1;
		Categorie c = createCategorie(id, nom);
		return c;
	}
	
	public static Categorie createCategorie(Long id,String nom){
		Categorie c = new Categorie(id,nom);
		Categories.addCategorie(c);
		return c;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Articles getArticles() {
		return articles;
	}

	public void copyValues(Categorie c) {
		this.setNom(c.getNom());

	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Categorie)) {
			return false;
		}
		Categorie other = (Categorie) obj;
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
