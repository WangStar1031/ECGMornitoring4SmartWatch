package com.YHJstyle.b005.j_style_Pro.b005.tool;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBrostReiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String str =  intent.getAction();
		if(("android.bluetooth.adapter.action.DISCOVERY_FINISHED").equals(str)
				&& MyApplication.deviceList.size() ==0){
			Toast.makeText(MyApplication.getInstance(), "NO Device!", Toast.LENGTH_LONG).show();
		}
		if(str.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")){
			BluetoothDevice bluetoothDevice = intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
			int j = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
			//10000
			if(bluetoothDevice.equals(MyApplication.mDevice) && (j == 10)){
				MyApplication.mDevice = null;
			}
		}	
		if(str.equals("android.bluetooth.adapter.action.STATE_CHANGED")){
			int i = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 1);
			if(i ==10){
				MyApplication.mDevice = null;
				MyApplication.mState =MyApplication.HRP_PROFILE_DISCONNECTED;
			}
		}
		if(str.equals("com.youhong.ACTION_DATA_AVAILABLE")){
			 byte[] xx = intent.getByteArrayExtra("com.youhong.EXTRA_DATA");
			 Runnable run = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			};
			
		}
	}

}
