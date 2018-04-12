/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.YHJstyle.b005.j_style_Pro.b005.tool;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.YHJstyle.b005.R;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
//import android.util.Log;
import android.widget.Toast;

/**
 * Service for managing connection and data communication with a GATT server
 * hosted on a given Bluetooth LE device.
 */
public class BluetoothLeService extends Service {
	private final static String TAG = BluetoothLeService.class.getSimpleName();
	private final static String SACHINTAG = "SACHIN";

	//
	public static final int DEVICE_SOURCE_SCAN = 0;// 设备扫描

	public static final String EXTRA_NO_FOUND = "NO_FOUND";
	public static final String EXTRA_RSSI = "RSSI";
	public static final String EXTRA_SOURCE = "SOURCE";

	public boolean isScaning;

	public static final int GATT_DEVICE_FOUND_MSG = 5;
	public static final int HRP_CONNECT_MSG = 1;
	public static final int HRP_DISCONNECT_MSG = 2;
	public static final int HRP_READY_MSG = 3;
	public static final int HRP_VALUE_MSG = 4;
	public static final int YH_SET_CLEAR_DATA = 16;
	public static final int YH_SET_CLEAR_DATA2 = 17;
	public static final int YH_SET_DEVICE_CLOCK = 14;
	public static final int YH_SET_DEVICE_FACTORY = 15;
	public static final int YH_SET_DEVICE_FATIGUE = 13;
	public static final int YH_SET_DEVICE_ID = 11;
	public static final int YH_SET_GET_GOALD = 7;
	public static final int YH_SET_TARGET = 12;
	public static final int YH_SET_TIME = 10;
	public static final int YH_SET_USER_INFO = 9;
	public static final int YH_SHOW_GOALD = 6;
	public static final int YH_USER_INFO = 8;
	public static final int YH_HEART_RETE = 99;

	public int mStatus;
	public static Handler mActivityHandler;
	public static Handler mDeviceListHandler;

	//
	private BluetoothManager mBluetoothManager;
	private static BluetoothAdapter mBluetoothAdapter;
	private String mBluetoothDeviceAddress;// 蓝牙设备地址
	private  BluetoothGatt mBluetoothGatt;
	public final static String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";

	// private MyBluetoothGattCallback mGattCallback ;
	private MyBluetoothcallback callback;
	// public static final UUID YH_SERVICE = UUID
	// .fromString("0000FFF0-0000-1000-8000-00805F9B34FB");

	public final static UUID UUID_PEDOMETER_SEND = UUID
			.fromString("0000FFF6-0000-1000-8000-00805F9B34FB");

	public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID
			.fromString("00002a37-0000-1000-8000-00805f9b34fb");
	public final static UUID UUID_PEDOMETER = UUID
			.fromString("0000FFF7-0000-1000-8000-00805F9B34FB");
	public final static UUID UUID_PEDOMETER_SERVICE = UUID
			.fromString("0000fff0-0000-1000-8000-00805f9b34fb");

	public final static UUID CCC = UUID
			.fromString("00002902-0000-1000-8000-00805f9b34fb");
	private BluetoothDevice mDevice;

	public static final int ADV_DATA_FLAG = 0X01;
	public static final int FIRST_BITMASK = 0x01;
	public static final int LIMITED_AND_GENERAL_DISC_MASK = 0x03;

	static {
		mActivityHandler = null;
		mDeviceListHandler = null;
	}
	
	/**
	 * fasong*/
	
	class MyBluetoothcallback extends BluetoothGattCallback {

		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			if(status == BluetoothGatt.GATT_SUCCESS){
				if(newState == BluetoothProfile.STATE_CONNECTED){
					mStatus = 2;//weizhi
					mBluetoothGatt = null;
					mBluetoothGatt = gatt;
					discovertSerivice();
					Bundle bundle = new Bundle();
					Message message  = Message.obtain(mActivityHandler, 1);
					bundle.putString("android.bluetooth.device.extra.DEVICE", gatt.getDevice().getAddress());
					message.setData(bundle);
					message.sendToTarget();
					
				}else if(newState == BluetoothProfile.STATE_DISCONNECTED){
					mStatus = 0;
					mDevice = null;
					Message.obtain(mActivityHandler, 2).sendToTarget();
//					Intent intent = new Intent("com.bluetooth.disconnect.action");
//					sendBroadcast(intent);
				}
				
			}
			
			
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			Message.obtain(mActivityHandler, 3).sendToTarget();
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			//特征值发现变化时
			if(characteristic.getValue() != null){
				ResolveData.DecodeRecvData(characteristic.getValue());
			}
//			MyApplication.getInstance().sendBroadcast(characteristic.getValue());
		}
		
		
		
	}


	/**
	 * 打开蓝牙notification模块 连接只有一定要打开这个
	 * 
	 * */


	/**
	 * 读取某些天的数据
	 * */
	public void WriteSomedayData(UUID uuid1,UUID uuid2,BluetoothDevice bluetoothDevice, byte pbyte1,
			byte pbyte2) {
		if (mBluetoothGatt == null) {
			return;
		}
		BluetoothGattService mGS = mBluetoothGatt
				.getService(uuid1);
		if (mGS == null) {
			return;
		}
		BluetoothGattCharacteristic bGC = mGS.getCharacteristic(uuid2);
		if (bGC == null) {
//			Log.e("=====",
//					"HEART RATE Copntrol Point characteristic not found!");
			return;
		}
		byte[] arrofByte = new byte[16];
		switch (pbyte1) {
		case 7:

			arrofByte = TransmitData.readSomeData(pbyte1, pbyte2);
			break;
		case 8:

			arrofByte = TransmitData.readSomeData(pbyte1, pbyte2);
			break;
		case 67:

			arrofByte = TransmitData.readSomeData(pbyte1, pbyte2);
			break;

		}
		ableYHNotification(uuid1, uuid2);
		bGC.setValue(arrofByte);

		try {
			Thread.sleep(300L);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mBluetoothGatt.writeCharacteristic(bGC);
	}

	public boolean isBleDevice(BluetoothDevice bluetoothDevice) {
		return false;
	}


	// 发现服务
	public void discovertSerivice() {
		mBluetoothGatt.discoverServices();
	}

	static TransmitData mtransmitData;

	/**
	 * 连接设备 bluetoothdevice boolean
	 * */

	public void connect(BluetoothDevice bluetoothDevice, boolean b) {
		if (mBluetoothGatt != null) {
			mBluetoothGatt.disconnect();
			mBluetoothGatt.close();
			mBluetoothGatt = null;


		}
		callback = new MyBluetoothcallback();
		mBluetoothGatt = bluetoothDevice.connectGatt(this, b, callback);
		MyApplication.isBluetoothConnection = true;
	}





	/**
	 * 
	 * */

	public void ableYHNotification(UUID UUID1, UUID UUID2) {
//		Log.i("=====", "enableNotification");
		BluetoothGattService bluetoothGattService = mBluetoothGatt
				.getService(UUID_PEDOMETER_SERVICE);
		if (bluetoothGattService == null) {
//			Log.i("APP", "======service not found");
			return;
		}
		BluetoothGattDescriptor mBluetoothGattDescriptor;
		BluetoothGattCharacteristic bluetoothGattCharacteristic;
		bluetoothGattCharacteristic = bluetoothGattService
				.getCharacteristic(UUID_PEDOMETER);
		if (bluetoothGattCharacteristic == null) {
//			Log.i("APP", "charateristic not found");
			return;
		}
		if (mBluetoothGatt.setCharacteristicNotification(
				bluetoothGattCharacteristic, true)) {
			mBluetoothGattDescriptor = bluetoothGattCharacteristic
					.getDescriptor(CCC);
			if (mBluetoothGattDescriptor == null) {
//				Log.i("=====", "bluetoothGattDescriptor not found");
			}
			mBluetoothGattDescriptor
					.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
			mBluetoothGatt.writeDescriptor(mBluetoothGattDescriptor);
//			Log.i("=====", "success" + "  wirite");

		}

	}

	/**
	 * 蓝牙状态不通知
	 * */

	public void disableNotification(UUID UUID1, UUID UUID2,
			BluetoothDevice bluetoothDevice) {
//		Log.i("=====", "disableNotification ");
		BluetoothGattService bluetoothGattService = mBluetoothGatt
				.getService(UUID_PEDOMETER_SERVICE);
		if (bluetoothGattService == null) {
//			Log.e("=====", "service not found");
			return;
		}
		BluetoothGattDescriptor bgDescript;
		BluetoothGattCharacteristic bgCharacteristic;
		bgCharacteristic = bluetoothGattService
				.getCharacteristic(UUID_PEDOMETER);
		if (bgCharacteristic == null) {
//			Log.e("=====", "charateristic not found!");
			return;
		}
		if (mBluetoothGatt.setCharacteristicNotification(bgCharacteristic,
				false)) {
			bgDescript = bgCharacteristic.getDescriptor(CCC);
			if (bgDescript == null) {
//				Log.e("=====", "bg");
				return;
			}
			bgDescript
					.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
			mBluetoothGatt.writeDescriptor(bgDescript);
		}

	}

	public void writeDataToPedometer(UUID UUID1, UUID UUID2, int set_what) {
		if (mBluetoothGatt == null) {
//			Log.e("=====", "HRP service not found");
			return;
		}
		BluetoothGattService bluetoothGattService = mBluetoothGatt
				.getService(UUID1);
		if (bluetoothGattService == null) {
//			Log.i("=====", "HRP service not found");
			return;
		}
		BluetoothGattCharacteristic bluetoothGattCharacteristic = bluetoothGattService
				.getCharacteristic(UUID2);
		if (bluetoothGattCharacteristic == null) {
//			Log.e("=====", "HEART RATE Copntrol Point charateristic not found!");
			return;
		}
		byte[] arrofByte = new byte[16];
		arrofByte = TransmitData.sendDate(set_what);
//		Log.i("APP", "byte[]="+Arrays.toString(arrofByte));
		ableYHNotification(UUID1, UUID2);
		bluetoothGattCharacteristic.setValue(arrofByte);
		try {
			Thread.sleep(500L);
			if (mBluetoothGatt == null) {
				return;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mBluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);

	}

	public void writeDataToPedometer(UUID UUID1, UUID UUID2, byte[] aByte) {
		if (mBluetoothGatt == null) {
			return;
		}
		BluetoothGattService bluetoothGattService = mBluetoothGatt
				.getService(UUID1);
		if (bluetoothGattService == null) {
//			Log.e("=====", "HRP service not found");
			return;
		}
		BluetoothGattCharacteristic bluetoothGattCharacteristic = bluetoothGattService
				.getCharacteristic(UUID2);
		if (bluetoothGattCharacteristic == null) {
//			Log.e("=====",
//					"HEART REATE Conpntrol Point charateristic not found!");
			return;
		}
		ableYHNotification(UUID1, UUID2);
		bluetoothGattCharacteristic.setValue(aByte);

		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mBluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);

	}

	/**
	 * 连接断开
	 * */
	public void disconnect(BluetoothDevice bluetoothDevice) {
		if (mBluetoothGatt != null) {
			mBluetoothGatt.disconnect();
			mBluetoothGatt.close();
			MyApplication.getInstance().mService.mStatus = 0;
			mBluetoothGatt = null;
			callback= null;
			MyApplication.isBluetoothConnection = false;
			MyApplication.CloseLoadingProgress();
			MyApplication.mDevice = null;
		}
	}

	public class LocalBinder extends Binder {
		public BluetoothLeService getService() {
			return BluetoothLeService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		close();
		return super.onUnbind(intent);
	}

	private final IBinder mBinder = new LocalBinder();

	public void close() {
		if (mBluetoothGatt == null) {
			return;
		}
		mBluetoothGatt.close();
		mBluetoothGatt = null;
	}

	/**
	 * 设置activity的 Handler
	 */
	public void setActivityHandler(Handler handler) {
		mActivityHandler = handler;
	}

	/**
	 * 设置设备的 Handler
	 * */
	public void setDeviceListHandler(Handler handler) {
		mDeviceListHandler = handler;

	}

	// 蓝牙扫描回调函数
	LeScanCallback mLeScanCallback = new LeScanCallback() {

		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//			Log.d("=====", "onScanResult() - device=" + device + ", rssi="
//					+ rssi);
			if (!checkIfBroadcastMode(scanRecord)) {
				Bundle bundle = new Bundle();
				Message message = Message.obtain(
						BluetoothLeService.mDeviceListHandler, 5);
				bundle.putInt(BluetoothLeService.EXTRA_RSSI, rssi);
				bundle.putParcelable("android.bluetooth.device.extra.DEVICE",
						device);
				message.setData(bundle);
				message.sendToTarget();
			}
		}

	};

	private boolean checkIfBroadcastMode(byte[] scanRecord) {
		int offset = 0;
		while (offset < (scanRecord.length - 2)) {
			int len = scanRecord[offset++];
			if (len == 0)
				break;
			int type = scanRecord[offset++];
			switch (type) {
			case ADV_DATA_FLAG:
				if (len > 2) {
					byte falg = scanRecord[offset++];

					if ((falg & LIMITED_AND_GENERAL_DISC_MASK) > 0)
						return false;
					else
						return true;
				} else if (len == 1) {
					continue;
				}

			default:
				offset += (len - 1);
				break;
			}

		}

		return false;
	}

	/**
	 * 扫描
	 * */
	public void scan(boolean paramBoolean) {
		if (paramBoolean && !isScaning) {
			mBluetoothAdapter.startLeScan(mLeScanCallback);
			isScaning = true;
		} else {
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
			isScaning = false;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, "ble not support", Toast.LENGTH_SHORT).show();
		}

		if (mBluetoothAdapter == null) {
			mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			mBluetoothAdapter = mBluetoothManager.getAdapter();
		}
	}

}
