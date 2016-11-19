package com.spring.login;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	
	ArrayList <String> list;
	Context context;
	
	public void setContext(Context context){
		this.context = context;
	}
	
	public void setArrayList(ArrayList <String> list){
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		String item = list.get(position);
		return item;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout layout;
		layout = new LinearLayout(context);
		TextView text = new TextView(context);
		text.setTextSize(1, 25);
		text.setText("--"+getItem(position)+"--");
		layout.addView(text);
		return layout;
	}

}
