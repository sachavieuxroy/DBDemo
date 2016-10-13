package com.example.dbdemo.db;

import com.example.dbdemo.Global;
import com.example.dbdemo.PrincipalApplication;
import com.example.dbdemo.model.Article;
import com.example.dbdemo.model.Articles;
import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.CategorieData;
import com.example.dbdemo.model.Categories;

public class CategorieDatasource {
	private Categories categories;
	private Categorie currentCategorie;
	private DBDemoOpenHelper dbhelper;

	public Categorie getCurrentCategorie() {
		return currentCategorie;
	}


	public void setCurrentCategorie(Categorie currentCategorie) {
		this.currentCategorie = currentCategorie;
	}


	public Categories getCategories() {
		if (Global.UseSQLite) {
			return PrincipalApplication.INSTANCE.getDbhelper().findAllCategories();
		}else{
			return categories;
		}
	}


	public CategorieDatasource(DBDemoOpenHelper helper) {
		if (Global.UseSQLite) {
			this.dbhelper = helper;
			this.dbhelper.open();
		} else {
			categories = CategorieData.INSTANCE.getSampledata();
		}
	}

	public Categories findAll() {
		if (Global.UseSQLite) {
			return getCategories();
		} else {
			return Categories.findAllCategories();
		}
	}
	
	public static Categorie getCategorieById(Long id) {
		if (Global.UseSQLite) {
			return PrincipalApplication.INSTANCE.getDbhelper().GetCategorieById(id);
		} else {
			return Categories.GetCategorieById(id);
		}
	}

	public void create(String nom){
		if (Global.UseSQLite) {
			PrincipalApplication.INSTANCE.getDbhelper().createCategorie(nom);
		} else {
			Categorie.createCategorie(nom);
		}
	}
	
	public void add(Categorie c) {
		Categories.addCategorie(c);
	}
	
	public void modify(Categorie c) {
		if (Global.UseSQLite) {
			PrincipalApplication.INSTANCE.getDbhelper().modifyCategorie(c);
		}else{
			Categories.modifyCategorie(c);
		}
		
	}	
	
	public void remove(Categorie c) {
		if (Global.UseSQLite) {
			PrincipalApplication.INSTANCE.getDbhelper().removeCategorie(c);
		} else {
			Categories.removeCategorie(c);
		}
	}

	public Long getMaxId() {
		return Categories.getCategoriesMaxId();
	}
	
	
}
