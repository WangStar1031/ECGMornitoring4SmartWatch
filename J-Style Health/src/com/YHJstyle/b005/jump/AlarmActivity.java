package com.YHJstyle.b005.jump;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.YHJstyle.b005.R;

/**
 * ÌבÊ¾
 * */

public class AlarmActivity extends Activity implements OnClickListener {
	private AlertDialog dialog;
	private Time mTime;
	private EditText messageEt;
	private String messageStr;
	private EditText phoneEt;
	private String phoneStr;
	@Override	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sport_remind:
			Intent intent =  new Intent(AlarmActivity.this, FatigueMindSetting.class);
			startActivity(intent);
			break;
		case R.id.clock_remind:
			startActivity(new Intent(this, AlarmclockSetting.class));
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_page);
		((Button)findViewById(R.id.clock_remind)).setOnClickListener(this);
		((Button)findViewById(R.id.sport_remind)).setOnClickListener(this);
		
	}

}
