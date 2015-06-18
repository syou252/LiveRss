package com.example.liverss;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class ItemDetailActivity extends Activity{
	 	private TextView mTitle;
	    private TextView mDescr;
	    static String strBody;
	    
	  
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.itemdetail);
	        
	        

	        Intent intent = getIntent();
	        String link = intent.getStringExtra("LINK");
	        String title = intent.getStringExtra("TITLE");
	        mTitle = (TextView) findViewById(R.id.textView1);
	        mTitle.setText(title);
	   
			//タスク起動
			AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
			
			 
			 @Override
			 protected void onPreExecute() {
			
			 }
			
			 @Override
			 protected String doInBackground(String... params) {
			
				 String Url = params[0];
			     String html = "";
			     HttpClient client = new DefaultHttpClient();
			
			     try {
			         // 続きを読み込む
			    	 html = client.execute((new HttpGet(Url)), new BasicResponseHandler());
			     } catch (ClientProtocolException e) {
			     } catch (IOException e) {
			     } finally {
			         client.getConnectionManager().shutdown();
			     }
			     //Log.v("tag",html);
			     return html;
			 }
			 
			 @Override
			 protected void onPostExecute(String param) {
				 
				 TextView textview3 = (TextView)findViewById(R.id.textView3);
			     TextView textview4 = (TextView)findViewById(R.id.textView4);
			     TextView textview5 = (TextView)findViewById(R.id.textView5);
			     TextView textview6 = (TextView)findViewById(R.id.textView6);
			     // ここでHTMLソースを改変してarticleBodyを表示
				 String body1 = "<span itemprop=\"articleBody\">";
				 String body2 = "</span>";
				 String body3 = "関連記事";
				 String body4 = "\">";
				 String body5 = "</a>";
				 String body6 = "href";
				 int place1 = param.indexOf(body1);
				 int n = body1.length();
				 int place2 = param.indexOf(body2,place1+n);
				 String Text = param.substring(place1+n,place2);
				 int AAA = Text.indexOf(body3);
				 
				 if( -1 < AAA){
					 //---関連記事を表示---
					 
					 String Textrel = Text.substring(AAA);
					 
					 int place3 = Textrel.indexOf(body6);
					 int place4 = Textrel.indexOf(body4);
					 int place5 = Textrel.indexOf(body5);
					 String Textlink1 = Textrel.substring(place3+6,place4);//link
					 String Textview1 = Textrel.substring(place4+2,place5);//subtitle
					 
					 int place6 = Textrel.indexOf(body6,place3);//place5～
					 int place7 = Textrel.indexOf(body4,place4);
					 int place8 = Textrel.indexOf(body5,place5);
					 String Textlink2 = Textrel.substring(place6+5+place5,place7+place5);//link
					 String Textview2 = Textrel.substring(place7+1+place5,place8+place5);//subtitleここに問題
					 
					 int place9 = Textrel.indexOf(body6,place5+place8);
					 int place10 = Textrel.indexOf(body4,place5+place8);
					 int place11 = Textrel.indexOf(body5,place5+place8);
					 String Textlink3 = Textrel.substring(place9+6,place10);//title
					 String Textview3 = Textrel.substring(place10+2,place11);//subtitle
					 
					 textview3.setText("＝＝＝＝関連記事＝＝＝＝");
					 textview4.setText(Textlink1);
					 textview5.setText(Textlink2);
					 textview6.setText(Textlink3);
					 
//					 Pattern pattern1 = Pattern.compile(Textview1);//なぜか日本語をリンク化できない
//					 Pattern pattern2 = Pattern.compile(Textview2);
//					 Pattern pattern3 = Pattern.compile(Textview3);
					 Linkify.addLinks(textview4, Linkify.ALL);
					 Linkify.addLinks(textview5, Linkify.ALL);
					 Linkify.addLinks(textview6, Linkify.ALL);
				 
				 }
				 
				 
				 //余計なタグの消去
				 Text=HtmlTagRemover(Text);//タグ削除
			     ((TextView)findViewById(R.id.textView2)).setText(Text);
			 }
			};
						//タスクを実行
				        task.execute(link);
			

	    }
	     
	    public static String HtmlTagRemover(String str) {
	    	// 文字列のすべてのタグを取り除く
	    	return str.replaceAll("<.+?>", "");
	    }
}
