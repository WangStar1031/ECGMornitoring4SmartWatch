package com.YHJstyle.b005.b005.view;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.tool.BluetoothLeService;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyHandler;
import com.YHJstyle.b005.j_style_Pro.b005.tool.TransmitData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 设置页面
 * */

public class SettingView extends LinearLayout implements OnClickListener {

	// 三个
	private static final int BASIC_TYPE = 0;
	private static final int GOAL_TYPE = 1;
	private static final int PEDOMETER = 2;
	private int mType = -1;

	private static boolean isStop = true;
	private static final int msgKey1 = -1;// 、、、、、
	private static final int getInfo = 2;

	private EditText ageEt;
	private Button femaleBt;
	private Button getGoald;
	private EditText goaldEt;
	private EditText heightEt;
	private boolean isMale;
	private EditText lengthEt;
	private Context mContext;

	private Button maleBt;
	private Button setGoaldBt;
	private EditText setIdEt;
	private TextView showDailyGoald;
	private EditText timeEt;
	private Thread timeThread;
	private EditText weightEt;
	private Timer timer;
	private AnsycThread thread;
	private TextView heightUnitTv;
	private TextView weightUnitTv;
	private TextView lengthUnitTv;
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			long t = System.currentTimeMillis();
			switch (msg.what) {
			case msgKey1:
				CharSequence charSequence = DateFormat.format(
						"yyyy-MM-dd  HH:mm:ss", t);
				timeEt.setText(charSequence);
				break;
			case getInfo:
				// MyApplication.getInstance().mService.writeDataToPedometer(BluetoothLeService.UUID_PEDOMETER_SERVICE,
				// BluetoothLeService.UUID_PEDOMETER_SEND,66);
			}
		}
	};

	public SettingView(Context context, int paramInt) {
		super(context);
		this.mContext = context;
		this.mType = paramInt;
		initView();
	}

	/**
	 * 得到设置设备字数的数量
	 * */

	private int getWordCount(String str) {
		int i = 0;
		for (int j = 0; j < str.length(); j++) {
			int k = Character.codePointAt(str, j);
			if ((k > 0) && (k <= 255)) {
				i++;
			} else {
				i += 2;
			}
		}
		return i;
	}

	private void initView() {
		switch (mType) {
		case BASIC_TYPE:// 基本信息
			View.inflate(this.mContext, R.layout.setting_basic_view, this);
			maleBt = (Button) findViewById(R.id.setting_male_bt);
			maleBt.setOnClickListener(this);
			femaleBt = (Button) findViewById(R.id.setting_female_bt);
			femaleBt.setOnClickListener(this);
			heightEt = (EditText) findViewById(R.id.height_editText);
			weightEt = (EditText) findViewById(R.id.weight_editText);
			lengthEt = (EditText) findViewById(R.id.length_editText);
			ageEt = (EditText) findViewById(R.id.age_editText);
			int heightNumber = Integer.parseInt(heightEt.getText().toString());
			int weightNumber = Integer.parseInt(weightEt.getText().toString());
			int lengthNumber = Integer.parseInt(lengthEt.getText().toString());
			
			heightUnitTv = (TextView)findViewById(R.id.setting_height_tv);
			weightUnitTv = (TextView)findViewById(R.id.setting_weight_tv);
			lengthUnitTv = (TextView)findViewById(R.id.setting_length_tv);
			if(MyApplication.isUSA){
				
				heightUnitTv.setText("inches");
				weightUnitTv.setText("lbs");
				lengthUnitTv.setText("inches");
			}else{
				heightUnitTv.setText("cm");
				weightUnitTv.setText("kg");
				lengthUnitTv.setText("cm");
				
			}
			
			
			
			
			
			MyApplication.heightEt = this.heightEt;
			MyApplication.weightEt = this.weightEt;
			MyApplication.lengthEt = this.lengthEt;
			MyApplication.agetEt = this.ageEt;
			MyApplication.maleBtn = this.maleBt;
			MyApplication.femaleBtn = this.femaleBt;
			SharedPreferences sf = this.mContext.getSharedPreferences(
					"USERS_INFO", 0);
			MyApplication.userHeight = sf.getString("user_height", "");
			MyApplication.userWeight = sf.getString("user_weight", "");
			MyApplication.userSteps = sf.getString("user_step", "");
			MyApplication.userAge = sf.getString("user_age", "");
			MyApplication.userSex = sf.getString("user_sex", "");
			if (!"".equals(MyApplication.userHeight)
					&&(!"".equals(MyApplication.userWeight))
					&& (!"".equals(MyApplication.userSteps))
					&& (!"".equals(MyApplication.userAge))
					&& (!"".equals(MyApplication.userSex))) {
				heightEt.setText(MyApplication.userHeight);
				weightEt.setText(MyApplication.userWeight);
				lengthEt.setText(MyApplication.userSteps);
				ageEt.setText(MyApplication.userAge);
				if (Integer.parseInt(MyApplication.userSex) != 0) {
					maleBt.setBackgroundResource(R.drawable.male_pressed);
					femaleBt.setBackgroundResource(R.drawable.setting_female);
				} else {
					maleBt.setBackgroundResource(R.drawable.setting_male);
					femaleBt.setBackgroundResource(R.drawable.female_pressed);
				}
//				DecimalFormat decimal = new DecimalFormat("#");
//				if(MyApplication.isUSA){
//					heightEt.setText(decimal.format(Double.valueOf(MyApplication.userHeight.trim()).doubleValue()/2.54d));
//					weightEt.setText(decimal.format(Double.valueOf(MyApplication.userWeight.trim()).doubleValue()/0.453d));
//					lengthEt.setText(decimal.format(Double.valueOf(MyApplication.userSteps.trim()).doubleValue()/2.54d));
//				}

			}else{
				heightEt.setText("");
				weightEt.setText("");
				lengthEt.setText("");
				ageEt.setText("");
				maleBt.setBackgroundResource(R.drawable.setting_male);
				femaleBt.setBackgroundResource(R.drawable.setting_female);
			}

			// }
			break;
		case GOAL_TYPE:// 目标
			if ((MyApplication.getInstance().mService != null)
					&& (MyApplication.isBluetoothConnection)) {
				MyApplication.getInstance().mService.WriteSomedayData(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND,
						MyApplication.mDevice, (byte) 8, (byte) 0);

			}

			View.inflate(this.mContext, R.layout.setting_goal_view, this);
			setGoaldBt = (Button) findViewById(R.id.set_daily_goald);
			setGoaldBt.setOnClickListener(this);
			getGoald = (Button) findViewById(R.id.get_daily_goald);
			getGoald.setOnClickListener(this);
			goaldEt = (EditText) findViewById(R.id.goald_textView);
			showDailyGoald = (TextView) findViewById(R.id.show_daily_goald);
			showDailyGoald.setText(MyApplication.goaldData + "%");
			((ProgressBar) findViewById(R.id.goald_progress_bar))
					.setProgress(MyApplication.goaldData);
			if (TransmitData.setDataGoald == 0) {
				goaldEt.setText(String.valueOf(TransmitData.setDataGoald));
			}
			break;
		case PEDOMETER:// 设备
			View.inflate(this.mContext, R.layout.setting_pedometer_view, this);
			((Button) findViewById(R.id.setting_pedometer_get_time))
					.setOnClickListener(this);
			((Button) findViewById(R.id.setting_pedometer_get_id))
					.setOnClickListener(this);
			timeEt = (EditText) findViewById(R.id.setting_pedometer_set_time);
			setIdEt = (EditText) findViewById(R.id.setting_pedometer_set_id);

			if (timeThread == null) {
				timeThread = new TimeThread();
			}
			timeThread.start();
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_pedometer_get_time:// 设置时间
			if ((MyApplication.getInstance().mService != null)
					&& (MyApplication.isBluetoothConnection)) {
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND, 1);
				// MyApplication.getInstance().mService.writeDataToPedometer(BluetoothLeService.UUID_PEDOMETER_SERVICE,
				// BluetoothLeService.UUID_PEDOMETER_SEND, 1);
				// 蓝牙服务写如数据 1

				// 设置时间 模式 37 只试用印度计步器
				byte[] arrayByte1 = new byte[16];
				arrayByte1[0] = 55;// 55
				arrayByte1[1] = 1;// 1
				int m = (byte) (arrayByte1[0] + arrayByte1[1]);
				for (int n = 0; n < arrayByte1.length - 2; n++) {
					m = (byte) (m + arrayByte1[(n + 2)]);
				}
				arrayByte1[15] = (byte) (m & 0xFF);
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND, arrayByte1);
			}
			if (!MyApplication.isBluetoothConnection) {
				Toast.makeText(this.mContext,
						R.string.home_page_blutooth_dialog, Toast.LENGTH_LONG)
						.show();
			}

			break;
		case R.id.setting_pedometer_get_id:
			// 设置完成后 执行软复位 2E
			setIdEt = (EditText) findViewById(R.id.setting_pedometer_set_id);
//			Log.i("APP", "sendID" + getWordCount(setIdEt.getText().toString()));
			if ((getWordCount(setIdEt.getText().toString()) > 14)
					|| ("".equals(setIdEt.getText().toString()))
					|| (getWordCount(setIdEt.getText().toString()) == 0)) {
				Toast.makeText(this.mContext,
						getResources().getString(R.string.set_IDValue),
						Toast.LENGTH_LONG).show();
				return;
			}
			TransmitData.devideID = new char[getWordCount(setIdEt.getText()
					.toString())];
			for (int i = 0; i < getWordCount(setIdEt.getText().toString()); i++) {

				TransmitData.devideID[i] = setIdEt.getText().toString()
						.charAt(i);
			}
			if ((MyApplication.getInstance().mService != null)
					&& (MyApplication.isBluetoothConnection)) {
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND, 61);
				byte[] aByte = new byte[16];
				aByte[0] = 46;
				int j = aByte[0];
//				for (int k = 0; k < aByte.length - 1; k++) {
//					j = (byte) (j + aByte[k + 1]);
//				}
				for(int k = 1;k<15;k++){
					aByte[k] = 0;
				}
				aByte[15] = (byte) (j & 0xFF);
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND, aByte);// 设置蓝牙设备名称
//				Thread.sleep(500)
				MyApplication.getInstance().mService.disconnect(MyApplication.mDevice);
			}
			
			break;
		case R.id.setting_male_bt:
			maleBt.setBackgroundResource(R.drawable.male_pressed);
			femaleBt.setBackgroundResource(R.drawable.setting_female);
			MyApplication.isMale = true;
			break;
		case R.id.setting_female_bt:

			maleBt.setBackgroundResource(R.drawable.setting_male);
			femaleBt.setBackgroundResource(R.drawable.female_pressed);
			MyApplication.isMale = false;
			break;
		case R.id.set_daily_goald:// 设置每日目标
			TransmitData.setDataGoald = Integer.valueOf(
					goaldEt.getText().toString().trim()).intValue();
			setStepGoald();
			this.mContext
					.getSharedPreferences("USERS_INFO", Context.MODE_PRIVATE)
					.edit()
					.putString("user_datagoald",
							String.valueOf(TransmitData.setDataGoald)).commit();
			if (MyApplication.getInstance().mService != null
					&& (MyApplication.isBluetoothConnection)) {
				Toast.makeText(
						this.mContext,
						MyApplication.getInstance().getResources()
								.getString(R.string.set_stepSuccess),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.get_daily_goald:// 得到每日目标
			getStepGoald();
		default:
			break;
		}
	}

	private void setStepGoald() {
		if ((MyApplication.getInstance().mService != null)
				&& (MyApplication.isBluetoothConnection)) {
			// 向计步器写入个人呢目标

			MyApplication.getInstance().mService.writeDataToPedometer(
					BluetoothLeService.UUID_PEDOMETER_SERVICE,
					BluetoothLeService.UUID_PEDOMETER_SEND, 11);
		}

	}

	private void getStepGoald() {
		com.YHJstyle.b005.j_style_Pro.b005.tool.MyHandler.mGoaldLinearLayout = (LinearLayout) findViewById(R.id.get_set_goald);
		if ((MyApplication.getInstance().mService != null)
				&& (MyApplication.isBluetoothConnection)) {
			MyApplication.getInstance().mService.writeDataToPedometer(
					BluetoothLeService.UUID_PEDOMETER_SERVICE,
					BluetoothLeService.UUID_PEDOMETER_SEND, 75);
		}
	}

	class AnsycThread extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(500);
				Message mes = new Message();
				mes.what = 2;
				mHandler.sendMessage(mes);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class TimeThread extends Thread {

		@Override
		public void run() {

			while (isStop) {
				try {
					Thread.sleep(1000L);
					Message message = new Message();
					message.what = -1;

					mHandler.sendMessage(message);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	/**
	 * 保存基本信息
	 * */
	public void saveBasicdata() {
		if ((heightEt.getText() != null) && (weightEt.getText() != null)
				&& (lengthEt.getText() != null) && (ageEt.getText() != null)
				&& (!"".equals(heightEt.getText()))
				&& (!"".equals(weightEt.getText()))
				&& (!"".equals(lengthEt.getText()))
				&& (!"".equals(ageEt.getText()))) {
			TransmitData.userInfo.add(heightEt.getText().toString());
			TransmitData.userInfo.add(weightEt.getText().toString());
			TransmitData.userInfo.add(lengthEt.getText().toString());
			TransmitData.userInfo.add(ageEt.getText().toString());
			if (isMale) {
				TransmitData.userInfo.add("1".toString());
			} else {
				TransmitData.userInfo.add("0".toString());
			}
		}

		if ((MyApplication.getInstance().mService != null)
				&& (MyApplication.isBluetoothConnection)) {
			// MyApplication.getInstance().mService.mBleController
			// .writeDataToPedometer(MyService.YH_SERVICE,
			// MyService.YH_SEND_UUID, 2);
			MyApplication.getInstance().mService.writeDataToPedometer(
					BluetoothLeService.UUID_PEDOMETER_SERVICE,
					BluetoothLeService.UUID_PEDOMETER_SEND, 2);
		}
		MyApplication.userHeight = heightEt.getText().toString();
		MyApplication.userWeight = weightEt.getText().toString();
		MyApplication.userSteps = lengthEt.getText().toString();
		MyApplication.userAge = ageEt.getText().toString();
		if (!isMale) {
			MyApplication.userSex = "0";
		} else {
			MyApplication.userSex = "1";
		}
	}
}
