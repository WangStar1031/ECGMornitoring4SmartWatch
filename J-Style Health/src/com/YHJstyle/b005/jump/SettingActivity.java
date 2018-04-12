package com.YHJstyle.b005.jump;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.util.Log;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.SettingView;
import com.YHJstyle.b005.j_style_Pro.b005.tool.BluetoothLeService;
import com.YHJstyle.b005.j_style_Pro.b005.tool.DBitmapHelper;
import com.YHJstyle.b005.j_style_Pro.b005.tool.ImageUtils;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyBrostReiver;
import com.YHJstyle.b005.j_style_Pro.b005.tool.TransmitData;

public class SettingActivity extends Activity implements OnClickListener {
	private static final int BASIC_TYPE = 0;
	private static final int GOAL_TYPE = 1;
	private static final String TAG = "SettingActivity";
	private static final int PEDOMETER = 2;
	private static final int REQUEST_SELECT_DEVICE = 100;
	private static final int STATE_READY = 10;
	private static final String TAB_BASIC = "basic";
	private static final String TAB_GOALD = "goald";
	private static final String TAB_PEDOMETER = "pedometer";
	public static int clearDay = 0;
	public static boolean isConnection = false;
	private LinearLayout basicLayout;

	private MyBrostReiver deviceStateListener;
	BitmapDrawable bd;
	private Button bleBinging;
	private Button four_btn;
	// private MyService myService;
	private BluetoothLeService myService;
	private LinearLayout goaldLayout;
	private ImageView imagebtn;
	boolean isTime = false;

	long start, end;

	// private Context context;
	// public static final String IMAGE_PATH = "jump";
	// private static String localTempImageFileName = "";
	// public static final File FILE_SDCARD = Environment
	// .getExternalStorageDirectory();
	// public static final File FILE_LOCAL = new File(FILE_SDCARD,IMAGE_PATH);
	// public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
	// "images/screenshots");

	// 设置链接服务
	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			myService = null;
		}

		// 应用中获取实例
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// myService = ((MyService.LocalBinder) service).getService();
			myService = ((BluetoothLeService.LocalBinder) service).getService();
			MyApplication.getInstance().mService = myService;

		}
	};

	private SettingView mSettingBasicView;
	private TabHost mTabhost;
	private int mType = 0;
	private LinearLayout pedometerLayout;
	Bitmap photo;
	private RadioGroup radioGroup;
	private Button resetting;
	private LinearLayout settingLayout;
	private Button tw_btn;

	Drawable drawable;
	// 截取的图片保存
	private static final File tempFile = new File(
			Environment.getExternalStorageDirectory() + "/tempFile.jpg");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_page);
		initView();
	}

	private void initView() {
		// Intent intent = new Intent(this, MyService.class);
		Intent intent = new Intent(this, BluetoothLeService.class);
		getApplicationContext().startService(intent);
		getApplicationContext().bindService(intent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
		IntentFilter intentFilter = new IntentFilter();
		// 添加广播过滤器
		intentFilter
				.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
		intentFilter
				.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
		getApplicationContext().registerReceiver(deviceStateListener,
				intentFilter);
		((Button) findViewById(R.id.back_button)).setOnClickListener(this);
		((Button) findViewById(R.id.reset_button)).setOnClickListener(this);
		bleBinging = (Button) findViewById(R.id.setting_bingbtn);
		resetting = (Button) findViewById(R.id.setting_restart);
		bleBinging.setOnClickListener(this);
		resetting.setOnClickListener(this);
		imagebtn = (ImageView) findViewById(R.id.imageView11);
		imagebtn.setOnClickListener(this);
		tw_btn = (Button) findViewById(R.id.tw_btn);
		four_btn = (Button) findViewById(R.id.four_btn);
		tw_btn.setOnClickListener(this);
		four_btn.setOnClickListener(this);
		// 蓝牙已断开链接! 缺少判断
		if ((MyApplication.mState != STATE_READY)
				&& (MyApplication.homeboolean)) {
			Toast.makeText(
					this,
					MyApplication.getInstance().getResources()
							.getString(R.string.bluetooth_connectionfail),
					Toast.LENGTH_SHORT).show();

		}
		initTab();
		bd = (BitmapDrawable) imagebtn.getBackground();
		photo = bd.getBitmap();
		SharedPreferences sharedPreferences = getSharedPreferences("user_info",
				Context.MODE_PRIVATE);
		String str1 = sharedPreferences.getString("personalYear", "");
		String str2 = sharedPreferences.getString("personalMonth", "");
		String str3 = sharedPreferences.getString("personalDay", "");
		String str4 = sharedPreferences.getString("settingsTime", "");
		// if ("false".equals(str4)) {
		// tw_btn.setBackgroundColor(Color.argb(222, 40, 165, 223));
		// four_btn.setBackgroundColor(Color.argb(200, 255, 255, 255));
		// } else if ("".equals(str4)) {
		// four_btn.setBackgroundColor(Color.argb(222, 40, 165, 223));
		// tw_btn.setBackgroundColor(Color.argb(200, 255, 255, 255));
		// }
		if (MyApplication.isUSA) {
			tw_btn.setBackgroundColor(Color.argb(222, 40, 165, 223));
			four_btn.setBackgroundColor(Color.argb(200, 255, 255, 255));
		} else {
			four_btn.setBackgroundColor(Color.argb(222, 40, 165, 223));
			tw_btn.setBackgroundColor(Color.argb(200, 255, 255, 255));
		}
		photo = getDiskBitmap(Environment.getExternalStorageDirectory()
				+ "/tempFile.jpg");
		if (photo == null) {
			imagebtn.setBackground(ImageUtils.ControlBitMap(
					SettingActivity.this, R.drawable.head_bg));
			;
		} else {
			imagebtn.setImageBitmap(photo);
		}

	}

	private Bitmap getDiskBitmap(String pathString) {
		Bitmap bitmap = null;
		try {
			File file = new File(pathString);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(pathString);
			}
		} catch (Exception e) {
			// Log.i("APP", e.toString());
		}
		return bitmap;
	}

	private void initTab() {
		SharedPreferences localSharedPreferences = getSharedPreferences(
				"USERS_INFO", 0);
		String str1 = localSharedPreferences.getString("user_height", "");
		String str2 = localSharedPreferences.getString("user_weight", "");
		String str3 = localSharedPreferences.getString("user_step", "");
		String str4 = localSharedPreferences.getString("user_age", "");
		String str5 = localSharedPreferences.getString("user_sex", "");
		String str6 = localSharedPreferences.getString("user_datagoald", "");
		MyApplication.userHeight = str1;
		MyApplication.userWeight = str2;
		MyApplication.userSteps = str3;
		MyApplication.userAge = str4;
		MyApplication.userSex = str5;
		if (!str6.equals("")) {
			TransmitData.setDataGoald = Integer.parseInt(str6);
		}

		mTabhost = (TabHost) findViewById(R.id.setting_tabs);
		mTabhost.setup();
		basicLayout = (LinearLayout) findViewById(R.id.basic_layout);
		basicLayout.setBackground(ImageUtils.ControlBitMap(
				SettingActivity.this, R.drawable.setting_bg));
		goaldLayout = (LinearLayout) findViewById(R.id.goald_layout);
		goaldLayout.setBackground(ImageUtils.ControlBitMap(
				SettingActivity.this, R.drawable.setting_bg));
		pedometerLayout = (LinearLayout) findViewById(R.id.pedometer_layout);
		pedometerLayout.setBackground(ImageUtils.ControlBitMap(
				SettingActivity.this, R.drawable.setting_bg));
		addView();
		mTabhost.addTab(mTabhost.newTabSpec("basic").setIndicator("tab1")
				.setContent(R.id.basic_layout));
		mTabhost.addTab(mTabhost.newTabSpec("goald").setIndicator("tab2")
				.setContent(R.id.goald_layout));
		mTabhost.addTab(mTabhost.newTabSpec("pedometer").setIndicator("tab3")
				.setContent(R.id.pedometer_layout));

		radioGroup = (RadioGroup) findViewById(R.id.setting_radiogroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_basic:
					mTabhost.setCurrentTabByTag("basic");
					if (mSettingBasicView != null) {
						basicLayout.removeAllViews();
					}
					mSettingBasicView = new SettingView(SettingActivity.this, 0);
					basicLayout.addView(mSettingBasicView);

					break;
				case R.id.radio_goald:
					mTabhost.setCurrentTabByTag("goald");

					break;
				case R.id.radio_pedometer:
					mTabhost.setCurrentTabByTag("pedometer");
					break;
				default:
					break;
				}

			}
		});
	}

	private void addView() {
		mSettingBasicView = new SettingView(this, BASIC_TYPE);
		basicLayout.addView(mSettingBasicView);
		goaldLayout.addView(new SettingView(this, GOAL_TYPE));
		pedometerLayout.addView(new SettingView(this, PEDOMETER));

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		BluetoothDevice bluetoothDevice;
		if ((requestCode == 100) && (resultCode == RESULT_OK)) {
			if ((myService != null) && (data != null)) {
				String str = data
						.getStringExtra("android.bluetooth.device.extra.DEVICE");
				// Log.i("APP", "SettingActivity   str =" + str);
				bluetoothDevice = BluetoothAdapter.getDefaultAdapter()
						.getRemoteDevice(str);
				if ((myService != null) && (bluetoothDevice != null)) {
					myService.connect(bluetoothDevice, false);
					MyApplication.mDevice = bluetoothDevice;
					getSharedPreferences("bluetooth_info", Context.MODE_PRIVATE)
							.edit()
							.putString("bluetooth_device",
									bluetoothDevice.getAddress()).commit();
					Toast.makeText(
							this,
							MyApplication.getInstance().getResources()
									.getString(R.string.set_BingingSuccess),
							Toast.LENGTH_LONG).show();
					if (MyApplication.getInstance().mService == null) {
						MyApplication.getInstance().mService = myService;
					}
					MyApplication.mState = MyApplication.STATE_READY;
					isConnection = true;
				}
			}
		} else if ((requestCode == 11) && (resultCode == RESULT_OK)) {
			if (data != null) {

				startPhotoZoom(data.getData());
			}
		} else if ((requestCode == 13) && (resultCode == RESULT_OK)) {
			if (data != null) {
				setHeadPicture(data);
			}
		} else if (requestCode == 10) {
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/SPA Tracker.jpg");
			startPhotoZoom(Uri.fromFile(temp));
		}

	}

	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 13);

	}

	private void setHeadPicture(Intent picdata) {

		try {
			Bundle extras = picdata.getExtras();
			if (extras != null) {
				photo = extras.getParcelable("data");
			}
			// if(photo != null){
			// photo.recycle();
			// }
			imagebtn.setImageBitmap(photo);
			savePreviewBitmap();
			// ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
			// photo.compress(Bitmap.CompressFormat.PNG, 100, outPutStream);
		} catch (Exception e) {
			// Log.i("APP", "toPicture" + e.toString());
		}
	}

	private void savePreviewBitmap() {
		if (tempFile.exists()) {
			tempFile.delete();
		}
		try {
			tempFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(tempFile);
		} catch (Exception e) {
			// TODO: handle exception
		}
		photo.compress(Bitmap.CompressFormat.JPEG, 100, fout);
		try {
			fout.flush();
			fout.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_bingbtn:// 绑定

			Intent intent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.imageView11:// 头像

			String[] items = { getString(R.string.take_photo),
					getString(R.string.choose_photo) };
			new AlertDialog.Builder(this).setTitle(getString(R.string.photo))
					.setItems(items, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								Intent intent = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
										.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												"SPA Tracker.jpg")));
								startActivityForResult(intent, 10);
								break;
							case 1:
								Intent intent4 = new Intent(
										"android.intent.action.PICK", null);
								intent4.setDataAndType(
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
										"image/*");
								startActivityForResult(intent4, 11);
							default:
								break;
							}
						}
					}).show();
			break;
		case R.id.tw_btn:// 12时间
			tw_btn.setBackgroundColor(Color.argb(200, 61, 184, 233));
			four_btn.setBackgroundColor(Color.argb(200, 255, 255, 255));
			MyApplication.isUSA = true;
			// MyApplication.kmORIm = true;
			isTime = false;
			byte[] arr = new byte[16];
			arr[0] = 55;// 0x37
			arr[1] = 0;
			int k = arr[0];
			for (int m = 0; m < 14; m++) {
				k = (byte) (k + arr[m + 1]);
			}
			arr[15] = (byte) (k & 0xFF);
			if ((MyApplication.getInstance().mService != null)
					&& (MyApplication.isBluetoothConnection)) {
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND, arr);
				byte[] arrbyte = new byte[16];
				arrbyte[0] = 15;
				arrbyte[1] = 1;
				int a = (byte) (arrbyte[0] + arrbyte[1]);
				for (int q = 2; q < 16; q++) {
					arrbyte[q] = 0;
				}
				arrbyte[15] = (byte) (a & 0xFF);
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND, arrbyte);
			}
			if (mSettingBasicView != null) {
				basicLayout.removeAllViews();
			}
			mSettingBasicView = new SettingView(SettingActivity.this, 0);
			basicLayout.addView(mSettingBasicView);
			break;
		case R.id.four_btn:// 24 默认为英制 英里
			four_btn.setBackgroundColor(Color.argb(200, 61, 184, 233));
			tw_btn.setBackgroundColor(Color.argb(200, 255, 255, 255));
			MyApplication.isUSA = false;
			// MyApplication.kmORIm = false;
			isTime = true;
			byte[] arr1 = new byte[16];
			arr1[0] = 55;// 0x37
			arr1[1] = 1;
			int j = arr1[0];
			for (int i = 0; i < 14; i++) {
				j = (byte) (j + arr1[i + 1]);
			}
			arr1[15] = (byte) (j & 0xFF);
			if ((MyApplication.getInstance().mService != null)
					&& (MyApplication.isBluetoothConnection)) {
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND, arr1);

				byte[] arrbyte2 = new byte[16];
				arrbyte2[0] = 15;
				arrbyte2[1] = 0;
				int b = (byte) (arrbyte2[0] + arrbyte2[1]);
				for (int q = 2; q < 16; q++) {
					arrbyte2[q] = 0;
				}
				arrbyte2[15] = (byte) (b & 0xFF);
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND, arrbyte2);
			}
			if (mSettingBasicView != null) {
				basicLayout.removeAllViews();
			}
			mSettingBasicView = new SettingView(SettingActivity.this, 0);
			basicLayout.addView(mSettingBasicView);
			
			break;
		case R.id.setting_restart:// 重设 删除29天的数据
			if ((MyApplication.isBluetoothConnection)
					&& (MyApplication.getInstance().mService != null)) {
				showDialog(SettingActivity.this);

			}
			if (!MyApplication.isBluetoothConnection) {
				Toast.makeText(SettingActivity.this,
						R.string.home_page_blutooth_dialog, Toast.LENGTH_LONG)
						.show();
			}

			break;
		case R.id.back_button:// 图标 返回
			finish();
			break;
		case R.id.reset_button:// 保存
			if (MyApplication.heightEt.getText().toString() != null
					&& MyApplication.weightEt.getText().toString() != null
					&& MyApplication.lengthEt.getText().toString() != null
					&& MyApplication.agetEt.getText().toString() != null) {
				int i = 0;
				SharedPreferences spFerences = getSharedPreferences(
						"USERS_INFO", 0);
				spFerences
						.edit()
						.putString("user_height",
								MyApplication.heightEt.getText().toString())
						.commit();
				spFerences
						.edit()
						.putString("user_weight",
								MyApplication.weightEt.getText().toString())
						.commit();
				spFerences
						.edit()
						.putString("user_step",
								MyApplication.lengthEt.getText().toString())
						.commit();
				spFerences
						.edit()
						.putString("user_age",
								MyApplication.agetEt.getText().toString())
						.commit();
				if (!MyApplication.isMale) {

					i = 0;
//					Log.i("time", "女性");
				} else {
//					Log.i("time", "男性");

					i = 1;
				}
				spFerences.edit().putString("user_sex", String.valueOf(i))
						.commit();
				
				//0.453 llbs = 0.4536kg  inches = 2.54cm 
				
				if ((MyApplication.getInstance().mService != null)
						&& (MyApplication.isBluetoothConnection)) {

					TransmitData.userInfo.add(MyApplication.heightEt.getText()
							.toString());
//					Log.i("time", "height 高 =="+MyApplication.heightEt.getText().toString());
					TransmitData.userInfo.add(MyApplication.weightEt.getText()
							.toString());
//					Log.i("time", "weight 重 =="+MyApplication.weightEt.getText().toString());
					TransmitData.userInfo.add(MyApplication.lengthEt.getText()
							.toString());
					TransmitData.userInfo.add(MyApplication.agetEt.getText()
							.toString());
					TransmitData.userInfo.add(String.valueOf(i));
					Log.i("time", "男性是1" + i);
					MyApplication.getInstance().mService.writeDataToPedometer(
							BluetoothLeService.UUID_PEDOMETER_SERVICE,
							BluetoothLeService.UUID_PEDOMETER_SEND, 2);
				}
			}
			// Toast.makeText(SettingActivity.this,
			// getResources().getString(R.string.userinfo_save),
			// Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	private void showDialog(Context context) {
		Builder builder = new Builder(context);
		builder.setTitle(R.string.popup_title).setMessage(R.string.popup_text);
		builder.setNegativeButton(getResources().getString(R.string.popup_yes),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						byte[] arr = new byte[16];
						arr[0] = 18;
						int i = arr[0];
						// arr[1] = (byte) SettingActivity.clearDay;
						// for (int j = 1; j < arr.length; j++) {
						// i = (byte) (i + arr[j]);
						// }
						for (int j = 1; j < 15; j++) {
							arr[j] = 0;
						}
						arr[15] = (byte) (i & 0xFF);
						if (MyApplication.getInstance().mService != null) {
							MyApplication.getInstance().mService
									.writeDataToPedometer(
											BluetoothLeService.UUID_PEDOMETER_SERVICE,
											BluetoothLeService.UUID_PEDOMETER_SEND,
											arr);
						}
					}
				});
		builder.setPositiveButton(getResources().getString(R.string.popup_no),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	private byte[] getBitmapView(String str1, String str2, String str3) {
		byte[] arr = new byte[1024];
		SQLiteDatabase database = new DBitmapHelper(this).getWritableDatabase();
		Cursor cursor = database.query("bitmaptbl",
				new String[] { "name,year,month,day,obj" }, "name=?",
				new String[] { "Jack" }, null, null, null);
		// while (cursor.moveToNext()) {
		// arr = cursor.getBlob(cursor.getColumnIndex("obj"));
		// }
		if (cursor.moveToFirst()) {
			do {
				arr = cursor.getBlob(cursor.getColumnIndex("obj"));

			} while (cursor.moveToNext());
		}
		cursor.close();

		database.close();

		return arr;
	}

	@Override
	protected void onResume() {

		if (MyApplication.isUSA) {
			tw_btn.setBackgroundColor(Color.argb(222, 40, 165, 223));
			four_btn.setBackgroundColor(Color.argb(200, 255, 255, 255));
		} else {
			four_btn.setBackgroundColor(Color.argb(222, 40, 165, 223));
			tw_btn.setBackgroundColor(Color.argb(200, 255, 255, 255));
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// long start = System.currentTimeMillis();
		getApplication().unbindService(mServiceConnection);
		// long end = System.currentTimeMillis();
		// Log.i("TAG", ""+(end -start));
		// start = System.currentTimeMillis();
		try {

			getApplication().unregisterReceiver(deviceStateListener);
			getApplication().stopService(
					new Intent(this, BluetoothLeService.class));
		} catch (Exception e) {
			// Log.i("APP", "SettingActivity onDestroy	=" + e.toString());
		}

		// end = System.currentTimeMillis();
		// Log.i("TAG", ""+(end -start));
	}
}
