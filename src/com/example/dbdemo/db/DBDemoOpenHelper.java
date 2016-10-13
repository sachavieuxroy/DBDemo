package com.example.dbdemo.db;

import com.example.dbdemo.model.Article;
import com.example.dbdemo.model.Articles;
import com.example.dbdemo.model.Categorie;
import com.example.dbdemo.model.Categories;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBDemoOpenHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "dbdemo.db";
	public static final int DB_VERSION = 1;
	public static final String CATEGORIE_TABLE_NAME = "Categorie";
	public static final String CATEGORIE_ID_COLUMN = "id";
	public static final String CATEGORIE_NOM_COLUMN = "nom";
	public static final int CATEGORIE_ID_COLUMN_ID = 0;
	public static final int CATEGORIE_NOM_COLUMN_ID = 1;
	public static final String ARTICLE_TABLE_NAME = "Article";
	public static final String ARTICLE_ID_COLUMN = "id";
	public static final String ARTICLE_CATEGORIEID_COLUMN = "categorie_id";
	public static final String ARTICLE_NOM_COLUMN = "nom";
	public static final int ARTICLE_ID_COLUMN_ID = 0;
	public static final int ARTICLE_CATEGORIEID_COLUMN_ID = 1;
	public static final int ARTICLE_NOM_COLUMN_ID = 2;
	private static final String[] AllCategorieColumns = {CATEGORIE_ID_COLUMN,CATEGORIE_NOM_COLUMN};
	private static final String[] AllArticleColumns = {ARTICLE_ID_COLUMN, ARTICLE_CATEGORIEID_COLUMN,ARTICLE_NOM_COLUMN};
	private SQLiteDatabase database;

	public DBDemoOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	private static final String CREATE_CATEGORIE_TABLE = "CREATE TABLE "+ CATEGORIE_TABLE_NAME +" ("
			+ CATEGORIE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ CATEGORIE_NOM_COLUMN + " TEXT"
			+ ")";
	
	private static final String CREATE_ARTICLE_TABLE = "CREATE TABLE "+ ARTICLE_TABLE_NAME +" ("
			+ ARTICLE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ ARTICLE_CATEGORIEID_COLUMN + " INTEGER,"
			+ ARTICLE_NOM_COLUMN + " TEXT"
			+ ")";
	
	private static final String CATEGORIE_DROP_TABLE = "DROP TABLE IF EXISTS" + CATEGORIE_TABLE_NAME;

	private static final String CATEGORIE_SAMPLE_DATA_INSERT = "INSERT INTO " + CATEGORIE_TABLE_NAME + "("
			+ CATEGORIE_ID_COLUMN + "," 
			+ CATEGORIE_NOM_COLUMN + ")"
	          + "SELECT 1 AS "+CATEGORIE_ID_COLUMN+", "
	          		 + "'Categorie - 1' AS "+CATEGORIE_NOM_COLUMN+" "
	          + "UNION SELECT 2, 'Categorie - 2'"
	          + "UNION SELECT 3, 'Categorie - 3'";
	
	private static final String ARTICLE_SAMPLE_DATA_INSERT = "INSERT INTO " + ARTICLE_TABLE_NAME + "("
	+ ARTICLE_ID_COLUMN + "," 
	+ ARTICLE_CATEGORIEID_COLUMN + "," 
	+ ARTICLE_NOM_COLUMN + ")"
	          +  "SELECT 1 AS "+ARTICLE_ID_COLUMN+", "
	          +			"1 AS "+ARTICLE_CATEGORIEID_COLUMN+", "
	          +         "'Article 1 - 1' AS "+ARTICLE_NOM_COLUMN+" "
	          + "UNION SELECT 2, 1, 'Article 1 - 2'"
	          + "UNION SELECT 3, 1, 'Article 1 - 3'"
	          + "UNION SELECT 4, 2, 'Article 2 - 4'"
	          + "UNION SELECT 5, 2, 'Article 2 - 5'"
	          + "UNION SELECT 6, 2, 'Article 2 - 6'"
	          + "UNION SELECT 7, 3, 'Article 3 - 7'"
	          + "UNION SELECT 8, 3, 'Article 3 - 8'"
	          + "UNION SELECT 9, 3, 'Article 3 - 9'";


	private static final String ARTICLE_DROP_TABLE = "DROP TABLE IF EXISTS" + ARTICLE_TABLE_NAME;
	
	
	private static final String TAG = "SQLiteOpenHelper";

	public DBDemoOpenHelper(Context context, String name, CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, DB_NAME, factory, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CATEGORIE_TABLE);
		db.execSQL(CREATE_ARTICLE_TABLE);
		db.execSQL(CATEGORIE_SAMPLE_DATA_INSERT);
		db.execSQL(ARTICLE_SAMPLE_DATA_INSERT);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(ARTICLE_DROP_TABLE);
		db.execSQL(CATEGORIE_DROP_TABLE);
		onCreate(db);
	}
	
	public void open() {
		database = this.getWritableDatabase();
	}
	
	public void close() {
		database.close();
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}
	
	public Articles getArticles(Categorie c) {
        String key = String.valueOf(c.getId());
        String whereClause = String.format("%1$s = ?",ARTICLE_CATEGORIEID_COLUMN);
        String[] arg = new String[]{new String(key)};

        open();
		Cursor cursor = database.query(ARTICLE_TABLE_NAME, AllArticleColumns, whereClause, arg, null, null, null);
		if (cursor == null || cursor.getCount()==0) {
			Log.i(TAG, "getArticles: no data was retreived.");
		}else{
			Log.i(TAG, "getArticles: Successfully retreived existing data.");
		}
		return new Articles(cursor);
	}

	public Categories findAllCategories(){
		
		open();
		Cursor cursor = database.query(CATEGORIE_TABLE_NAME, AllCategorieColumns, null, null, null, null, null);
		if (cursor == null || cursor.getCount()==0) {
			Log.i(TAG, "findAllCategories: no data was retreived.");
		}else{
			Log.i(TAG, "findAllCategories: Successfully retreived existing data.");
		}
		return new Categories(cursor);
	}
	
	
	public Categorie createCategorie(String nom){
		
		open();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(CATEGORIE_NOM_COLUMN, nom);
		Long insertId = database.insert(CATEGORIE_TABLE_NAME, null, contentvalues);
		if (insertId > 0) {
			Log.i(TAG, "createCategorie: created categorie " + nom);
			return Categorie.createCategorie(insertId, nom);
		}
		Log.i(TAG, "createCategorie: FAILED !! to create categorie " + nom);
		return null;
	}
	
	public Article createArticle(Long category_id,String nom){
		
		open();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(ARTICLE_CATEGORIEID_COLUMN, category_id);
		contentvalues.put(ARTICLE_NOM_COLUMN, nom);
		Long insertId = database.insert(ARTICLE_TABLE_NAME, null, contentvalues);
		if (insertId > 0) {
			Log.i(TAG, "createArticle: created article " + nom);
			return Article.createArticle(insertId,category_id, nom);
		}
		Log.i(TAG, "createArticle: FAILED !! to create article " + nom);
		return null;
	}
	
	public void modifyCategorie(Categorie c){
		String key = String.valueOf(c.getId());
		String whereClause = String.format("%1$s = ?",CATEGORIE_ID_COLUMN);
        String[] arg = new String[]{new String(key)};
		
		open();
		ContentValues values = new ContentValues();
		values.put(CATEGORIE_NOM_COLUMN,c.getNom());		
		int result = database.update(
				CATEGORIE_TABLE_NAME,
				values,
				whereClause,
				arg);		
		if (result != 1) {
			Log.i(TAG, "modifyCategorie: Error while updating categorie.");
			throw new SQLException("Error while updating categorie.");
		}
		Log.i(TAG, "modifyCategorie: Updated categorie " + key + " successfully.");
	}

	   public void modifyArticle(Categorie c,Article a){
	        String key = String.valueOf(a.getId());
	        String whereClause = String.format("%1$s = ?",ARTICLE_ID_COLUMN);
	        String[] arg = new String[]{new String(key)};
		   
		   open();	        
	        ContentValues values = new ContentValues();
	        values.put(ARTICLE_CATEGORIEID_COLUMN,a.getCategorie_id());
	        values.put(ARTICLE_NOM_COLUMN,a.getNom());
	        int result = database.update(
	                ARTICLE_TABLE_NAME,
	                values,
	                whereClause,
	                arg);
	        if (result != 1) {
	        	Log.i(TAG, "modifyArticle: Error while updating article.");
	            throw new SQLException("Error while updating article.");
	        }
	        Log.i(TAG, "modifyArticle: Updated article " + key + " successfully.");
	    }
	   
	   public void removeCategorie(Categorie c) {
		   String key = String.valueOf(c.getId());
		   String whereClause = String.format("%1$s = ?",CATEGORIE_ID_COLUMN);
		   String[] arg = new String[]{new String(key)};
		   
		   open();
		   int result = database.delete(
				   CATEGORIE_TABLE_NAME,
				   whereClause,
				   arg);
		   if (result != 1) {
			   Log.i(TAG, "removeCategorie: Error while deleting categorie.");
			   throw new SQLException("Error while deleting categorie.");
		   }
		   Log.i(TAG, "removeCategorie: Deleted categorie " + key + " successfully.");
	   }
	
	    public void removeArticle(Long category_id,Article a) {
	        String key = String.valueOf(a.getId());
	        String whereClause = String.format("%1$s = ?",ARTICLE_ID_COLUMN);
	        String[] arg = new String[]{new String(key)};
	    	
	    	open();
	        int result = database.delete(
	                ARTICLE_TABLE_NAME,
	                whereClause,
	                arg);
	        if (result != 1) {
	            Log.i(TAG, "removeArticle: Error while deleting article.");
	            throw new SQLException("Error while deleting article.");
	        }
	        Log.i(TAG, "removeArticle: Deleted article " + key + " successfully.");
	    }
	    
	    
	    public Article getArticleById(Long categorie_id,Long article_id){
	    	String key = String.valueOf(article_id);
	    	String whereClause = String.format("%1$s = ?",ARTICLE_ID_COLUMN);
	    	String[] arg = new String[]{new String(key)};
	    	
	    	open();
	    	Cursor cursor = database.query(ARTICLE_TABLE_NAME, AllArticleColumns, 
	    			whereClause, arg, null, null, null);
	    	if (cursor == null){
	    		Log.i(TAG, "getArticleById: Error while retreiving article with id " + key);
	    		throw new SQLException("Error while retreiving article with id " + key);
	    	}
	    	
	    	if (cursor.getCount() != 1){
	    		Log.i(TAG, "getArticleById: too many or too few records found while retreiving article with id " + key);
	    		throw new SQLException("Too many or too few records found while retreiving article with id " + key);
	    	}
	    	
	    	Log.i(TAG, "Successfully retreived article with id " + key);
	    	return new Article(cursor);
	    }
	    
	    public Categorie GetCategorieById(Long id){
	        String key = String.valueOf(id);
	        String whereClause = String.format("%1$s = ?",CATEGORIE_ID_COLUMN);
	        String[] arg = new String[]{new String(key)};
	    	
	    	open();
	        Cursor cursor = database.query(CATEGORIE_TABLE_NAME, AllCategorieColumns, 
	        		whereClause, arg, null, null, null);
	        if (cursor == null){
	        	Log.i(TAG, "GetCategorieById: Error while retreiving categorie with id " + key);
	        	throw new SQLException("Error while retreiving categorie with id " + key);
	        }
	        
	        if (cursor.getCount() != 1){
	        	Log.i(TAG, "GetCategorieById: too many or too few records found while retreiving categorie with id " + key);
	        	throw new SQLException("Too many or too few records found while retreiving categorie with id " + key);
	        }
	        
	        Log.i(TAG, "Successfully retreived categorie with id " + key);
	        return new Categorie(cursor);
	    }
	
}
