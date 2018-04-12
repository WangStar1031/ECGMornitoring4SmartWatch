package com.YHJstyle.b005.jump;

import java.util.Calendar;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.MySlipSwitch;
import com.YHJstyle.b005.b005.view.Parameter_titleView;
import com.YHJstyle.b005.j_style_Pro.b005.tool.BluetoothLeService;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.TimeUtil;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class FatigueMindSetting extends Activity implements OnClickListener {
	private int DATA_PICKED_ID = 0;
	private StringBuffer buffer;
	private String endTime;// 结束时间
	private MySlipSwitch fSwtich;
	private boolean isEndTime;
	private boolean isStartTime;
	private String num;

	private String oneTime;
	private String meter_endTime;
	private Button meter_fatiguemind_endTime_btn;
	private Button meter_fatiguemind_startTime_btn;
	private String meter_reachTime;
	private String meter_startTime;
	private EditText meter_tatigueremind_reach_time_btn;
	private Parameter_titleView pt;
	private String st;
	private String startTime;

	// 闹钟选择监听
	TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			if (FatigueMindSetting.this.DATA_PICKED_ID == 1) {

				FatigueMindSetting.this.meter_fatiguemind_startTime_btn
						.setText(MyApplication
								.timeConversion(hourOfDay, minute));
				// 闹钟选择时间 确定
				FatigueMindSetting.this.meter_startTime = FatigueMindSetting.this.meter_fatiguemind_startTime_btn
						.getText().toString();
				FatigueMindSetting.this.isStartTime = true;

			}
			if (DATA_PICKED_ID == 2) {
				FatigueMindSetting.this.meter_fatiguemind_endTime_btn
						.setText(MyApplication
								.timeConversion(hourOfDay, minute));
				FatigueMindSetting.this.meter_endTime = FatigueMindSetting.this.meter_fatiguemind_endTime_btn
						.getText().toString();
				FatigueMindSetting.this.isEndTime = true;
			}
		}
	};

	public String getEndTime() {
		return this.meter_endTime;
	}

	public String getReachTime() {
		return meter_reachTime;
	}

	public String getStartTime() {
		return meter_startTime;
	}

	/**
	 * 保存闹钟设置
	 * 
	 * */
	private void setFatiguRemindData() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"parameterFatigue_info", Context.MODE_PRIVATE);
		sharedPreferences.edit()
				.putString("fatigue_time", this.meter_reachTime).commit();
		sharedPreferences.edit()
				.putString("fatigue_startTime", this.meter_startTime).commit();
		sharedPreferences.edit()
				.putString("fatigue_endTime", this.meter_endTime).commit();

		String[] str1;
		String[] str2;
		int j = 0;
		int m = 0;
		int n = 0;
		int b = 0;
		// 判断起始 结束时间是否为空 是否等于00
		if ((!"00:00".equals(meter_startTime))
				&& (!"00:00".equals(meter_endTime)) && (meter_endTime != null)
				&& (meter_startTime != null)) {
			str1 = TimeUtil.splitTimeStr(meter_startTime);
			str2 = TimeUtil.splitTimeStr(meter_endTime);
			MyApplication.fatigueRemind = new byte[16];
			MyApplication.fatigueRemind[0] = 37;
			if (str1[0].length() != 1) {
				if (str1[0].length() == 2) {
					j = 16
							* Integer
									.parseInt(String.valueOf(str1[0].charAt(0)))
							+ Integer
									.parseInt(String.valueOf(str1[0].charAt(1)));

				} else {
					j = Integer.parseInt(str1[0]);
				}
			}
			if (str1[1].length() != 1) {
				if (str1[1].length() == 2) {
					m = 16
							* Integer
									.parseInt(String.valueOf(str1[1].charAt(0)))
							+ Integer
									.parseInt(String.valueOf(str1[1].charAt(1)));

				} else {
					m = Integer.parseInt(str1[1]);
				}
			}
			if (str2[0].length() != 1) {
				if (str2[0].length() == 2) {
					n = 16
							* Integer
									.parseInt(String.valueOf(str2[0].charAt(0)))
							+ Integer
									.parseInt(String.valueOf(str2[0].charAt(1)));

				} else {
					n = Integer.parseInt(str1[0]);
				}
			}
			if (str2[1].length() != 1) {
				if (str2[1].length() == 2) {
					b = 16
							* Integer
									.parseInt(String.valueOf(str2[1].charAt(0)))
							+ Integer
									.parseInt(String.valueOf(str2[1].charAt(1)));

				} else {
					b = Integer.parseInt(str2[1]);
				}
			}
			MyApplication.fatigueRemind[1] = (byte) j;
			MyApplication.fatigueRemind[2] = (byte) m;
			MyApplication.fatigueRemind[3] = (byte) n;
			MyApplication.fatigueRemind[4] = (byte) b;
			MyApplication.fatigueRemind[5] = -1;
			MyApplication.fatigueRemind[6] = (byte) Integer
					.parseInt(meter_reachTime);
			for (int q = 7; q < 15; q++) {
				MyApplication.fatigueRemind[q] = 0;
			}
			int var = 0;
			for (int k = 0; k < MyApplication.fatigueRemind.length; k++) {
				var += MyApplication.fatigueRemind[k];
			}
			MyApplication.fatigueRemind[15] = (byte) (var & 255);
			if (MyApplication.getInstance().mService != null) {
				MyApplication.getInstance().mService.writeDataToPedometer(
						BluetoothLeService.UUID_PEDOMETER_SERVICE,
						BluetoothLeService.UUID_PEDOMETER_SEND,
						MyApplication.fatigueRemind);
			}
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.parameter_fatigueremind_startTime_btn:
			showDialog(1);
			DATA_PICKED_ID = 1;
			break;

		case R.id.parameter_fatigueremind_endTime_btn:
			showDialog(2);
			DATA_PICKED_ID = 2;
			break;
		}
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == 1) {
			Calendar calendar = Calendar.getInstance();
			int k = calendar.get(Calendar.HOUR_OF_DAY);
			int l = calendar.get(Calendar.MINUTE);
			return new TimePickerDialog(this, onTimeSetListener, k, l, true);
		}
		if (id == 2) {
			Calendar calendar2 = Calendar.getInstance();
			int i = calendar2.get(Calendar.HOUR_OF_DAY);
			int j = calendar2.get(Calendar.MINUTE);
			return new TimePickerDialog(this, onTimeSetListener, i, j, true);

		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.parameter_fatigueremind_settings);
		pt = (Parameter_titleView) findViewById(R.id.parameter_title);
		meter_tatigueremind_reach_time_btn = (EditText) findViewById(R.id.parameter_tatigueremind_reach_time_btn);
		meter_fatiguemind_startTime_btn = (Button) findViewById(R.id.parameter_fatigueremind_startTime_btn);
		meter_fatiguemind_startTime_btn.setOnClickListener(this);
		meter_fatiguemind_endTime_btn = (Button) findViewById(R.id.parameter_fatigueremind_endTime_btn);
		meter_fatiguemind_endTime_btn.setOnClickListener(this);
		titleInit();
		buffer = new StringBuffer();
	}

	/**
	 * 标题初始化
	 * */

	private void titleInit() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"parameterFatigue_info", Context.MODE_PRIVATE);
		oneTime = sharedPreferences.getString("fatigue_time", "");
		startTime = sharedPreferences.getString("fatigue_startTime", "");
		endTime = sharedPreferences.getString("fatigue_endTime", "");
		if ((!"".equals(oneTime)) && (!"".equals(startTime))
				&& (!"".equals(endTime))) {
			meter_tatigueremind_reach_time_btn.setText(oneTime);
			meter_fatiguemind_endTime_btn.setText(endTime);
			meter_fatiguemind_startTime_btn.setText(startTime);
		}
		// pt.setLeftButton(getResources().getString(R.string.title_settings),
		// getResources().getString(R.string.parameter_fatigue))
		pt.setLeftButton(R.string.title_settings, R.string.parameter_fatigue,
				new Parameter_titleView.OnLeftButtonClickListener() {

					@Override
					public void onClick(View v) {
						finish();

					}

				});
		pt.setRightText(R.string.setting_bt);
		pt.setRightButton(new Parameter_titleView.OnRightButtonClickListener() {

			@Override
			public void onClick(View v) {
				meter_reachTime = meter_tatigueremind_reach_time_btn.getText()
						.toString();
				int i = Integer.parseInt(meter_reachTime);
				if ((i < 1) || (i > 255)) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.write_Value),
							Toast.LENGTH_SHORT).show();
					return;
				}
				setFatiguRemindData();
			}
		});

	}

}
