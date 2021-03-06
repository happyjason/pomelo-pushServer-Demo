package com.example.pomelo_push_demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.pomelopush.service.PomeloPushServer;
import com.netease.pomelo.PomeloClient;

public class MainActivity extends Activity {
	PomeloClient client;

	public static String CURRENTHOST = "42.121.117.150";
//	public static String currentHost = "192.168.1.107";
	//http://42.121.117.150:3001/dashboard
	public static int CURRENTPort = 3010;
	public static final String APIKEY = "0aada7a0-dd93-11e2-a5a5-2726305d545e";

	TextView tv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv1 = (TextView) findViewById(R.id.textView1);
		
		TextView host = (TextView) findViewById(R.id.host);
		host.setText(CURRENTHOST);
		
		TextView apiKey = (TextView) findViewById(R.id.apikey);
		apiKey.setText(APIKEY);
		
		findViewById(R.id.button2).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra(PomeloPushServer.HOST, CURRENTHOST);
				intent.putExtra(PomeloPushServer.PORT, CURRENTPort);
				//0aada7a0-dd93-11e2-a5a5-2726305d545e
				intent.putExtra(PomeloPushServer.APIKEY, APIKEY);
				PomeloPushServer.start(MainActivity.this, intent);
			}
			
		});
	}
	LocalBroadcastManager localBroaCast;
	
	BroadcastReceiver  msgNotfiy = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent != null){
			
				String msg = intent.getStringExtra(PomeloPushServer.PUSHMSG);
				tv1.setText(msg);
			}
		}
	};
	@Override
	protected void onResume() {
		super.onResume();
		localBroaCast = LocalBroadcastManager.getInstance(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(PomeloPushServer.PUSHACTION);
		localBroaCast.registerReceiver(msgNotfiy, filter);
		
	}
	

	@Override
	protected void onPause() {
		super.onPause();
		localBroaCast.unregisterReceiver(msgNotfiy);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	

	
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}




}
