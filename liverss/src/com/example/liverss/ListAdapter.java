package com.example.liverss;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<Item>{
	 private LayoutInflater mInflater;
	 private TextView mTitle;
	 private TextView mDescr;

	 //ここで一覧表示
	 
	 public ListAdapter(Context context, List<Item> objects) {
	 super(context,0, objects);
	 // TODO Auto-generated constructor stub
	 mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }

	 //一行ごとのビューを作成する。
	 public View getView(int position, View convertView,ViewGroup parent){
	 View view = convertView;
	 if(convertView == null){
	 view = mInflater.inflate(R.layout.itemlayout, null);
	 }
	 //何番目の値を取得か
	 Item item = this.getItem(position);
	 if(item != null){
//		 String title = intent.getStringExtra("TITLE");
//		 mTitle = (TextView) findViewById(R.id.textView1);
//		 mTitle.setText(title);
	 String title = item.getTitle().toString();
	 mTitle = (TextView)view.findViewById(R.id.textView1);
	 mTitle.setText(title);
	 //Log.v("tagss",title);
	 //タイトル表示だけにする
	 String date = item.getDate().toString();
	 mDescr = (TextView)view.findViewById(R.id.textView2);
	 mDescr.setText(date.substring(4,date.length()-5));
	 }
	 return view;
	 }

}
