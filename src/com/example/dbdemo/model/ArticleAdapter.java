package com.example.dbdemo.model;

import java.util.List;

import com.example.dbdemo.my.MyBaseArrayAdapter;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArticleAdapter extends MyBaseArrayAdapter<Article> {
	List<Article> items;
	ListActivity context;
	int viewRes;
	Resources res;
	
	public ArticleAdapter(Context context, List<Article> objects) {
		super(context, android.R.layout.simple_list_item_1, objects);
		this.context = (ListActivity) context;
		this.viewRes = android.R.layout.simple_list_item_1;
		this.items = objects;
		this.res = context.getResources();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		
		// initialize ViewHolder
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(viewRes, parent, false);
			
			holder = new ViewHolder();			
			holder.title = (TextView) view.findViewById(android.R.id.text1);
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		//get Article
		final Article article = items.get(position);

		//set holder values
		if (article != null) {
			holder.title.setText(article.getNom());
		}
		
		return view;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	
	static class ViewHolder {
        TextView title;
    }
	
	public void notifyDataSetChanged(Categorie c) {
		this.replace(Articles.getCurrentArticles(c));		
	}
	
}
