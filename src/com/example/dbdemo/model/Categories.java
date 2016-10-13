package com.example.dbdemo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.example.dbdemo.PrincipalApplication;
import com.example.dbdemo.db.CategorieDatasource;
import com.example.dbdemo.db.DBDemoOpenHelper;

import android.database.Cursor;

public class Categories extends ArrayList<Categorie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -170620632263307907L;

	public Categories() {
	}

	public Categories(int capacity) {
		super(capacity);
	}
	
	public Categories(Cursor cursor){
		Categorie c;
		if (cursor.getCount()>0) {
			
			
			while (cursor.moveToNext()) {
				c = new Categorie(
						cursor.getLong(DBDemoOpenHelper.CATEGORIE_ID_COLUMN_ID),
						cursor.getString(DBDemoOpenHelper.CATEGORIE_NOM_COLUMN_ID)
						);
				
				Articles a = PrincipalApplication.INSTANCE.getDbhelper().getArticles(c);
				c.getArticles().addAll(a);
				
				this.add(c);
			}
		}
	}

	public Categories(Collection<? extends Categorie> collection) {
		super(collection);
	}
	
	public Categorie getCategorieWithSameId(Categorie c) {
		return getCategorieById(c.getId());
	}
	
	public Categorie getCategorieById(Long id){
		for (Categorie categorie : this) {
			if(categorie.getId().equals(id)) return categorie;
		}
		return null;		
	}
	
	
	public void modify(Categorie c) {
		Categorie categorie = this.getCategorieWithSameId(c);
		if (categorie != null)categorie.copyValues(c);

	}
	
	public Long getMaxId() {
		ArrayList<Long> ids = new ArrayList<>();
		for (Categorie categorie : this) {
			ids.add(categorie.getId());
		}
		return Collections.max(ids);
	}
	

	public static Categorie getCurrentCategorie() {
		return getDatasource().getCurrentCategorie();

	}
	
	public static void setCurrentCategorie(Categorie c){
		getDatasource().setCurrentCategorie(c);
	}
	
	public static Categories getCurrentCategories() {
		return getDatasource().getCategories();
	}
	
	public static Categorie getCurrentCategoriesMatchingCategory(Categorie c) {
		return getCurrentCategories().getCategorieWithSameId(c);
	}
	
	public static CategorieDatasource getDatasource() {
		return PrincipalApplication.INSTANCE.getCategorieDatasource();
	}
	
	
	public static Long getCategoriesMaxId() {
		return getCurrentCategories().getMaxId();
	}
	
	public static void addCategorie(Categorie c){
		getCurrentCategories().add(c);
	}
	
	public static void removeCategorie(Categorie c){
		getCurrentCategories().remove(c);
	}
	
	public static Categorie GetCategorieById(Long id){
		return getCurrentCategories().getCategorieById(id);
	}
	
	public static Categories findAllCategories(){
		return getCurrentCategories();
	}

	public static void modifyCategorie(Categorie c){
		getCurrentCategories().modify(c);
	}
}
