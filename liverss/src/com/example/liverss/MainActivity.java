package com.example.liverss;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener{
    
	/** 記事のジャンル 主要、国内、海外、IT・経済、芸能、スポーツ、映画、グルメ、女子、トレンド*/
	public static final String RSS_FEED_URL = "http://news.livedoor.com/topics/rss/top.xml";
	private static final int MENU_ID_MENU1 = (Menu.FIRST + 1);
    private static final int MENU_ID_MENU2 = (Menu.FIRST + 2);
    private static final int MENU_ID_MENU3 = (Menu.FIRST + 3);
    private static final int MENU_ID_MENU4 = (Menu.FIRST + 4);
    private static final int MENU_ID_MENU5 = (Menu.FIRST + 5);
    private static final int MENU_ID_MENU6 = (Menu.FIRST + 6);
    private static final int MENU_ID_MENU7 = (Menu.FIRST + 7);
    private static final int MENU_ID_MENU8 = (Menu.FIRST + 8);
    private static final int MENU_ID_MENU9 = (Menu.FIRST + 9);
    private static final int MENU_ID_MENU10 = (Menu.FIRST + 10);
    
    private boolean visible = true;
    


	

	private ArrayList mItems;
	private ListAdapter mAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
      
    
    public void DoTask(String url){
    	mItems = new ArrayList();
        mAdapter = new ListAdapter(this, mItems);
    	ListView _listview = (ListView)findViewById(R.id.listView1);
    	RssTask task = new RssTask(this, mAdapter,_listview);
	    task.execute(url);
	        	
    	//MyIntentServiceを起動する
//        Intent intent = new Intent(this, RssIntentService.class);
//        RssIntentService.RssIntentService(this, mAdapter,_listview);
//        intent.putExtra("IntentServiceCommand",url);
//        this.startService(intent);
    
        _listview.setOnItemClickListener(this);
        
    }
    
    @Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自動生成されたメソッド・スタブ
		Item item = (Item)mItems.get(arg2);
		Intent intent = new Intent(this, ItemDetailActivity.class);
		intent.putExtra("TITLE", item.getTitle());
		intent.putExtra("LINK", item.getLink());
		intent.putExtra("DATE", item.getDate());
		startActivity(intent);
		
	}
    
 // オプションメニューが最初に呼び出される時に1度だけ呼び出されます
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//    	final MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
        // メニューアイテムを追加します
        menu.add(Menu.NONE, MENU_ID_MENU1, Menu.NONE, "主要");
        menu.add(Menu.NONE, MENU_ID_MENU2, Menu.NONE, "国内");
        menu.add(Menu.NONE, MENU_ID_MENU3, Menu.NONE, "海外");
        menu.add(Menu.NONE, MENU_ID_MENU4, Menu.NONE, "IT経済");
        menu.add(Menu.NONE, MENU_ID_MENU5, Menu.NONE, "芸能");
        menu.add(Menu.NONE, MENU_ID_MENU6, Menu.NONE, "スポーツ");
        menu.add(Menu.NONE, MENU_ID_MENU7, Menu.NONE, "映画");
        menu.add(Menu.NONE, MENU_ID_MENU8, Menu.NONE, "グルメ");
        menu.add(Menu.NONE, MENU_ID_MENU9, Menu.NONE, "女子");
        menu.add(Menu.NONE, MENU_ID_MENU10, Menu.NONE, "トレンド");
        
        //actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        
        return true;
    }

    // オプションメニューが表示される度に呼び出されます
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(MENU_ID_MENU2).setVisible(visible);
        visible = !visible;
        return super.onPrepareOptionsMenu(menu);
    }

    // オプションメニューアイテムが選択された時に呼び出されます
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	boolean ret = true;

        switch (item.getItemId()) {
        default:
            ret = super.onOptionsItemSelected(item);
            break;
        case MENU_ID_MENU1://主要
        	DoTask("http://news.livedoor.com/topics/rss/top.xml");//主要
        	ret = true;
            break;
        case MENU_ID_MENU2://国内
        	DoTask("http://news.livedoor.com/topics/rss/dom.xml");
        	ret = true;
            break;
        case MENU_ID_MENU3://海外
        	DoTask("http://news.livedoor.com/topics/rss/int.xml");
            ret = true;
            break;
        case MENU_ID_MENU4://IT経済
        	DoTask("http://news.livedoor.com/topics/rss/eco.xml");
            ret = true;
            break;
        case MENU_ID_MENU5://芸能
        	DoTask("http://news.livedoor.com/topics/rss/ent.xml");
            ret = true;
            break;
        case MENU_ID_MENU6://スポーツ
        	DoTask("http://news.livedoor.com/topics/rss/spo.xml");
            ret = true;
            break;
        case MENU_ID_MENU7://映画
        	DoTask("http://news.livedoor.com/rss/summary/52.xml");
            ret = true;
            break;
        case MENU_ID_MENU8://グルメ
        	DoTask("http://news.livedoor.com/topics/rss/gourmet.xml");
            ret = true;
            break;
        case MENU_ID_MENU9://女子
        	DoTask("http://news.livedoor.com/topics/rss/love.xml");
            ret = true;
            break;
        case MENU_ID_MENU10://トレンド
        	DoTask("http://news.livedoor.com/topics/rss/trend.xml");
            ret = true;
            break;
        }
        return ret;
    }

    
    @Override
    protected void onResume() {
        super.onResume();
    }

}

	