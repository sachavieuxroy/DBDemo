package com.example.dbdemo.model;

import java.util.ArrayList;

import com.example.dbdemo.PrincipalApplication;

public class CategorieData {
	public static CategorieData INSTANCE = new CategorieData(); 
	private ArrayList<Categorie> sampledata;
	public Categories getSampledata() {
		Categories c = new Categories();
		c.addAll(sampledata);
		return c;
	}
	public CategorieData() {
		String user;
	    sampledata = new ArrayList<>();
	    Categorie c;
	    Long jc = 1l;
		for (Long i = 0l; i < 20l; i++) {
			user = "Categorie " + i;
			c = new Categorie(i,user);
			for (Long j = 1l; j < 5l; j++) {
				c.getArticles().add(new Article(jc++,i,"Article - " + i + " - " + j));
			}
			sampledata.add(c);
		}
	}

}
