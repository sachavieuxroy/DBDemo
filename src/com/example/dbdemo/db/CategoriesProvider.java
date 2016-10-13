package com.example.dbdemo.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class CategoriesProvider extends ContentProvider {
    //I Ajouter une constante provider
    static final String PROVIDER_NAME = "google.android.com.dbdemo.Categories";
    //II Ajouter des constantes pour identifier les types MIME
    static final int CATEGORIES = 1;
    static final int CATEGORIE_ID = 2;
    static final int CATEGORIE_NOM = 3;
    //III Définisser les valeurs d'URI Matcher
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "categories", CATEGORIES);
        uriMatcher.addURI(PROVIDER_NAME, "categories/#", CATEGORIE_ID);
        uriMatcher.addURI(PROVIDER_NAME, "categories/*", CATEGORIE_NOM);
    }
    public static final Uri contentUri = Uri
            .parse("content://google.android.com.dbdemo.Categories");


    private final String CONTENT_PROVIDER_MIME =
            "vnd.android.cursor.item/vnd.google.android.com.categoriesprovider.categories";

    DBDemoOpenHelper dbHelper;
    public CategoriesProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
             SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {

                switch (uriMatcher.match(uri)) {
                    case CATEGORIES:
                        int i = db.delete(DBDemoOpenHelper.CATEGORIE_TABLE_NAME, selection,
                                selectionArgs);
                        db.delete("SQLITE_SEQUENCE", "NAME=?", new String[]{DBDemoOpenHelper.CATEGORIE_TABLE_NAME});
                        return i;


                    case CATEGORIE_ID:
                        return
                                db.delete(DBDemoOpenHelper.CATEGORIE_TABLE_NAME,
                                        DBDemoOpenHelper.CATEGORIE_ID_COLUMN+ "=" + uri.getPathSegments().get(1), selectionArgs);

                    case CATEGORIE_NOM:
                        int result =
                                db.delete(DBDemoOpenHelper.CATEGORIE_TABLE_NAME,
                                        DBDemoOpenHelper.CATEGORIE_NOM_COLUMN+ " Like '" + uri.getPathSegments().get(1) + "%'", selectionArgs);
                        return result;


                    default:
                        throw new IllegalArgumentException("Unknown URI " + uri);
                }
            } finally {
                db.close();
            }
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all chapitres records
             */
            case CATEGORIES:
                return "vnd.android.cursor.dir/vnd.android.categories";

            /**
             * Get a particular chapitres
             */
            case CATEGORIE_ID:
                return "vnd.android.cursor.item/vnd.android.categories";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            long id = db.insertOrThrow(DBDemoOpenHelper.CATEGORIE_TABLE_NAME,
                    null,values);

            if (id == -1) {
                throw new RuntimeException(String.format(
                        "%s : Failed to insert [%s] for unknown reasons.",
                        "AndroidProvider", values, uri));
            } else {
                return ContentUris.withAppendedId(uri, id);
            }

        } finally {
            db.close();
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBDemoOpenHelper(getContext(),
                DBDemoOpenHelper.DB_NAME, null, DBDemoOpenHelper.DB_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();



         switch (uriMatcher.match(uri)) {
            case CATEGORIES:
                return db.query(DBDemoOpenHelper.CATEGORIE_TABLE_NAME,
                        projection, selection, selectionArgs, null,  null,sortOrder);



            case CATEGORIE_ID:
                return db.query(DBDemoOpenHelper.CATEGORIE_TABLE_NAME,
                        projection, DBDemoOpenHelper.CATEGORIE_ID_COLUMN + "=" + uri.getPathSegments().get(1), null, null, null,  null);


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            switch (uriMatcher.match(uri)) {
                case CATEGORIES:
                    return db.update(DBDemoOpenHelper.CATEGORIE_TABLE_NAME, values,
                            selection, selectionArgs);


                case CATEGORIE_ID:
                    return db.update(DBDemoOpenHelper.CATEGORIE_TABLE_NAME, values,
                            CATEGORIE_ID + "=" + uri.getPathSegments().get(1), null);

                case CATEGORIE_NOM:
                    updateByNameStartingWith(DBDemoOpenHelper.CATEGORIE_TABLE_NAME, values, uri.getPathSegments().get(1));
                    return -1;


                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }


        } finally {
            db.close();
        }
    }

    private void updateByNameStartingWith(String table, ContentValues values, String whereValue){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StringBuilder sql = new StringBuilder(120);
        sql.append("UPDATE ");
        sql.append(table);
        sql.append(" SET ");

        // move all bind args to one array
        int setValuesSize = values.size();
        int bindArgsSize = setValuesSize;
        Object[] bindArgs = new Object[bindArgsSize];
        int i = 0;
        for (String colName : values.keySet()) {
            sql.append((i > 0) ? "," : "");
            sql.append(colName);
            bindArgs[i++] = values.get(colName);
            sql.append("=?");
        }
        sql.append(" WHERE ");
        sql.append(DBDemoOpenHelper.CATEGORIE_NOM_COLUMN + " Like '" + whereValue + "%'");

        ;
        try {
            db.execSQL(sql.toString(),bindArgs);
        } finally {
            db.close();
        }

    }
}

