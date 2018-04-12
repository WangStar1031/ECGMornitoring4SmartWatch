package com.YHJstyle.b005.j_style_Pro.b005.tool;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
//import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.HomeRoundView;

public class MyHandler extends Handler {

	public static LinearLayout mGoaldLinearLayout;
	public static LinearLayout mUserInfoLinearLayout;
	private Context context;
	byte[] list11 = new byte[16];

	public MyHandler(Context mContext) {
		this.context = mContext;
	}

	@Override 
	public void handleMessage(Message msg) {
		super.handleMessage(msg);

		switch (msg.what) {
		case 5:// 5
			Bundle bunlde = msg.getData();
			final BluetoothDevice bluetoothDevice = (BluetoothDevice) bunlde
					.getParcelable("android.bluetooth.device.extra.DEVICE");
			final int i1 = bunlde.getInt("NO_FOUND");
			MyApplication.yhDevice = bluetoothDevice.getAddress();
//			Log.v("MyHandler", "Device == " + MyApplication.yhDevice);
			MyApplication.getInstance().mService.scan(false);
			MyApplication.mDevice = BluetoothAdapter.getDefaultAdapter()
					.getRemoteDevice(bluetoothDevice.getAddress());
			MyApplication.getInstance().mService.connect(MyApplication.mDevice,
					false);

			Runnable runnable1 = new Runnable() {

				@Override
				public void run() {
					Iterator iterator = MyApplication.deviceList.iterator();
					int i = 0;
					if (iterator.hasNext()) {
						if (((BluetoothDevice) iterator.next()).getAddress()
								.equals(bluetoothDevice.getAddress())) {
							i = 1;
						}
						if (i == BluetoothLeService.DEVICE_SOURCE_SCAN) {
							MyApplication.deviceList.add(bluetoothDevice);
						}
						if (i1 == 0) {
							Toast.makeText(
									MyApplication.getInstance(),
									context.getResources().getString(
											R.string.no_ble_devices),
									Toast.LENGTH_SHORT).show();
						}

					}

				}
			};
			post(runnable1);
			break;
		case BluetoothLeService.HRP_CONNECT_MSG:// 1
			String str = msg.getData().getString(
					"android.bluetooth.device.extra.DEVICE");
//			Log.i("APP", "----str----" + str);
			BluetoothDevice device = BluetoothAdapter.getDefaultAdapter()
					.getRemoteDevice(str);
			Runnable runnable2 = new Runnable() {

				@Override
				public void run() {
					MyApplication.mState = MyApplication.HRP_PROFILE_CONNECTED;// 20
					// homepage界面 连接蓝牙按钮显示断开
					MyApplication.connectBt.setText(context.getResources()
							.getString(R.string.bluetooth_disconnected));
				}

			};
			post(runnable2);
//			Log.i("APP", "HRP_CONNECT_MSG======1");
			break;
		case BluetoothLeService.HRP_DISCONNECT_MSG:// 2
			Runnable runnable3 = new Runnable() {

				@Override
				public void run() {
					MyApplication.mState = MyApplication.HRP_PROFILE_DISCONNECTED;// 21
					Toast.makeText(
							MyApplication.getInstance(),
							MyHandler.this.context.getResources().getString(
									R.string.bluetooth_connectionfail),
							Toast.LENGTH_SHORT).show();
					MyApplication.homeboolean = true;// 多一重判断
					MyApplication.isBluetoothConnection = false;
					MyApplication.CloseLoadingProgress();
					MyApplication.mDevice = null;
				}
			};
			post(runnable3);
//			Log.i("APP", "HRP_CONNECT_MSG======2");
			break;
		case BluetoothLeService.HRP_READY_MSG:// 3
//			Log.i("APP", "mHandler.HRP_READY_MSG");
			Runnable runnable4 = new Runnable() {

				@Override
				public void run() {
					if (MyApplication.getInstance().mService != null) {
						// 获取计步器的目标步数
						MyApplication.getInstance().mService
								.writeDataToPedometer(
										BluetoothLeService.UUID_PEDOMETER_SERVICE,
										BluetoothLeService.UUID_PEDOMETER_SEND,
										75);
						// MyApplication.getInstance().mService
						// .writeDataToPedometer(
						// BluetoothLeService.UUID_PEDOMETER_SERVICE,
						// BluetoothLeService.UUID_PEDOMETER_SEND,
						// 66);

						// 连接状态
					}
					MyApplication.mState = MyApplication.STATE_READY;
					// MyApplication.STATE = true;
					MyApplication.homeboolean = false;// 多一重判断
					Toast.makeText(
							MyApplication.getInstance(),
							context.getResources().getString(
									R.string.already_connected),
							Toast.LENGTH_SHORT).show();
					// Log.i("APP",
					// "MyApplication.mDevice------"+MyApplication.mDevice.getAddress());
					// Log.i("APP",
					// "MyApplication.deviceAddrass------"+MyApplication.deviceAddrass);

					if (MyApplication.mDevice != null) {
						String str = MyApplication.mDevice.getAddress();
						// 判断第一次用时 不提示蓝牙设备跟换
						if (!TextUtils.isEmpty(MyApplication.deviceAddrass)
								&& (!MyApplication.deviceAddrass
										.equalsIgnoreCase(str))) {
							Toast.makeText(
									MyApplication.getInstance(),
									context.getResources().getString(
											R.string.bluetooth_updateData),
									Toast.LENGTH_SHORT).show();
						}
						if (!MyApplication.deviceAddrass.equalsIgnoreCase(str)) {

							SaveDataBase.saveUserInfo(
									MyApplication.getInstance(),
									"deviceAddrass", str);
							MyApplication.deviceAddrass = str;
							// 计步器跟换

							SaveDataBase.saveUserInfo(
									MyApplication.getInstance(), "date",
									"2013-07-14");
//							Log.i("APP", "deviceAddrass == " + str
//									+ "  deviceName == "
//									+ MyApplication.mDevice.getName());
						}
					}
				}

			};
			post(runnable4);
			break;
		case BluetoothLeService.HRP_VALUE_MSG:// 4
			final ArrayList list1;

			list1 = msg.getData().getStringArrayList("HOME_TIMES");
			// if((list1 != null) && (list1.size() >= 3)){

			post(new Runnable() {
				@Override
				public void run() {
					if (MyApplication.homepageStartOrStop) {
						TextView textView1 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_textView2);
						TextView textView2 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_textView4);
						TextView textView3 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_textView6);
						// Log.i("======", "textview1" + list1.get(0));
						TextView textView4 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_textView8);
						TextView textView5 = (TextView) MyApplication.dataLayout
								.findViewById(R.id.home_page_heart);
						if ((list1 != null) && (list1.size() >= 4)) {
							try {
								if (list1.size() == 5) {
									if (!TextUtils.isEmpty((CharSequence) list1
											.get(0))) {

										// if (list1.get(0) != null) {
										textView1.setText((CharSequence) list1
												.get(0));
										int i = Integer.valueOf(
												(String) list1.get(0))
												.intValue();
										if (TransmitData.setDataGoald == 0) {
											TransmitData.setDataGoald = 10000;
										}
										MyApplication.goaldData = i * 100
												/ TransmitData.setDataGoald;

										// Log.i("APP", "goaldData  "
										// + MyApplication.goaldData);
										if(MyApplication.goaldData >=100){
											MyApplication.goaldData = 100;
										}
										
										if (i > 10 + MyApplication.cursorSteps) {
											MyApplication.cursorSteps = i;
										}
										if ((MyApplication.goaldData != 0)
												&& (MyApplication.goaldData <= 100)) {
											HomeRoundView homeRoundView1 = new HomeRoundView(
													MyApplication.getInstance(),
													null,
													MyApplication.dataGoaldLayout,
													0, MyApplication.goaldData);// MyApplication.goaldData;
											MyApplication.firstRound = false;
											MyApplication.dataGoaldLayout
													.addView(homeRoundView1);

										}
										if (MyApplication.firstRound
												&& (!MyApplication.firstRound)) {
											HomeRoundView homeRoundView3 = new HomeRoundView(
													MyApplication.getInstance(),
													null,
													MyApplication.dataGoaldLayout,
													1, 100);
											MyApplication.dataGoaldLayout
													.addView(homeRoundView3);
										}

									} else {
										HomeRoundView homeRoundView1 = new HomeRoundView(
												MyApplication.getInstance(),
												null,
												MyApplication.dataGoaldLayout,
												0, MyApplication.goaldData);
										MyApplication.dataGoaldLayout
												.addView(homeRoundView1);
									}

									if (list1.get(1) != null) {
										textView2
												.setText(String
														.valueOf(new DecimalFormat(
																"#.#")
																.format(Float
																		.valueOf(
																				(String) list1
																						.get(1))

																		.floatValue() / 100.0F)));
									}
									if (list1.get(2) != null) {
										if (MyApplication.isUSA) {

											DecimalFormat dd = new DecimalFormat(
													"#.##");
											textView3
													.setText(String.valueOf(dd
															.format(0.621d * Float
																	.valueOf(
																			(String) list1
																					.get(2))
																	.floatValue() / 100f)));
										} else {
											textView3
													.setText(String
															.valueOf(
																	Math.round(1000.0F * (Float
																			.valueOf(
																					(String) list1
																							.get(2))
																			.floatValue() / 1000.0F)) / 100.0D)
															.trim());

										}
									}
									if (list1.get(3) != null) {
										String str1 = String.valueOf(Integer
												.valueOf((String) list1.get(3))
												.intValue() / 60);
										String str2 = String.valueOf(Integer
												.valueOf((String) list1.get(3))
												.intValue() % 60);
										if (str1.length() == 1) {
											str1 = "0" + str1;
										}
										if (str2.length() == 1) {
											str2 = "0" + str2;
										}
										textView4.setText(str1 + ":" + str2);
									}
									if (!TextUtils.isEmpty((CharSequence) list1
											.get(4))
											&& (!list1.get(4).equals("0"))) {
										// if(list1.get(4) != null){
										textView5.setText((CharSequence) list1
												.get(4));
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				}

			});
			// }
			break;
		case 101:
			final ArrayList list11;

			list11 = msg.getData().getStringArrayList("END_TIMES");
			post(new Runnable() {
				@Override
				public void run() {
					TextView textView1 = (TextView) MyApplication.dataLayout
							.findViewById(R.id.home_page_textView2);
					TextView textView2 = (TextView) MyApplication.dataLayout
							.findViewById(R.id.home_page_textView4);
					TextView textView3 = (TextView) MyApplication.dataLayout
							.findViewById(R.id.home_page_textView6);
					// Log.i("======", "textview1" + list1.get(0));
					TextView textView4 = (TextView) MyApplication.dataLayout
							.findViewById(R.id.home_page_textView8);
					TextView textView5 = (TextView) MyApplication.dataLayout
							.findViewById(R.id.home_page_heart);
					if ((list11 != null) && (list11.size() >= 4)) {
						try {
							if (list11.size() == 5) {
								if (!TextUtils.isEmpty((CharSequence) list11
										.get(0))) {

									textView1.setText((CharSequence) list11
											.get(0));
								}
								if (list11.get(1) != null) {
									textView2.setText(String
											.valueOf((String) list11.get(1)));
								}
								if (list11.get(2) != null) {
									textView3.setText((CharSequence) list11
											.get(2));
								}
								if (list11.get(3) != null) {
									textView4.setText((CharSequence) list11
											.get(3));
								}
								if (!TextUtils.isEmpty((CharSequence) list11
										.get(4))) {
									textView5.setText((CharSequence) list11
											.get(4));
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}

			});
			break;
		case BluetoothLeService.YH_SHOW_GOALD:// 6显示数据同步
			final int n = msg.getData().getInt("HOME_GOALD", 0);
			// Log.i("APP", "n = " + n);
			post(new Runnable() {

				@Override
				public void run() {
					if ((n > 100) && (n < 200)) {
						MyApplication.CloseLoadingProgress();
						Calendar calendar = Calendar.getInstance();
						String str = new SimpleDateFormat("yyyy-MM-dd")
								.format(calendar.getTime());
						String[] arr = str.split("-");
						MyHandler.this.context
								.getSharedPreferences("DateTime",
										Context.MODE_PRIVATE).edit()
								.putString("user_time", str).commit();
						Toast.makeText(
								MyApplication.getInstance(),
								context.getResources().getString(
										R.string.bluetooth_updateDataNow)
										+ ":"
										+ arr[0]
										+ "."
										+ arr[1]
										+ "."
										+ context.getResources().getString(
												R.string.month)
										+ arr[2]
										+ context.getResources().getString(
												R.string.day),
								Toast.LENGTH_SHORT).show();

					}
					if (n > 200) {
						Toast.makeText(
								MyApplication.getInstance(),
								MyHandler.this.context
										.getResources()
										.getString(
												R.string.bluetooth_updateDataNow)
										+ ":"
										+ MyApplication.getInstance().mPedoMeter.year
										+ "."
										+ MyApplication.getInstance().mPedoMeter.month
										+ "."
										+ MyHandler.this.context.getResources()
												.getString(R.string.month)
										+ MyApplication.getInstance().mPedoMeter.day
										+ MyHandler.this.context.getResources()
												.getString(R.string.day),
								Toast.LENGTH_SHORT).show();
						// Toast.makeText(
						// MyApplication.getInstance(),
						// context.getResources().getString(
						// R.string.bluetooth_updateDataNow)
						// + MyApplication.saveCurDate,
						// Toast.LENGTH_SHORT).show();
					}
				}
			});

			break;
		case BluetoothLeService.YH_USER_INFO:// 8
			final String[] arrayString = msg.getData().getStringArray(
					"GET_USER_INFO");
			post(new Runnable() {

				@Override
				public void run() {
					EditText editText1;
					EditText editText2;
					EditText editText3;
					EditText editText4;
					Button button1;
					Button button2;
					if ((arrayString != null) && (arrayString.length > 5)) {
						editText1 = (EditText) MyHandler.mUserInfoLinearLayout
								.findViewById(R.id.height_editText);
						// editText1 = (EditText)
						editText2 = (EditText) MyHandler.mUserInfoLinearLayout
								.findViewById(R.id.length_editText);
						editText3 = (EditText) MyHandler.mUserInfoLinearLayout
								.findViewById(R.id.weight_editText);
						editText4 = (EditText) MyHandler.mUserInfoLinearLayout
								.findViewById(R.id.age_editText);
						button1 = (Button) MyHandler.mUserInfoLinearLayout
								.findViewById(R.id.setting_male_bt);
						button2 = (Button) MyHandler.mUserInfoLinearLayout
								.findViewById(R.id.setting_female_bt);

						try {
							if (arrayString[2] != null) {
								editText1.setText(arrayString[2]);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							if (arrayString[3] != null) {
								editText2.setText(arrayString[3]);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							if (arrayString[4] != null) {
								editText3.setText(arrayString[4]);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							if (arrayString[1] != null) {
								editText4.setText(arrayString[1]);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						if (Integer.parseInt(arrayString[0]) != 0) {
							button1.setBackgroundResource(R.drawable.setting_male);
							button2.setBackgroundResource(R.drawable.female_pressed);
						} else {
							button1.setBackgroundResource(R.drawable.male_pressed);
							button2.setBackgroundResource(R.drawable.setting_female);

						}
					}
				}
			});
			break;
		case BluetoothLeService.YH_SET_GET_GOALD:// 7个人目标步数
			final ArrayList list2 = msg.getData().getStringArrayList(
					"SETTING_GOALD");
			post(new Runnable() {

				@Override
				public void run() {
					if ((list2 != null) && (list2.get(0) != null)) {
						TransmitData.setDataGoald = Integer.valueOf(
								(String) list2.get(0)).intValue();
						if (mGoaldLinearLayout != null) {
							((EditText) (mGoaldLinearLayout
									.findViewById(R.id.goald_textView)))
									.setText((CharSequence) list2.get(0));
							// Log.i("APP", "setText " + list2.get(0));
						}
					}
				}
			});
			break;
		case BluetoothLeService.YH_SET_USER_INFO:// 9

			Toast.makeText(
					MyApplication.getInstance(),
					MyApplication.getInstance().getResources()
							.getString(R.string.save_informationSuccess),
					Toast.LENGTH_LONG).show();// 41

			break;
		case BluetoothLeService.YH_SET_TIME:// 设置时间10
			Toast.makeText(
					MyApplication.getInstance(),
					MyApplication.getInstance().getResources()
							.getString(R.string.set_timeSuccess),
					Toast.LENGTH_LONG).show();
			break;
		case BluetoothLeService.YH_SET_DEVICE_FATIGUE:// 13
			Toast.makeText(
					MyApplication.getInstance(),
					MyApplication.getInstance().getResources()
							.getString(R.string.set_remindSuccess),
					Toast.LENGTH_LONG).show();//

			break;
		case BluetoothLeService.YH_SET_TARGET:// 12

			Toast.makeText(
					MyApplication.getInstance(),
					MyApplication.getInstance().getResources()
							.getString(R.string.set_stepSuccess),
					Toast.LENGTH_LONG).show();// 40
			break;
		case BluetoothLeService.YH_SET_DEVICE_CLOCK:// 14

			Toast.makeText(
					MyApplication.getInstance(),
					MyApplication.getInstance().getResources()
							.getString(R.string.set_clockSuccess),
					Toast.LENGTH_LONG).show();// 40

			break;
		case BluetoothLeService.YH_SET_DEVICE_ID:// 11
			Toast.makeText(
					MyApplication.getInstance(),
					MyApplication.getInstance().getResources()
							.getString(R.string.set_nameSuccess),
					Toast.LENGTH_LONG).show();
			break;
		case 16:
			Toast.makeText(
					MyApplication.getInstance(),
					MyApplication.getInstance().getResources()
							.getString(R.string.popup_finsh),
					Toast.LENGTH_SHORT).show();

			break;
		case 17:
			
			break;
		case BluetoothLeService.YH_SET_DEVICE_FACTORY:// 18

			Toast.makeText(
					MyApplication.getInstance(),
					MyApplication.getInstance().getResources()
							.getString(R.string.set_factorySuccess),
					Toast.LENGTH_LONG).show();// 49
			break;
		case BluetoothLeService.YH_HEART_RETE:// 99
			// byte[] lisss = msg.getData().getByteArray(key)
			final byte[] list99 = (byte[]) msg.getData().getSerializable(
					"HEART_RATE");
			// final byte[] list99 = msg.getData().getByteArray("HEART_RATE");
			// final String[] list131 =
			// msg.getData().getStringArray("HEART_RATE");

			post(new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent(
							"com.youhong.ACTION_DATA_AVAILABLE");
					intent.putExtra("com.youhong.EXTRA.DATA", list99);
					MyApplication.getInstance().mService.sendBroadcast(intent);
					// broadcastUpdata("com.youhong.ACTION_DATA_AVAILABLE",
					// list99);

				}
			});

			// Intent intent99 = new Intent();
			// MyApplication.getInstance().mService.sendBroadcast(intent99);
			break;
		}
	}

	private void broadcastUpdata(String string, byte[] list99) {
		Intent intent = new Intent(string);
		intent.putExtra("com.youhong.EXTRA.DATA", list99);
		this.context.sendBroadcast(intent);
		// Log.i("APP", "====BroadCastUpdata====");
	}

}
