package com.YHJstyle.b005.jump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.tool.BluetoothLeService;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyBrostReiver;

import android.R.color;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 扫描ble设备 并连接上
 * 
 * */
public class DeviceListActivity extends Activity {

	private static final String TAG = "DeviceListActivity";
	Map<String, Integer> devRssiValues;
	private DeviceAdapter deviceAdapter;
	// 设备集合
	private List<BluetoothDevice> deviceList;
	public BluetoothAdapter mBtAdapter = null;

	private MyBrostReiver mReceiver;
	private BluetoothLeService mService = null;

	private ServiceConnection onService = null;

	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			deviceList.get(position);
			// 点击以后 停止扫描
			mService.scan(false);
			// mService.mBleController.scan(false);
			Bundle bundle = new Bundle();
			bundle.putString("android.bluetooth.device.extra.DEVICE",
					((BluetoothDevice) deviceList.get(position)).getAddress());
			Intent intent = new Intent();
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();

		}
	};

	private TextView mEmptyList;// 扫描两字
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 5:// 5
				Bundle bundle1 = msg.getData();
				final BluetoothDevice bluetoothDevice = bundle1
						.getParcelable("android.bluetooth.device.extra.DEVICE");
				final int i = bundle1.getInt("RSSI");
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						addDevice(bluetoothDevice, i);
					}
				});
				break;

			}
		};
	};

	/**
	 * 扫描时添加设备进list 用迭代器遍历 有的不显示出来 不添加 加多一个常量判断
	 * */
	private void addDevice(BluetoothDevice mBluetoothDevice, int position) {
		Iterator localIterator = deviceList.iterator();
		int j = 0;
		devRssiValues.put(mBluetoothDevice.getAddress(), position);
		while (localIterator.hasNext()) {
			if (((BluetoothDevice) localIterator.next()).getAddress().equals(
					mBluetoothDevice.getAddress())) {
				j = 1;
			}
			}
		if (j == 0) {
			mEmptyList.setVisibility(View.GONE);
			deviceList.add(mBluetoothDevice);
			deviceAdapter.notifyDataSetChanged();
		}
		// boolean bool = localIterator.hasNext();
		// int i = 0;
		//
		// devRssiValues.put(mBluetoothDevice.getAddress(),
		// Integer.valueOf(position));
		// if (bool) {
		// if (((BluetoothDevice) localIterator.next()).getAddress().equals(
		// mBluetoothDevice.getAddress())) {
		// i = 1;
		// }
		// }
		//
		//
		//
		// if (i == 0) {
		// mEmptyList.setVisibility(View.GONE);
		// if(!deviceList.contains(mBluetoothDevice)){
		//
		// deviceList.add(mBluetoothDevice);
		// }
		// deviceAdapter.notifyDataSetChanged();
		//
		// }

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		// 通过上面的源码我们可以看到，如果我要定制自己的Title，我们需要采用Window.FEATURE_CUSTOM_TITLE这个特征来实现。
		// 需要注意的是，
		// Window.FEATURE_CUSTOM_TITLE这个特征不能其他特征一起使用，
		// 还有就是必须在setContentView()方法之前使用（这个是所有Window特征改变的必须条件），不然无效

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_bar);
		setFinishOnTouchOutside(false);
		setContentView(R.layout.device_list);

		mReceiver = new MyBrostReiver();
		onService = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				mService = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mService = ((BluetoothLeService.LocalBinder) service)
						.getService();
				if (mService != null) {
					mService.setDeviceListHandler(mHandler);
				}
				populateList();
			}
		};
		startService(new Intent(this, BluetoothLeService.class));
		bindService(new Intent(this, BluetoothLeService.class), onService,
				BIND_AUTO_CREATE);
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		// 扫描
		mEmptyList = (TextView) findViewById(R.id.empty);
		((Button) findViewById(R.id.btn_cancel))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();

					}
				});
	}

	private void populateList() {
		deviceList = new ArrayList();
		deviceAdapter = new DeviceAdapter(this, deviceList);
		devRssiValues = new HashMap();
		ListView listView = (ListView) findViewById(R.id.new_devices);
		listView.setAdapter(deviceAdapter);
		listView.setOnItemClickListener(mDeviceClickListener);

		Iterator iterator = mBtAdapter.getBondedDevices().iterator();
		while (iterator.hasNext()) {
			// 缺少 判断是否是BLE设备
			BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
			if (bluetoothDevice.getName().length() > 7
					&& (bluetoothDevice.getName().substring(0, 7) == "blestep")) {
				addDevice(bluetoothDevice, 0);
			}
		}
		mService.scan(true);

	}

	private void showMessage(int i) {
		Toast.makeText(this, i, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(onService);
		// mService.mBleController.scan(false);
		mService.scan(false);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter intentFilter = new IntentFilter(
				"android.bluetooth.adapter.action.DISCOVERY_FINISHED");
		intentFilter
				.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
		registerReceiver(mReceiver, intentFilter);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(mReceiver);
	}

	class DeviceAdapter extends BaseAdapter {
		Context mcontext;
		List<BluetoothDevice> dev;
		LayoutInflater inflater;

		public DeviceAdapter(Context context, List<BluetoothDevice> list) {
			this.mcontext = context;
			inflater = LayoutInflater.from(mcontext);
			dev = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dev.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return dev.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewGroup viewGroup;
			BluetoothDevice device;
			TextView text1;
			TextView text2;
			TextView text3;
			TextView text4;

			if (convertView != null) {
				viewGroup = (ViewGroup) convertView;
				device = dev.get(position);
				text1 = (TextView) viewGroup.findViewById(R.id.address);
				text2 = (TextView) viewGroup.findViewById(R.id.name);
				text3 = (TextView) viewGroup.findViewById(R.id.paired);
				text4 = (TextView) viewGroup.findViewById(R.id.rssi);
				text4.setVisibility(View.VISIBLE);
				int i = (byte) (devRssiValues.get(device.getAddress())
						.intValue());
				if (i != 0) {
					text4.setText("Rssi = " + String.valueOf(i));
					text1.setText(device.getAddress());
					text2.setText(device.getName());

					if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
						text2.setTextColor(Color.TRANSPARENT);
						text1.setTextColor(Color.TRANSPARENT);
						text3.setVisibility(View.GONE);
						text4.setVisibility(View.VISIBLE);
						text4.setTextColor(Color.TRANSPARENT);
					} else {
						text2.setTextColor(Color.WHITE);
						text1.setTextColor(Color.WHITE);
						text3.setTextColor(Color.WHITE);
						text3.setVisibility(View.VISIBLE);
						text3.setText(R.string.connected);
						text4.setTextColor(Color.WHITE);
						text4.setVisibility(View.VISIBLE);
					}
				}else {
					text2.setTextColor(Color.TRANSPARENT);
					text1.setTextColor(Color.TRANSPARENT);
					text3.setVisibility(View.VISIBLE);
					text3.setText(R.string.connected);
					text4.setVisibility(View.GONE);
				}
			} else {
				viewGroup = (ViewGroup) inflater.inflate(
						R.layout.device_element, null);

			}

			return viewGroup;
		}

	}

	/**
	 * 设备适配器 继承baseAdapter
	 * */
	// class DeviceAdapter extends BaseAdapter {
	// Context context;
	// List<BluetoothDevice> devices;
	// LayoutInflater inflater;
	//
	// public DeviceAdapter(Context mContenxt,
	// List<BluetoothDevice> devicesList) {
	// this.context = mContenxt;
	// this.inflater = LayoutInflater.from(mContenxt);
	// this.devices = devicesList;
	// }
	//
	// @Override
	// public int getCount() {
	// // TODO Auto-generated method stub
	// return devices.size();
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// // TODO Auto-generated method stub
	// return devices.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// // TODO Auto-generated method stub
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// ViewGroup viewGroup;
	// BluetoothDevice mBluetoothDevice;
	// TextView textView1 = null;
	// TextView textView2 = null;
	// TextView textView3 = null;
	// TextView textView4 = null;
	//
	//
	// if (convertView != null) {
	//
	// viewGroup = (ViewGroup) convertView;
	// mBluetoothDevice = (BluetoothDevice) devices.get(position);
	// textView1 = (TextView) viewGroup.findViewById(R.id.address);
	// textView2 = (TextView) viewGroup.findViewById(R.id.name);
	// textView3 = (TextView) viewGroup.findViewById(R.id.paired);
	// textView4 = (TextView) viewGroup.findViewById(R.id.rssi);
	// textView4.setVisibility(View.VISIBLE);
	// int i = devRssiValues.get(mBluetoothDevice.getAddress())
	// .intValue();
	//
	//
	// if (i != 0) {
	// textView4.setText("Rssi" + String.valueOf(i));
	// }
	// textView2.setText(mBluetoothDevice.getName());
	// textView1.setText(mBluetoothDevice.getAddress());
	// if (mBluetoothDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
	// textView1.setTextColor(Color.WHITE);
	// textView2.setTextColor(Color.WHITE);
	// textView3.setTextColor(Color.WHITE);
	// textView3.setText(R.string.connected);
	// textView4.setVisibility(View.INVISIBLE);
	// textView4.setTextColor(Color.WHITE);
	// } else {
	// textView1.setTextColor(Color.GRAY);
	// textView2.setTextColor(Color.GRAY);
	// textView3.setTextColor(Color.GRAY);
	// textView3.setVisibility(View.VISIBLE);
	// textView3.setText(R.string.paired);
	// textView4.setVisibility(View.GONE);
	// }
	// } else {
	// viewGroup = (ViewGroup) inflater.inflate(
	// R.layout.device_element, null);
	//
	// }
	// return viewGroup;
	// }
	//
	// }
}
