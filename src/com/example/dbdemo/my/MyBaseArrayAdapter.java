package com.example.dbdemo.my;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class MyBaseArrayAdapter<T> extends ArrayAdapter<T> {

	public MyBaseArrayAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
	}

	public void replace(ArrayList<?> items) {
		super.setNotifyOnChange(false);
		T newitems = (T) items.clone();
		super.clear();
		super.addAll((Collection<? extends T>) newitems);
		super.setNotifyOnChange(true);
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}
	
}
