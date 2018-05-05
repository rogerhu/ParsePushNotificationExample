package com.test;


import com.parse.ParseCloud;
import com.parse.ParseInstallation;

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
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends Activity implements OnClickListener {

	private Button push;

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		
        @Override
        public void onReceive(Context context, Intent intent) {        	
        	Toast.makeText(getApplicationContext(), "onReceive invoked!", Toast.LENGTH_LONG).show();
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		push = (Button)findViewById(R.id.senPushB);
		push.setOnClickListener(this);
	}
	
	@Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
    
	@Override
    public void onResume() {
        super.onResume();
        
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, new IntentFilter(MyCustomReceiver.intentAction));
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		JSONObject payload = new JSONObject();
		try {
			payload.put("sender", ParseInstallation.getCurrentInstallation().getInstallationId());
		} catch (JSONException e) {

		}
        HashMap<String, String> data = new HashMap<>();
        data.put("customData", payload.toString());
		ParseCloud.callFunctionInBackground("pingReply", data);
	}

	
}
