package com.example.liverss;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Xml;
import android.widget.ListView;

public class RssTask extends AsyncTask<String, Integer, ListAdapter> {

	 private MainActivity mActivity;
	 private ListAdapter mAdapter;
	 private ProgressDialog mProgressDialog;
	 private ListView mListView;
	
	// コンストラクタ
	public RssTask(MainActivity activity, ListAdapter adapter,ListView _listView) {
		mActivity = activity;
		mListView = _listView;
		mAdapter = adapter;
	}

	// タスクを実行した直後にコールされる
	@Override
	protected void onPreExecute() {//非同期処理前に実装
		// プログレスバーを表示する
		mProgressDialog = new ProgressDialog(mActivity);
		mProgressDialog.setMessage("データの取得中...");
		mProgressDialog.show();
	}

	// バックグラウンドにおける処理を担う。タスク実行時に渡された値を引数とする
	@Override
	protected ListAdapter doInBackground(String... params) {//非同期処理内容(メイン実行ではない)
		//ArrayList<String> mItems = new ArrayList<String>();
		ListAdapter result = null;
		try {
			// HTTP経由でアクセスし、InputStreamを取得する
			URL url = new URL(params[0]);
			InputStream is = url.openConnection().getInputStream();
			result = parserXml(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	// メインスレッド上で実行される
	@Override
	protected void onPostExecute(ListAdapter result) {//doIn～が実行された後にメインスレッド実行、doIn～の戻り値をここの引数に
		mProgressDialog.dismiss();
		mListView.setAdapter(result);
	}

	public ListAdapter parserXml(InputStream is)
			throws IOException, XmlPullParserException {
		
		XmlPullParser parser = Xml.newPullParser();//パース用インスタンス
		
		try {
			parser.setInput(is, null);
			int eventType = parser.getEventType();
			Item currentItem = null;
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tag = null;
				
				switch (eventType) {
				
				case XmlPullParser.START_TAG:
					tag = parser.getName();
					
					if (tag.equals("item")) {
						currentItem = new Item();
						
					} else if (currentItem != null) {	
						if (tag.equals("title")) {
							//タイトルタグの中身の文字の事
							currentItem.setTitle(parser.nextText());
						}
						else if (tag.equals("pubDate")) {
							//日付と時間の表示
							currentItem.setDate(parser.nextText());
						}
						else if (tag.equals("link")) {
							//説明タグの中身の文字の事(いらない？→タイトルだけでいいかな？)
							//ここを書き換えて中身からリンクを拾う
							currentItem.setLink(parser.nextText());
						}
					}
					break;
				
				case XmlPullParser.END_TAG:
					tag = parser.getName();
					if (tag.equals("item")) {
						mAdapter.add(currentItem);
					}
					break;
					}
				
				eventType = parser.next();
				
				}
			} catch (Exception e) {
					e.printStackTrace();
			}
			 	return mAdapter;
	}
	
}

