package com.YHJstyle.b005.jump;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.spec.MGF1ParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.CharView;
import com.YHJstyle.b005.b005.view.HearView;
import com.YHJstyle.b005.j_style_Pro.b005.tool.BluetoothLeService;
import com.YHJstyle.b005.j_style_Pro.b005.tool.DataSend;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyBrostReiver;

public class HeartActivity extends Activity implements OnClickListener {
	//Changed by Wang
	public DataSend sender = new DataSend();
	private short[] sendingDataBuff = new short[251];
	
	private Button back_btn;
	private Button heart_btn;
	private boolean isOpen;
	private HearView heartView;
	private RelativeLayout relative;

	private Context context;
	private TextView tv;// 心率
	public byte[] dataBuffer = new byte[16];

	public Handler mLoopHandler;

	// public LooperThread savedThread;

	private int displayLength = 3000;// 宽屏展示的长度
	public short mHeartRate;
	private final int fs = 250;//

	private final int DEAULT_ALGO_SIZE = fs * 2;

	private final short datasPerSec = fs;
	private static final int TransferSize = 20;
	private short[] displayDataBuff;// 展示数据

	private final int bufferSize = fs * 5 * 3;// 15 seconds data
	private short[] ecgBuffer;
	public short winOffset = 0;
	public int storedOffset = 0;
	public int getOffset = 0;
	public int dispOffset = 0;
	public boolean firstLoad = true;
	public short[] tempBuf;
	public short[] hearrate;

	public int saveOffsetA = 0;
	public int saveOffsetB = 0;

	public short[] savedBufA;
	public short[] savedBufB;
	private short[] ecgData;
	// 启动一个线程开始
	public Runnable startCapture;

	public boolean abFlag = false;

	public static float ecg_yscale = 1;
	public static float ecg_xscale = 1;
	private final float adcMax = 1024;

	public static 	int width=0;
	public static  int heigh=0;
	private double scale = 1;
	
	File file;

	String filename = "heart";
	String SaveFileName = "";
	private MyBrostReiver deviceStateListener;
	private BluetoothLeService myService;

	
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {

	 @Override
		public void onServiceDisconnected(ComponentName name) {
			myService = null;
		}

		// 应用中获取实例
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
		//	// myService = ((MyService.LocalBinder) service).getService();
			myService = ((BluetoothLeService.LocalBinder) service).getService();
			MyApplication.getInstance().mService = myService;

		}
	};

	// 保存线程
	// class LooperThread extends Thread {
	// private Looper mLooper;
	//
	// public LooperThread(String name) {
	// super(name);
	// }
	//
	// @Override
	// public void run() {
	// Looper.prepare();
	// mLooper = Looper.myLooper();
	//
	// mLoopHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 1:
	// refreshUI();
	// break;
	//
	// default:
	// break;
	// }
	// }
	// };
	// }
	// }

	// private Handler handler = new Handler(){
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 1:
	// refreshUI();
	// break;
	//
	// default:
	// break;
	// }
	// }
	// };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

//		setContentView(R.layout.heart_new);
		setContentView(R.layout.heart_new_two);
		initView();

	}

	private void initView() {
		Intent intentService = new Intent(this, BluetoothLeService.class);
		//getApplicationContext().startService(intentService);
		getApplicationContext().bindService(intentService, mServiceConnection,
				0);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter
				.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
		intentFilter
				.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
		// intentFilter.addAction("com.youhong.ACTION_DATA_AVAILABLE");	
		
		deviceStateListener = new MyBrostReiver();
		getApplicationContext().registerReceiver(deviceStateListener,
				intentFilter);
		IntentFilter updateIntent = new IntentFilter();
		updateIntent.addAction("com.youhong.ACTION_DATA_AVAILABLE");
		getApplicationContext().registerReceiver(on, updateIntent);

		back_btn = (Button) findViewById(R.id.heart_btn);
		back_btn.setOnClickListener(this);
		heart_btn = (Button) findViewById(R.id.heart_start);
		heart_btn.setOnClickListener(this);
		relative = (RelativeLayout) findViewById(R.id.heart_relative);
		relative.addView(new HearView(this, null));
		heartView = (HearView) findViewById(R.id.heartView);
		tv = (TextView) findViewById(R.id.text_ecg_val);//
		mHeartRate = 0;// 心率
		displayDataBuff = new short[displayLength + 1];

		ecgBuffer = new short[bufferSize + 1];
		// hearrate = new short[2];
		tempBuf = new short[501];
		hearrate = new short[2];
		savedBufA = new short[1024];
		savedBufB = new short[1024];
		
		ViewTreeObserver Observer = heartView.getViewTreeObserver();
		
		Observer.addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				width = heartView.getMeasuredWidth();
				heigh = heartView.getMeasuredHeight();
				scale = (double) heigh/399;
				return true;
			}
		});
		
		EcgNative.EcgIni(50);// 初始化
		
	}

	BroadcastReceiver on = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("com.youhong.ACTION_DATA_AVAILABLE")) {
				// Log.i("APP", "====Receive======");

				final byte[] txValue = intent
						.getByteArrayExtra("com.youhong.EXTRA.DATA");
//				Log.i("APP", "TxValue[]" + Arrays.toString(txValue));
//				 Save(txValue);
				short w = (short)(txValue[0] & 0xFF);
				if(w != 0xA9) return;
				for (int i = 0; i <= 15; i++) {
					dataBuffer[i] = txValue[i];
				}

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
//						short dataMagic = 0;
						short ecgVal = 0;
						for (int j = 1; j < 15; j++) {
							ecgVal = (short) (0xFF & dataBuffer[j]);

							if ((!abFlag) && (saveOffsetA < 1024)
									&& savedBufA != null) {
								savedBufA[saveOffsetA++] = ecgVal;
							}
							if (abFlag && saveOffsetB < 1024
									&& savedBufB != null) {
								savedBufB[saveOffsetB++] = ecgVal;
							}
							if (saveOffsetA >= 1024) {
								abFlag = true;
							}
							if (saveOffsetB >= 1024) {
								abFlag = false;
							}
							EcgNative.EcgInserData(ecgVal);
							//
							int ret = EcgNative.EcgProcessData(tempBuf,
									hearrate);
							long end = System.currentTimeMillis();
							if (ret == 1) {
								mHeartRate = hearrate[0];
								for (int k = 0; k < 500; k++) {
//									ecgBuffer[storedOffset] = (short) (tempBuf[k]/3);
									ecgBuffer[storedOffset] = (short) (tempBuf[k]/2 * scale);
									if (storedOffset >= bufferSize - 1)
										storedOffset = 0;
									else
										storedOffset++;
									if (storedOffset % datasPerSec == 0) {
										refreshUI();

									}
								}
							}
						}
					}
				});
			}
		}

	};

	// 保存数据
	private void Save(byte[] txValue) {
		String data = txValue.toString();
		try {
			OutputStream fos = null;
			File dPath = new File(SaveFileName);
			fos = new FileOutputStream(dPath, true);
			DataOutputStream out = new DataOutputStream(fos);

			out.write(data.getBytes());
			out.close();
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * */
	public void refreshUI() {
//		Log.i("Test", "==进入更新==");
		forwardData();
		updateGraphic();
		if (mHeartRate != 0xFF) {
			String str = Integer.toString(mHeartRate);
			tv.setText(str);
		}
		relative.removeAllViews();
		// heartView.postInvalidate();
		relative.addView(heartView);
	}

	private void updateGraphic() {
//		Log.i("Test", "进入updateGraphic");
		float y;
		for (int i = 0; i < displayLength; i++) {
			y = displayDataBuff[i];
			y = (64 * 30 * y * ecg_yscale) /(768);//  adcMax * 8
//			heartView.y[i] = 192 - y;
//			heartView.x[i] = i * 32 * 5 * ecg_xscale  / 250;
			heartView.y[i] = heigh/2+32 - y;
			heartView.x[i] = i * 32 * 5 * ecg_xscale  / 250;
		}

	}

	private void forwardData() {
//		Log.i("Test", "进入forwardData	");
		int i;
		for (i = datasPerSec; i < displayLength; i++) {
			displayDataBuff[i - datasPerSec] = displayDataBuff[i];
		}
		// Changed by Wang.
		for (i = displayLength - datasPerSec; i < displayLength; i++) {
			displayDataBuff[i] = ecgBuffer[getOffset];
			sendingDataBuff[i - displayLength + datasPerSec] = ecgBuffer[getOffset];
			if (getOffset >= bufferSize - 1)
				getOffset = 0;
			else
				getOffset++;
		}
		sender.SendDataToServer(sendingDataBuff, datasPerSec, mHeartRate);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.heart_btn:
			finish();

			break;
		case R.id.heart_start:

			if (!isOpen) {
				if (MyApplication.getInstance().mService != null) {
					MyApplication.getInstance().mService.writeDataToPedometer(
							BluetoothLeService.UUID_PEDOMETER_SERVICE,
							BluetoothLeService.UUID_PEDOMETER_SEND,153);
					isOpen = true;
					heart_btn.setText("stop");

//					Log.i("APP", "点击进入了");
				} else {
//					Log.i("APP", "无服务");
				}
			} else {
				if (MyApplication.getInstance().mService != null) {
					MyApplication.getInstance().mService.writeDataToPedometer(
							BluetoothLeService.UUID_PEDOMETER_SERVICE,
							BluetoothLeService.UUID_PEDOMETER_SEND, 152);
					isOpen = false;
					heart_btn.setText("start");

					for (int f = 0; f < displayLength; f++)
						displayDataBuff[f] = 0;
				}
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getApplication().unbindService(mServiceConnection);
		try {
			MyApplication.getInstance().mService.stopSelf();
			getApplication().unregisterReceiver(deviceStateListener);
			getApplication().unregisterReceiver(on);
			//getApplication().stopService(
			//		new Intent(this, BluetoothLeService.class));
			getApplicationContext().unbindService(mServiceConnection);
			displayDataBuff = null;
			ecgBuffer = null;
			tempBuf = null;
			heartView.x = null;
			heartView.y = null;
		} catch (Exception e) {

		}
	}

	static {
		ecg_xscale = 1f;
		try {
			System.loadLibrary("Ped_heart");// Ped_heart
		} catch (Exception e) {
//			Log.i("APP", "HeartActivity" + e.toString());
		} catch (Throwable e) {
//			Log.i("APP", "HeartActivity" + e.toString());
		}
	}

}