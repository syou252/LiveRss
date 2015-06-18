package com.example.liverss;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

public class RssDetailTask extends AsyncTask<String, Integer, String> {
	
	private ItemDetailActivity mActivity;
	private ProgressDialog mProgressDialog;
	private TextView textView;
	//private String strbody;
	
   
    
 // コンストラクタ
 	public RssDetailTask(ItemDetailActivity activity, TextView textview) {
 		mActivity = activity;
 		textView = textview;
 		//strbody = strBody;
// 		mListView = _listView;
// 		mAdapter = adapter;
 	}

	// タスクを実行した直後にコールされる
	@Override
	protected void onPreExecute() {//非同期処理前に実装
		// プログレスバーを表示する
		mProgressDialog = new ProgressDialog(mActivity);
		mProgressDialog.setMessage("データの取得中...");
		mProgressDialog.show();
	}
	
	
	@Override
	protected String doInBackground(String... params) {

		String html = "";
        HttpClient client = new DefaultHttpClient();
 
        try {
 
            // Google先生を読み込む
            html = client.execute((new HttpGet("http://www.google.co.jp/")), new BasicResponseHandler());
 
        } catch (ClientProtocolException e) {
 
        } catch (IOException e) {
 
        } finally {
 
            client.getConnectionManager().shutdown();
        }
 
        return html;
		//		String StrBody="";
//		 try {
//				// HTTP経由でアクセスし、InputStreamを取得する
//	        	URL url1 = new URL(params[0]);
//	        	String str=params[0];
//	        	Log.v("tag",str);
//				InputStream iss = url1.openConnection().getInputStream();
//				StrBody = parseXml(iss);
//				textView.setText(StrBody);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		return null;
	}
	
	@Override
	// メインスレッド上で実行される
		protected void onPostExecute(String result) {//doIn～が実行された後にメインスレッド実行、doIn～の戻り値をここの引数に
			mProgressDialog.dismiss();
			//ListView list = (ListView)mActivity.findViewById(R.id.list);
			//adapter = new ArrayAdapter<String>(mActivity,android.R.layout.simple_expandable_list_item_1,result);
			//list.setAdapter(adapter);
			
		}
	

	public String parseXml(InputStream iss)
			throws IOException, XmlPullParserException {	
		
		XmlPullParser parse = Xml.newPullParser();//パース用インスタンス
		
		String strBody = "aaa";
		
		try {
			
			parse.setInput(iss, null);
			int EventType = parse.getEventType();
			//EventType = parse.next();//increment
			while (EventType != XmlPullParser.END_DOCUMENT) {
	        	if(EventType == XmlPullParser.START_DOCUMENT) {
	                Log.v("tag","Start document");
	                strBody = parse.getName();
	                Log.v("tag",strBody);
	        	} 
	        	else if(EventType == XmlPullParser.START_TAG) {
	                
	                for(int i=0; i<parse.getAttributeCount(); i++){
	                	if(parse.getAttributeValue(i).equals("articleBody")){
	                		strBody = parse.nextText();
	                	}
	                }
//	                if(strBody.equals("")){
//	                	strBody = parse.nextText();
//	                	
//	                }
	                
	            }
	            EventType = parse.next();//increment
			}
	                
		} catch (XmlPullParserException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return strBody;
	}
	
	
	
}
