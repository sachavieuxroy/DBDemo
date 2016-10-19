package com.example.dbdemo.db;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mail on 10/13/2016.
 */

public class WebServiceHelper {
    private Context baseContext;
    private String resultat;

    public class Asynchrone extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            String strUrl = params[0];
            HttpURLConnection httpUrlConnection = null;
            String result = null;
            try {
                URL url = new URL(strUrl);
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = new BufferedInputStream(httpUrlConnection.getInputStream());
                int inChar;
                final StringBuilder readStr = new StringBuilder();
                while ((inChar = inStream.read()) != -1) {
                    readStr.append((char) inChar);
                }
                result=readStr.toString();
                parseJsonFile(result);
                httpUrlConnection.disconnect();
            } catch (MalformedURLException me) {
                me.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Toast.makeText(baseContext, result, Toast.LENGTH_LONG).show();
        }


    }
    public void getHttpResonse(String url){
        resultat="";
        Asynchrone as=new Asynchrone();
        as.execute(url);
    }
    private void parseJsonFile (String jString) throws Exception {
        JSONObject jsonObj = new JSONObject(jString);


        JSONArray book  = jsonObj.getJSONArray("books");
        for (int i = 0; i < book.length(); i++) {
            String attributeId = book.getJSONObject(i).getString("id");
            resultat+="id: "+attributeId+"\n";

            String attributeName = book.getJSONObject(i).getString(
                    "name");
            resultat+="name: "+attributeName+"\n";
            String attributeAuthor = book.getJSONObject(i).getString(
                    "name");
            resultat+="author: "+attributeAuthor+"\n";
            String attributeIsbn = book.getJSONObject(i).getString(
                    "isbn");
            resultat+="isbn: "+attributeIsbn+"\n";


        }
    }


}
