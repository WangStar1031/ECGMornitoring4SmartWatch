package com.YHJstyle.b005.jump;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.TabItemView;
import com.YHJstyle.b005.j_style_Pro.b005.tool.ImageUtils;
import com.YHJstyle.b005.j_style_Pro.b005.tool.TransmitData;

import android.app.Activity;
import android.app.ActivityGroup;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
	
/**
 **/
public class BaseViewActivity extends ActivityGroup implements
		OnTabChangeListener {
	public static String bluetoothName = "";
	private long exitTime = 0L;
	private TabHost tabhost;
	private LinearLayout contentLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_tab_base);
		init();
	}

	private void init() {
		// ͨ��ƫ�������е��ļ��õ� �������õ�����
		contentLayout = (LinearLayout)findViewById(R.id.content_layout);
		contentLayout.setBackground(ImageUtils.ControlBitMap(BaseViewActivity.this, R.drawable.bg_commen));
		
		 bluetoothName = getSharedPreferences("bluetooth_info",
		 Context.MODE_PRIVATE).getString("bluetooth_device", "");
		
		tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup(getLocalActivityManager());
		addTabSpec("��ҳ", HomePageActivity.class, R.drawable.home_icon,
				R.string.base_home);
		addTabSpec("˯��", SleepActivity.class, R.drawable.sleep_icon,
				R.string.base_sleep);
		addTabSpec("��¼", ActivityActivity.class, R.drawable.activity_icon,
				R.string.base_activity);
		addTabSpec("����", ProgressActivity.class, R.drawable.progress_icon,
				R.string.base_progress);
		addTabSpec("��ʾ", AlarmActivity.class, R.drawable.alarm_icon,
				R.string.base_alarm);
		
		tabhost.setCurrentTab(0);
		tabhost.setOnTabChangedListener(this);
		// ȡ�ø���Ŀ��
		String str = getSharedPreferences("USERS_INFO", Context.MODE_PRIVATE).getString("user_datagoald", "");
		if(!"".equals(str)){
			TransmitData.setDataGoald = Integer.parseInt(str);
		}
		// String str = getSharedPreferences("USERS_INFO",
		// 0).getString("user_datagoald", "");
		// if (!"".equals(str)) {
		// com.YHYHpedometer.lib.bluetooth.TransmitData.setDataGoald =
		// Integer.parseInt(str);

	}

	/**
	 * ͨ���ļ��࣬�������β��� ����tabSpec
	 * */
	private void addTabSpec(String str, Class<?> paramClass, int i, int j) {
		TabSpec tabSpec = tabhost.newTabSpec(str);
		tabSpec.setContent(new Intent(this, paramClass));
		tabSpec.setIndicator(new TabItemView(this, i, j));
		tabhost.addTab(tabSpec);

	}

	@Override
	public void onTabChanged(String tabId) {

	}
	
	/**
	 *�������˳�����
	 * */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			
			if (System.currentTimeMillis() - exitTime > 2000L) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.finish_system),
						Toast.LENGTH_LONG).show();
				this.exitTime = System.currentTimeMillis();
				
				return true;
			}
			exitProgrames();
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * �˳����� 
	 * ֱ��ɱ������
	 * */
	public void exitProgrames() {
		Intent intent = new Intent("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.HOME");
		// ����ģʽ
		intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		// ֱ��ɱ������
		Process.killProcess(Process.myPid());
	}
	
	
	
}
