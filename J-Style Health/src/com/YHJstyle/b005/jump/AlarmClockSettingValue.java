package com.YHJstyle.b005.jump;

import java.util.Calendar;
import java.util.Date;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.Parameter_titleView;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

/**
 * 设置闹钟 周末 工作日 每天
 * */
public class AlarmClockSettingValue extends Activity implements OnClickListener {

	private String aTime ;
	private Bundle bundle;
	private RadioGroup group;
	private Intent in;
	int index = 0;
	private boolean isAlarmClock = false;
	private boolean[] isWeeks = new boolean[7];
	private ListView lv;

	private RadioButton other_btn;
	private Button parameter_alarmClock_clockRepeater_btn;
	private Button parameter_alarmClock_time_btn;
	private Parameter_titleView pt;
	private StringBuffer sb;
	private RadioButton sleep_btn;
	private String[] weeks = new String[7];

	// 时间选择起
	OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			aTime = MyApplication.timeConversion(hourOfDay, minute);
			parameter_alarmClock_time_btn.setText(aTime);
		}
	};

	protected Dialog onCreateDialog(int id) {
		if (id == 1) {
			Calendar calendar = Calendar.getInstance();
			int i = calendar.get(Calendar.HOUR_OF_DAY);
			int j = calendar.get(Calendar.MINUTE);
			return new TimePickerDialog(this, onTimeSetListener, i, j, true);
		}
		return super.onCreateDialog(id);
		// new TimePickerDialog(context, callBack, hourOfDay, minute,
		// is24HourView)
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.parameter_alarmClock_time_btn:
			showDialog(1);//
			// DialogFragment
			// DialogFragment dg.showDialog(1);
			break;
		case R.id.parameter_alarmClock_clockRepeatr_btn:
			new AlertDialog.Builder(this)
					.setTitle(R.string.parameter_clock_selection_time)
					.setMultiChoiceItems(weeks, isWeeks,
							new DialogInterface.OnMultiChoiceClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {
									// TODO Auto-generated method stub

								}

							})
					.setPositiveButton(R.string.confirm,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									sb = new StringBuffer();
									for (int i = 0; i < isWeeks.length; i++) {
										if (isWeeks[i] == true ) {
											sb.append(weeks[i] + "\t");
										}
									}
									parameter_alarmClock_clockRepeater_btn
											.setText(sb.toString());
								}
							})
					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									for (int i = 0; i < isWeeks.length; i++) {
										if (isWeeks[i] == false)
											isWeeks[i] = false;
									}
								}
							}).show();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (sb != null) {
			bundle = getIntent().getExtras();
			int i = bundle.getInt("clock");
			if (i == 1) {
				MyApplication.alarmClock_one = sb.toString();
//				Log.i("APP", "SB" + sb.toString());
				if (aTime != null) {
					MyApplication.alarmClock_oneTime = aTime;
				} else {

					aTime = null;
				}
			} 
			if (i == 2) {
				MyApplication.alarmClock_two = sb.toString();
				if (aTime != null) {
					MyApplication.alarmClock_twoTime = aTime;
				} else {
					aTime = null;
				}
				
			} 
			if (i == 3) {
				MyApplication.alarmClock_three = sb.toString();
				if (aTime != null) {
					MyApplication.alarmClock_threeTime = aTime;
				} else {

					aTime = null;
				}
			} 
			if (i == 4) {
				MyApplication.alarmClock_four = sb.toString();
				if (aTime != null) {
					MyApplication.alarmClock_fourTime = aTime;
				} else {

					aTime = null;
				}
			} 
			if (i == 5) {
				MyApplication.alarmClock_five = sb.toString();
				if (aTime != null) {
					MyApplication.alarmClock_fiveTime = aTime;
				} else {

					aTime = null;
				}
			}
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.parameter_alarmclock_setting_value);
		pt = (Parameter_titleView) findViewById(R.id.parameter_title);
		parameter_alarmClock_time_btn = (Button) findViewById(R.id.parameter_alarmClock_time_btn);
		parameter_alarmClock_time_btn.setOnClickListener(this);
		parameter_alarmClock_clockRepeater_btn = (Button) findViewById(R.id.parameter_alarmClock_clockRepeatr_btn);
		parameter_alarmClock_clockRepeater_btn.setOnClickListener(this);
		titleInit();// 标题初始化
		init();
		// 初始界面
		if (AlarmclockSetting.isONE) {
			if ("".equals(AlarmclockSetting.time_week1)) {
				AlarmclockSetting.time_week1 = getResources().getString(
						R.string.parameter_clock_weeks_txt);
			}
			if ("".equals(AlarmclockSetting.time_Min1)) {
				AlarmclockSetting.time_Min1 = "00:00";
			}

			parameter_alarmClock_time_btn.setText(AlarmclockSetting.time_Min1);
			parameter_alarmClock_clockRepeater_btn
					.setText(AlarmclockSetting.time_week1);
		}
		if (AlarmclockSetting.isTWO) {
			if ("".equals(AlarmclockSetting.time_week2)) {
				AlarmclockSetting.time_week2 = getResources().getString(
						R.string.parameter_clock_weeks_txt);
			}
			if ("".equals(AlarmclockSetting.time_Min2)) {
				AlarmclockSetting.time_Min2 = "00:00";
			}
			parameter_alarmClock_time_btn.setText(AlarmclockSetting.time_Min2);
			parameter_alarmClock_clockRepeater_btn
					.setText(AlarmclockSetting.time_week2);
		}
		if (AlarmclockSetting.isTHREE) {
			if ("".equals(AlarmclockSetting.time_week3)) {
				AlarmclockSetting.time_week3 = getResources().getString(
						R.string.parameter_clock_weeks_txt);
			}
			if ("".equals(AlarmclockSetting.time_Min3)) {
				AlarmclockSetting.time_Min3 = "00:00";
			}
			parameter_alarmClock_time_btn.setText(AlarmclockSetting.time_Min3);
			parameter_alarmClock_clockRepeater_btn
					.setText(AlarmclockSetting.time_week3);
		}
		if (AlarmclockSetting.isFOUR) {
			if ("".equals(AlarmclockSetting.time_week4)) {
				AlarmclockSetting.time_week4 = getResources().getString(
						R.string.parameter_clock_weeks_txt);
			}
			if ("".equals(AlarmclockSetting.time_Min4)) {
				AlarmclockSetting.time_Min4 = "00:00";
			}
			parameter_alarmClock_time_btn.setText(AlarmclockSetting.time_Min4);
			parameter_alarmClock_clockRepeater_btn
					.setText(AlarmclockSetting.time_week4);
		}
		if (AlarmclockSetting.isFIVE) {
			if ("".equals(AlarmclockSetting.time_week5)) {
				AlarmclockSetting.time_week5 = getResources().getString(
						R.string.parameter_clock_weeks_txt);
			}
			if ("".equals(AlarmclockSetting.time_Min5)) {
				AlarmclockSetting.time_Min5 = "00:00";
			}
			parameter_alarmClock_time_btn.setText(AlarmclockSetting.time_Min5);
			parameter_alarmClock_clockRepeater_btn
					.setText(AlarmclockSetting.time_week5);
		}

	}

	private void init() {
		String[] str = new String[7];
		str[0] = getResources().getString(R.string.weeks_sunday);
		str[1] = getResources().getString(R.string.weeks_monday);
		str[2] = getResources().getString(R.string.weeks_tuesday);
		str[3] = getResources().getString(R.string.weeks_wednesday);
		str[4] = getResources().getString(R.string.weeks_thursday);
		str[5] = getResources().getString(R.string.weeks_friday);
		str[6] = getResources().getString(R.string.weeks_Saturday);
		weeks = str;
		isWeeks = new boolean[7];
	}

	private void titleInit() {
		pt.setLeftButton(R.string.title_settings,
				R.string.parameter_clock_setting,
				new Parameter_titleView.OnLeftButtonClickListener() {

					@Override
					public void onClick(View v) {
						finish();

					}
				});

	}

}
