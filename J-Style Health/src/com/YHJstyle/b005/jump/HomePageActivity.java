package com.YHJstyle.b005.jump;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.HomeRoundView;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarUtil;
import com.YHJstyle.b005.j_style_Pro.b005.tool.BluetoothLeService;
import com.YHJstyle.b005.j_style_Pro.b005.tool.ImageUtils;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyBrostReiver;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyHandler;
import com.YHJstyle.b005.j_style_Pro.b005.tool.ResolveData;
import com.YHJstyle.b005.j_style_Pro.b005.tool.SaveDataBase;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends Activity implements OnClickListener {

	private MyBrostReiver deviceStateListener;
	private static final String TAG = "HomePageActivity";

	private static final int HRP_PROFILE_CONNECTED = 20;
	private static final int HRP_PROFILE_DISCONNECTED = 21;
	private static final int REQUEST_ENABLE_BT = 2;
	private static final int REQUEST_SELECT_DEVICE = 1;

	private static final int STATE_OFF = 10;// 关闭
	private static final int STATE_READAY = 10;// 启动
	private static String update_day = "";
	private String bluetooth_name = "";
	private boolean isStart;
	private BluetoothDevice mDevice = null;
	private BluetoothAdapter mBtAdapter = null;
	private MyHandler mHandler;

	private BluetoothLeService mService = null;
	
	private Button heartBtn;// 心率测试
	private Button searchBt;
	private Button settingBtn;
	private Button startBtn;
	private Button syncBt;
	private Timer mTimer;
	
	private LinearLayout indexLayout;
			
	TextView textViewH;
	//判断是否关闭实时记步功能
	private boolean IsTen = false;
	private Button upGradeBtn;
//	public short[] heartRate = new short[2];
//	public short[] temp = new short[501];
//	public short mHeartRate = 0;
	// 接收心率数据
	//公制 英制 
	private TextView kmOrMileTv;
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = ((BluetoothLeService.LocalBinder) service).getService();
//			Log.v("HomePageActivity", "onServiceConnected mService= "
//					+ HomePageActivity.this.mService);
			mService.setActivityHandler(mHandler);
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
//			Log.i("APP", "WHAT ==" + msg.what);
			switch (msg.what) {
			case 1:
				mTimer.cancel();
				timerSync();
				MyApplication.getInstance().mService.WriteSomedayData(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND,
						MyApplication.mDevice, (byte) 67,
						(byte) MyApplication.saveDataTimes);
//				Log.i("APP", "MyApplication.saveDataTimes"
//						+ MyApplication.saveDataTimes);
				break;
			case 0:
				mTimer.cancel();
				startBtn.setClickable(true);
				settingBtn.setClickable(true);
				MyApplication.CloseLoadingProgress();
				break;
			default:
				break;
			}
		}

	};

	/**
	 * 判断时间 判断间隔等于 1或者 0 时
	 * */
	private void timerSync() {
		mTimer = new Timer();
		TimerTask timerTask3 = new TimerTask() {

			@Override
			public void run() {
				Message message = new Message();
				int i = Integer.valueOf(
						CalendarUtil.getTwoDay(CalendarUtil
								.getNowTime("yyyy-MM-dd"), SaveDataBase
								.getSaveDate(HomePageActivity.this, "date",
										"2013-07-14"))).intValue();
//				Log.i("APP", "timerSync  " + i);
				if(i>0){
					message.what = 1;
				}else 
					message.what = 0;
				handler.sendMessage(message);
			}
		};
		//
		mTimer.schedule(timerTask3, 115000L);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBtAdapter == null) {
			Toast.makeText(this,
					getResources().getString(R.string.bluetooth_useType),
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		init();
	}


	private void init() {
		indexLayout = (LinearLayout)findViewById(R.id.index_layout);
		Intent intent = new Intent(this, BluetoothLeService.class);
		// 启动服务 二个
		getApplicationContext().startService(intent);
		getApplicationContext().bindService(intent, mServiceConnection,
				BIND_AUTO_CREATE);
		// 添加意图过滤器
		IntentFilter intentFilter = new IntentFilter();
		intentFilter
				.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
		intentFilter
				.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
		getApplicationContext().registerReceiver(deviceStateListener,
				intentFilter);


		MyApplication.dataLayout = (LinearLayout) findViewById(R.id.home_data_layout);
		
		
		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.daily_goald_layout);
		MyApplication.dataGoaldLayout = relativeLayout;
		relativeLayout.addView(new HomeRoundView(this, null, relativeLayout, 0,
				MyApplication.goaldData));
		indexLayout.setBackground(ImageUtils.ControlBitMap(HomePageActivity.this, R.drawable.bg_commen));
		
		kmOrMileTv = (TextView)findViewById(R.id.home_page_textView7);
		
		
		
		settingBtn = (Button) findViewById(R.id.setting_btn);
		settingBtn.setOnClickListener(this);
		searchBt = (Button) findViewById(R.id.search_button);
		searchBt.setOnClickListener(this);
		startBtn = (Button) findViewById(R.id.start_btn);
		startBtn.setOnClickListener(this);
		syncBt = (Button) findViewById(R.id.sync_btn);
		syncBt.setOnClickListener(this);
		//
		heartBtn = (Button) findViewById(R.id.heart_button);
		heartBtn.setOnClickListener(this);
		// upGradeBtn = (Button) findViewById(R.id.home_upgrade);
		// upGradeBtn.setOnClickListener(this);

		isStart = false;
		startBtn.setBackground(getResources().getDrawable(
				R.drawable.home_page_start_selected));
		mHandler = new MyHandler(this);
		deviceStateListener = new MyBrostReiver();
		searchBt.setText(getResources().getString(R.string.connected));
		MyApplication.connectBt = searchBt;

//		EcgNative.EcgIni(50);
	}

	@Override
	public void onClick(View v) {
		BluetoothDevice bDevice = null;
		switch (v.getId()) {
		// case R.id.home_upgrade:
		// if ((MyApplication.isBluetoothConnection)) {
		// MyApplication.getInstance().mService.writeDataToPedometer(
		// BluetoothLeService.UUID_PEDOMETER_SERVICE,
		// BluetoothLeService.UUID_PEDOMETER_SEND, 71);
		// Log.i("APP", "----------");
		// }
		// break;
		case R.id.heart_button:
			if(MyApplication.isBluetoothConnection){
//				if(!IsTen){
//					if (MyApplication.getInstance().mService != null) {
//						MyApplication.getInstance().mService
//								.writeDataToPedometer(
//										BluetoothLeService.UUID_PEDOMETER_SERVICE,
//										BluetoothLeService.UUID_PEDOMETER_SEND,
//										10);
//				}
//				}
				Intent intent1 = new Intent();
				intent1.setClass(HomePageActivity.this, HeartActivity.class);
				
				startActivity(intent1);
				if(mService == null){
					mService = MyApplication.getInstance().mService;
				}
			}else{
				Toast.makeText(
						this,
						getResources().getString(
								R.string.home_page_blutooth_dialog),
						Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.setting_btn:
			// long start = System.currentTimeMillis();
			Intent intent = new Intent(HomePageActivity.this,
					SettingActivity.class);
			startActivity(intent);
			// long end = System.currentTimeMillis();
			// Log.i("APP", "" + (end - start));
			break;

		case R.id.search_button:
			try {
				// 连接断开 主要通过关闭notify
				if ((MyApplication.mState != 10)
						&& (MyApplication.mState != 20)) {
					bluetooth_name = getSharedPreferences("bluetooth_info", 0)
							.getString("bluetooth_device", "");
//					Log.i("APP", "bluetooth_name=== " +bluetooth_name);
					if(!TextUtils.isEmpty(bluetooth_name)){
//					if ((!"".equals(bluetooth_name))
//							&& (!"null".equals(bluetooth_name))) {
						BluetoothDevice device = BluetoothAdapter
								.getDefaultAdapter().getRemoteDevice(
										bluetooth_name);
						if (device != null) {
							if (MyApplication.getInstance().mService == null) {
//								Log.i("APP", "DAO这里");
								MyApplication.getInstance().mService = this.mService;
							}
								mService.connect(device, false);
								MyApplication.mDevice = device;
								mDevice = device;
							searchBt.setText(getResources().getString(
									R.string.bluetooth_disconnected));
							SettingActivity.isConnection = false;
						}
					} else {
						Toast.makeText(
								this,
								getResources().getString(
										R.string.home_page_blutooth_dialog),
								Toast.LENGTH_LONG).show();
					}
				} else if (MyApplication.mState != 21) {
					bluetooth_name = getSharedPreferences("bluetooth_info", 0)
							.getString("bluetooth_device", "");
					if ((!"".equals(bluetooth_name))
							&& (!"null".equals(bluetooth_name))) {
						BluetoothDevice device = BluetoothAdapter
								.getDefaultAdapter().getRemoteDevice(
										bluetooth_name);
					}
					if (mService != null) {
						mService = MyApplication.getInstance().mService;
					}
//					mService.disableNotification(
//							BluetoothLeService.UUID_PEDOMETER_SERVICE,
//							BluetoothLeService.UUID_PEDOMETER_SEND,
//							MyApplication.mDevice);
					mService.disconnect(MyApplication.mDevice);
					MyApplication.mState = 21;
					searchBt.setText(getResources().getString(
							R.string.connected));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.start_btn:// 开始
			if (MyApplication.isBluetoothConnection) {
				if (!MyApplication.homepageStartOrStop) {

					startBtn.setBackground(getResources().getDrawable(
							R.drawable.home_page_stop_selected));

					if (MyApplication.dataGoaldLayout != null) {
						HomeRoundView homeRoundView2 = new HomeRoundView(
								MyApplication.getInstance(), null,
								MyApplication.dataGoaldLayout, 0, 0);
						MyApplication.dataGoaldLayout.addView(homeRoundView2);

						if (MyApplication.getInstance().mService != null) {
							MyApplication.getInstance().mService
									.writeDataToPedometer(
											BluetoothLeService.UUID_PEDOMETER_SERVICE,
											BluetoothLeService.UUID_PEDOMETER_SEND,
											9);
							MyApplication.homepageStartOrStop = true;
							syncBt.setClickable(false);
						}
					}

				} else {

					startBtn.setBackground(getResources().getDrawable(
							R.drawable.home_page_start_selected));
					if (MyApplication.getInstance().mService != null) {
						MyApplication.getInstance().mService
								.writeDataToPedometer(
										BluetoothLeService.UUID_PEDOMETER_SERVICE,
										BluetoothLeService.UUID_PEDOMETER_SEND,
										10);
						MyApplication.homepageStartOrStop = false;
						IsTen = true;
						HomeRoundView homeRoundView1 = new HomeRoundView(
								MyApplication.getInstance(), null,
								MyApplication.dataGoaldLayout, 0, 0);
						MyApplication.dataGoaldLayout.addView(homeRoundView1);
						TextView textViewH2 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_heart);
						TextView textView5 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_textView2);
						TextView textView6 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_textView4);
						TextView textView7 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_textView6);
						TextView textView8 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_textView8);
						textViewH2.setText("0");
						textView5.setText("0");
						textView6.setText("0");
						textView7.setText("0");
						textView8.setText("00:00");
						syncBt.setClickable(true);
					}
				}
			} else {
				Toast.makeText(
						this,
						getResources().getString(
								R.string.home_page_blutooth_dialog),
						Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.sync_btn:
			if ((MyApplication.isBluetoothConnection)
					&& (MyApplication.mState == STATE_READAY)) {
//				timerSync();// 时间同步
				forLoadingProgress();
				String str1 = getSharedPreferences("DateTime",
						Context.MODE_PRIVATE).getString("user_time", "");
//				Log.i("APP", "str1" + str1.toString());
				String str2 = "2013-07-14";
				if (!("".equals(str1))) {
					str2 = str1;
					String[] arr1 = str1.split("-");
					SQLiteDatabase sql = new SaveDataBase.SaveDateBaseHelp(this)
							.getWritableDatabase();
					String[] arr2 = new String[3];
					arr2[0] = arr1[0];
					arr2[1] = arr1[1];
					arr2[2] = arr1[2];
					sql.delete(
							"db_pedometer_info",
							"pedometer_year =? AND pedometer_month =? AND pedometer_day =? ",
							arr2);
					sql.close();
				}

				bluetooth_name = getSharedPreferences("bluetooth_info",
						Context.MODE_PRIVATE).getString("bluetooth_device", "");
				// 判断设备是否一样
				if (!(BaseViewActivity.bluetoothName.equals(bluetooth_name))) {
					BaseViewActivity.bluetoothName = bluetooth_name;
					SQLiteDatabase sql2 = new SaveDataBase.SaveDateBaseHelp(
							this).getWritableDatabase();
					sql2.delete("db_pedometer_info", null, null);
					sql2.close();
					str2 = "2013-07-14";
				}
				// 取得没保存数据天数
				int i = Integer.valueOf(
						CalendarUtil.getTwoDay(
								CalendarUtil.getNowTime("yyyy-MM-dd"), str2))
						.intValue();
//				Log.i("APP", "start ================" + i);
				if (i > 0) {
					if (i >= 30) {
						i = 29;
					}
//					Log.i("APP", "dayTime:" + i);
					MyApplication.saveDataTimes = i;
					ResolveData.syncDataThread((byte) 67);

				} else {
					MyApplication.saveDataTimes = 0;
					ResolveData.syncDataThread((byte) 67);
				}

			} else {

				Toast.makeText(
						this,
						getResources().getString(
								R.string.home_page_blutooth_dialog),
						Toast.LENGTH_LONG).show();
			}

			break;
		}
	}

	private void forLoadingProgress() {
		MyApplication.homeProgressLayout = (RelativeLayout) findViewById(R.id.ll_con_pb);
		MyApplication.bt1 = this.startBtn;
		MyApplication.bt2 = this.settingBtn;
		MyApplication.bt3 = this.syncBt;
		MyApplication.showLoadingProgress();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 开启手机蓝牙
		// if (requestCode == REQUEST_ENABLE_BT
		// && resultCode == Activity.RESULT_CANCELED) {
		// finish();
		// return;
		// }

		switch (requestCode) {
		case 1:
			while ((resultCode == RESULT_OK) && (data != null)) {
				String str = data
						.getStringExtra("android.bluetooth.device.extra.DEVICE");
				mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(
						str);
//				Log.v(TAG, "...onActivityResultdevice.address==" + mDevice
//						+ "mserviceValue" + mService);
				//
				mService.connect(this.mDevice, false);
				searchBt.setText(getResources().getString(
						R.string.bluetooth_disconnected));
				MyApplication.mDevice = this.mDevice;
				MyApplication.connectBt = this.searchBt;
				MyApplication.getInstance().mService = this.mService;
			}
			break;

		case 2:
			if (resultCode == RESULT_OK) {
				Toast.makeText(this,
						getResources().getString(R.string.bluetooth_TypeOpen),
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
		Toast.makeText(this,
				getResources().getString(R.string.bluetooth_NotOpen),
				Toast.LENGTH_SHORT).show();

		// if (requestCode == REQUEST_SELECT_DEVICE && resultCode == RESULT_OK
		// && data != null) {
		// String str = data
		// .getStringExtra("android.bluetooth.device.extra.DEVICE");
		// mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(str);
		// mService.mBleController.connect(mDevice, false);
		// mService.connect(address)
		// searchBt.setText(getResources().getString(
		// R.string.bluetooth_disconnected));
		// MyApplication.mDevice = mDevice;
		// MyApplication.connectBt = searchBt;
		// MyApplication.getInstance().mService = mService;
		// return;
		// }
		// if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
		// Toast.makeText(this,
		// getResources().getString(R.string.bluetooth_TypeOpen),
		// Toast.LENGTH_SHORT).show();
		// } else {
		// Toast.makeText(this,
		// getResources().getString(R.string.bluetooth_NotOpen),
		// Toast.LENGTH_SHORT).show();
		//
		// }

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(MyApplication.isUSA){
			kmOrMileTv.setText(getString(R.string.distance_eunit));
		}else
			kmOrMileTv.setText(getString(R.string.distance_unit));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		Log.i("test","android destroy");
//		getApplication().unbindService(mServiceConnection);
		try {
			getApplicationContext().unregisterReceiver(deviceStateListener);
//			getApplicationContext().unregisterReceiver(receiver);
			getApplicationContext().stopService(
					new Intent(this, BluetoothLeService.class));
		} catch (Exception e) {
			// Log.e("======", "unKNOW");
		}
	}

//	static {
//		try {
//			System.loadLibrary("Ped_heart");// Ped_heart
//		} catch (Exception e) {
//			Log.i("APP", "HeartActivity" + e.toString());
//		} catch (Throwable e) {
//			Log.i("APP", "HeartActivity" + e.toString());
//		}
//	}

}
