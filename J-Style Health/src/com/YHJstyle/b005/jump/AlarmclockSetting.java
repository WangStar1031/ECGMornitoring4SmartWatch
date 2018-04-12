package com.YHJstyle.b005.jump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.MySlipSwitch;
import com.YHJstyle.b005.b005.view.Parameter_titleView;
import com.YHJstyle.b005.b005.view.MySlipSwitch.OnSwitchListener;
import com.YHJstyle.b005.b005.view.Parameter_titleView.OnLeftButtonClickListener;
import com.YHJstyle.b005.j_style_Pro.b005.tool.BluetoothLeService;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.TimeUtil;

/*
 * 闹钟设置
 * */
public class AlarmclockSetting extends Activity implements OnClickListener {
	public static boolean isFIVE;
	public static boolean isFOUR;
	public static boolean isONE;
	public static boolean isTHREE;
	public static boolean isTWO;
	public static String time_Min1 = "";
	public static String time_Min2 = "";
	public static String time_Min3 = "";
	public static String time_Min4 = "";
	public static String time_Min5 = "";
	public static String time_week1 = "";
	public static String time_week2 = "";
	public static String time_week3 = "";
	public static String time_week4 = "";
	public static String time_week5 = "";
	private StringBuffer bu;
	private Bundle bundle;
	private String everyday = "";
	private String five;
	private String fiveRemind;
	private String fiveTime;
	private String four;
	private String fourRemind;
	private String fourTime;
	private Intent in;
	private boolean isEdit = true;
	private String num;
	private String one;
	private String oneRemind;
	private String oneTime;
	private TextView parameter_clock_fiveWeeks_txt;
	private TextView parameter_clock_fourWeeks_txt;
	private TextView parameter_clock_oneWeeks_txt;
	private TextView parameter_clock_threeWeeks_txt;
	private TextView parameter_clock_twoWeeks_txt;
	private String st;
	private String st2;
	private String st3;
	private String st4;
	private String st5;
	private String three;
	private String threeRemind;
	private String threeTime;
	private Button timeBtn_FIVE;
	private Button timeBtn_FOUR;
	private Button timeBtn_ONE;
	private Button timeBtn_THREE;
	private Button timeBtn_TWO;
	private MySlipSwitch timeSwitch_FIVE;
	private MySlipSwitch timeSwitch_FOUR;
	private MySlipSwitch timeSwitch_ONE;
	private MySlipSwitch timeSwitch_THREE;
	private MySlipSwitch timeSwitch_TWO;
	private Parameter_titleView title_pt;
	private String two;
	private String twoRemind;
	private String twoTime;
	private String workDay = "";
	private String workend = "";

	public void setAlarmClockData() {
		setAlarmClockData2();
	}

	private void setAlarmClockData2() {
		setAlarmClockData3();
	}

	private void setAlarmClockData3() {
		setAlarmClockData4();
	}

	private void setAlarmClockData4() {
		setAlarmClockData5();
	}

	private void setAlarmClockData5() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.parameter_alarmclock_settings);
		title_pt = (Parameter_titleView) findViewById(R.id.parameter_title);
		timeSwitch_ONE = (MySlipSwitch) findViewById(R.id.parameter_clock_oneSwitch);
		timeSwitch_TWO = (MySlipSwitch) findViewById(R.id.parameter_clock_twoSwitch);
		timeSwitch_THREE = (MySlipSwitch) findViewById(R.id.parameter_clock_threeSwitch);
		timeSwitch_FOUR = (MySlipSwitch) findViewById(R.id.parameter_clock_fourSwitch);
		timeSwitch_FIVE = (MySlipSwitch) findViewById(R.id.parameter_clock_fiveSwitch);
		timeBtn_ONE = (Button) findViewById(R.id.parameter_clock_oneTime_txt);
		timeBtn_ONE.setOnClickListener(this);
		timeBtn_TWO = (Button) findViewById(R.id.parameter_clock_twoTime_txt);
		timeBtn_TWO.setOnClickListener(this);
		timeBtn_THREE = (Button) findViewById(R.id.parameter_clock_threeTime_txt);
		timeBtn_THREE.setOnClickListener(this);
		timeBtn_FOUR = (Button) findViewById(R.id.parameter_clock_fourTime_txt);
		timeBtn_FOUR.setOnClickListener(this);
		timeBtn_FIVE = (Button) findViewById(R.id.parameter_clock_fiveTime_txt);
		timeBtn_FIVE.setOnClickListener(this);
		parameter_clock_oneWeeks_txt = (TextView) findViewById(R.id.parameter_clock_oneWeeks_txt);
		parameter_clock_twoWeeks_txt = (TextView) findViewById(R.id.parameter_clock_twoWeeks_txt);
		parameter_clock_threeWeeks_txt = (TextView) findViewById(R.id.parameter_clock_threeWeeks_txt);
		parameter_clock_fourWeeks_txt = (TextView) findViewById(R.id.parameter_clock_fourWeeks_txt);
		parameter_clock_fiveWeeks_txt = (TextView) findViewById(R.id.parameter_clock_fiveWeeks_txt);
		tileInit();
		clockInit();
		bu = new StringBuffer();
	}

	private void clockInit() {
		timeSwitch_ONE.setImageResource(R.drawable.bkg_switch,
				R.drawable.bkg_switch, R.drawable.btn_slip);
		timeSwitch_ONE.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean paramBoolean) {
				if (paramBoolean) {
					MyApplication.isAlarmClock_ONE = true;
					String[] str1;
					int j = 0;
					int m = 0;
					int k = 0;
					if (MyApplication.alarmClock_one != null) {
						SharedPreferences sharedPreferences1 = getSharedPreferences(
								"alarmClock_info", Context.MODE_PRIVATE);
						str1 = TimeUtil
								.splitTimeStr(MyApplication.alarmClock_oneTime);
						// Log.i("APP", "Alarmclocksetting   str1=====" +
						// Arrays.toString(str1));
						if (MyApplication.isAlarmClock_ONE) {
							sharedPreferences1.edit()
									.putString("alarmClock_now", "1").commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_oneTime",
											MyApplication.alarmClock_oneTime)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_oneRemind",
											MyApplication.alarmClock_oneRemind)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_one",
											MyApplication.alarmClock_one)
									.commit();
							time_Min1 = MyApplication.alarmClock_oneTime;
							time_week1 = MyApplication.alarmClock_one;
							MyApplication.clockRemindOne = new byte[16];
							MyApplication.clockRemindOne[0] = 35;
							MyApplication.clockRemindOne[1] = 0;
							MyApplication.clockRemindOne[2] = 1;
							if (str1[0].length() != 1) {
								if (str1[0].length() == 2) {

									j = 16
											* Integer.parseInt(String
													.valueOf(str1[0].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[0].charAt(1)));
								}
							} else {
								j = Integer.parseInt(str1[0]);
							}
							if (str1[1].length() != 1) {
								if (str1[1].length() == 2) {
									k = 16
											* Integer.parseInt(String
													.valueOf(str1[1].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[1].charAt(1)));
								}
							} else {
								k = Integer.parseInt(str1[1]);
							}
							MyApplication.clockRemindOne[3] = (byte) j;
							MyApplication.clockRemindOne[4] = (byte) k;
//							Log.i("APP", MyApplication.clockRemindOne[3] + "");
							if (!(MyApplication.alarmClock_one
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_sunday)))) {
								MyApplication.clockRemindOne[5] = 0;
							} else {
								MyApplication.clockRemindOne[5] = 1;
							}
							if (!(MyApplication.alarmClock_one
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_monday)))) {
								MyApplication.clockRemindOne[6] = 0;
							} else {
								MyApplication.clockRemindOne[6] = 1;
							}
							if (!(MyApplication.alarmClock_one
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_tuesday)))) {
								MyApplication.clockRemindOne[7] = 0;
							} else {
								MyApplication.clockRemindOne[7] = 1;
							}
							if (!(MyApplication.alarmClock_one
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_wednesday)))) {
								MyApplication.clockRemindOne[8] = 0;
							} else {
								MyApplication.clockRemindOne[8] = 1;
							}
							if (!(MyApplication.alarmClock_one
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_thursday)))) {
								MyApplication.clockRemindOne[9] = 0;
							} else {
								MyApplication.clockRemindOne[9] = 1;
							}
							if (!(MyApplication.alarmClock_one
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_friday)))) {
								MyApplication.clockRemindOne[10] = 0;
							} else {
								MyApplication.clockRemindOne[10] = 1;
							}
							if (!(MyApplication.alarmClock_one
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_Saturday)))) {
								MyApplication.clockRemindOne[11] = 0;
							} else {
								MyApplication.clockRemindOne[11] = 1;
							}
							MyApplication.clockRemindOne[15] = AlarmclockSetting.this
									.getCRCvalue(MyApplication.clockRemindOne);
						}
						if (MyApplication.getInstance().mService != null) {
							MyApplication.getInstance().mService
									.writeDataToPedometer(
											BluetoothLeService.UUID_PEDOMETER_SERVICE,
											BluetoothLeService.UUID_PEDOMETER_SEND,
											MyApplication.clockRemindOne);
						}

					}
				}
				MyApplication.isAlarmClock_ONE = false;
			}

		});

		timeSwitch_TWO.setImageResource(R.drawable.bkg_switch,
				R.drawable.bkg_switch, R.drawable.btn_slip);
		timeSwitch_TWO.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean paramBoolean) {
				if (paramBoolean) {
					MyApplication.isAlarmClock_TWO = true;
					String[] str1;
					int j = 0;
					int m = 0;
					int k = 0;
					if (MyApplication.alarmClock_two != null) {
						SharedPreferences sharedPreferences1 = getSharedPreferences(
								"alarmClock_info2", Context.MODE_PRIVATE);
						str1 = TimeUtil
								.splitTimeStr(MyApplication.alarmClock_twoTime);
						// Log.i("APP", "Alarmclocksetting   str1=====" +
						// Arrays.toString(str1));
						if (MyApplication.isAlarmClock_TWO) {
							sharedPreferences1.edit()
									.putString("alarmClock_now2", "2").commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_twoTime",
											MyApplication.alarmClock_twoTime)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_twoRemind",
											MyApplication.alarmClock_twoRemind)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_two",
											MyApplication.alarmClock_two)
									.commit();
							time_Min2 = MyApplication.alarmClock_twoTime;
							time_week2 = MyApplication.alarmClock_two;
							MyApplication.clockRemindTwo = new byte[16];
							MyApplication.clockRemindTwo[0] = 35;
							MyApplication.clockRemindTwo[1] = 1;
							MyApplication.clockRemindTwo[2] = 1;
							if (str1[0].length() != 1) {
								if (str1[0].length() == 2) {

									j = 16
											* Integer.parseInt(String
													.valueOf(str1[0].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[0].charAt(1)));
								}
							} else {
								j = Integer.parseInt(str1[0]);
							}
							if (str1[1].length() != 1) {
								if (str1[1].length() == 2) {
									k = 16
											* Integer.parseInt(String
													.valueOf(str1[1].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[1].charAt(1)));
								}
							} else {
								k = Integer.parseInt(str1[1]);
							}
							MyApplication.clockRemindTwo[3] = (byte) j;
							MyApplication.clockRemindTwo[4] = (byte) k;
//							Log.i("APP", MyApplication.clockRemindTwo[3] + "");
							if (!(MyApplication.alarmClock_two
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_sunday)))) {
								MyApplication.clockRemindTwo[5] = 0;
							} else {
								MyApplication.clockRemindTwo[5] = 1;
							}
							if (!(MyApplication.alarmClock_two
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_monday)))) {
								MyApplication.clockRemindTwo[6] = 0;
							} else {
								MyApplication.clockRemindTwo[6] = 1;
							}
							if (!(MyApplication.alarmClock_two
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_tuesday)))) {
								MyApplication.clockRemindTwo[7] = 0;
							} else {
								MyApplication.clockRemindTwo[7] = 1;
							}
							if (!(MyApplication.alarmClock_two
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_wednesday)))) {
								MyApplication.clockRemindTwo[8] = 0;
							} else {
								MyApplication.clockRemindTwo[8] = 1;
							}
							if (!(MyApplication.alarmClock_two
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_thursday)))) {
								MyApplication.clockRemindTwo[9] = 0;
							} else {
								MyApplication.clockRemindTwo[9] = 1;
							}
							if (!(MyApplication.alarmClock_two
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_friday)))) {
								MyApplication.clockRemindTwo[10] = 0;
							} else {
								MyApplication.clockRemindTwo[10] = 1;
							}
							if (!(MyApplication.alarmClock_two
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_Saturday)))) {
								MyApplication.clockRemindTwo[11] = 0;
							} else {
								MyApplication.clockRemindTwo[11] = 1;
							}
							MyApplication.clockRemindTwo[15] = AlarmclockSetting.this
									.getCRCvalue(MyApplication.clockRemindTwo);
						}
						if (MyApplication.getInstance().mService != null) {
							MyApplication.getInstance().mService
									.writeDataToPedometer(
											BluetoothLeService.UUID_PEDOMETER_SERVICE,
											BluetoothLeService.UUID_PEDOMETER_SEND,
											MyApplication.clockRemindTwo);
						}

					}
				}
				MyApplication.isAlarmClock_TWO = false;
			}

		});
		timeSwitch_THREE.setImageResource(R.drawable.bkg_switch,
				R.drawable.bkg_switch, R.drawable.btn_slip);
		timeSwitch_THREE.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean paramBoolean) {
				if (paramBoolean) {
					MyApplication.isAlarmClock_THREE = true;
					String[] str1;
					int j = 0;
					int m = 0;
					int k = 0;
					if (MyApplication.alarmClock_three != null) {
						SharedPreferences sharedPreferences1 = getSharedPreferences(
								"alarmClock_info3", Context.MODE_PRIVATE);
						str1 = TimeUtil
								.splitTimeStr(MyApplication.alarmClock_threeTime);
						// Log.i("APP", "Alarmclocksetting   str1=====" +
						// Arrays.toString(str1));
						if (MyApplication.isAlarmClock_THREE) {
							sharedPreferences1.edit()
									.putString("alarmClock_now3", "3").commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_threeTime",
											MyApplication.alarmClock_threeTime)
									.commit();
							sharedPreferences1
									.edit()
									.putString(
											"alarmClock_threeRemind",
											MyApplication.alarmClock_threeRemind)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_three",
											MyApplication.alarmClock_three)
									.commit();
							time_Min3 = MyApplication.alarmClock_threeTime;
							time_week3 = MyApplication.alarmClock_three;
							MyApplication.clockRemindThree = new byte[16];
							MyApplication.clockRemindThree[0] = 35;
							MyApplication.clockRemindThree[1] = 2;
							MyApplication.clockRemindThree[2] = 1;
							if (str1[0].length() != 1) {
								if (str1[0].length() == 2) {

									j = 16
											* Integer.parseInt(String
													.valueOf(str1[0].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[0].charAt(1)));
								}
							} else {
								j = Integer.parseInt(str1[0]);
							}
							if (str1[1].length() != 1) {
								if (str1[1].length() == 2) {
									k = 16
											* Integer.parseInt(String
													.valueOf(str1[1].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[1].charAt(1)));
								}
							} else {
								k = Integer.parseInt(str1[1]);
							}
							MyApplication.clockRemindThree[3] = (byte) j;
							MyApplication.clockRemindThree[4] = (byte) k;
//							Log.i("APP", MyApplication.clockRemindThree[3] + "");
							if (!(MyApplication.alarmClock_three
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_sunday)))) {
								MyApplication.clockRemindThree[5] = 0;
							} else {
								MyApplication.clockRemindThree[5] = 1;
							}
							if (!(MyApplication.alarmClock_three
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_monday)))) {
								MyApplication.clockRemindThree[6] = 0;
							} else {
								MyApplication.clockRemindThree[6] = 1;
							}
							if (!(MyApplication.alarmClock_three
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_tuesday)))) {
								MyApplication.clockRemindThree[7] = 0;
							} else {
								MyApplication.clockRemindThree[7] = 1;
							}
							if (!(MyApplication.alarmClock_three
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_wednesday)))) {
								MyApplication.clockRemindThree[8] = 0;
							} else {
								MyApplication.clockRemindThree[8] = 1;
							}
							if (!(MyApplication.alarmClock_three
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_thursday)))) {
								MyApplication.clockRemindThree[9] = 0;
							} else {
								MyApplication.clockRemindThree[9] = 1;
							}
							if (!(MyApplication.alarmClock_three
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_friday)))) {
								MyApplication.clockRemindThree[10] = 0;
							} else {
								MyApplication.clockRemindThree[10] = 1;
							}
							if (!(MyApplication.alarmClock_three
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_Saturday)))) {
								MyApplication.clockRemindThree[11] = 0;
							} else {
								MyApplication.clockRemindThree[11] = 1;
							}
							MyApplication.clockRemindThree[15] = AlarmclockSetting.this
									.getCRCvalue(MyApplication.clockRemindThree);
						}
						if (MyApplication.getInstance().mService != null) {
							MyApplication.getInstance().mService
									.writeDataToPedometer(
											BluetoothLeService.UUID_PEDOMETER_SERVICE,
											BluetoothLeService.UUID_PEDOMETER_SEND,
											MyApplication.clockRemindThree);
						}

					}
				}
				MyApplication.isAlarmClock_THREE = false;
			}

		});
		timeSwitch_FOUR.setImageResource(R.drawable.bkg_switch,
				R.drawable.bkg_switch, R.drawable.btn_slip);
		timeSwitch_FOUR.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean paramBoolean) {
				if (paramBoolean) {
					MyApplication.isAlarmClock_FOUR = true;
					String[] str1;
					int j = 0;
					int m = 0;
					int k = 0;
					if (MyApplication.alarmClock_four != null) {
						SharedPreferences sharedPreferences1 = getSharedPreferences(
								"alarmClock_info4", Context.MODE_PRIVATE);
						str1 = TimeUtil
								.splitTimeStr(MyApplication.alarmClock_fourTime);
						// Log.i("APP", "Alarmclocksetting   str1=====" +
						// Arrays.toString(str1));
						if (MyApplication.isAlarmClock_FOUR) {
							sharedPreferences1.edit()
									.putString("alarmClock_now4", "4").commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_fourTime",
											MyApplication.alarmClock_fourTime)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_fourRemind",
											MyApplication.alarmClock_fourRemind)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_Four",
											MyApplication.alarmClock_four)
									.commit();
							time_Min4 = MyApplication.alarmClock_fourTime;
							time_week4 = MyApplication.alarmClock_four;
							MyApplication.clockRemindFour = new byte[16];
							MyApplication.clockRemindFour[0] = 35;
							MyApplication.clockRemindFour[1] = 3;
							MyApplication.clockRemindFour[2] = 1;
							if (str1[0].length() != 1) {
								if (str1[0].length() == 2) {

									j = 16
											* Integer.parseInt(String
													.valueOf(str1[0].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[0].charAt(1)));
								}
							} else {
								j = Integer.parseInt(str1[0]);
							}
							if (str1[1].length() != 1) {
								if (str1[1].length() == 2) {
									k = 16
											* Integer.parseInt(String
													.valueOf(str1[1].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[1].charAt(1)));
								}
							} else {
								k = Integer.parseInt(str1[1]);
							}
							MyApplication.clockRemindFour[3] = (byte) j;
							MyApplication.clockRemindFour[4] = (byte) k;
//							Log.i("APP", MyApplication.clockRemindFour[3] + "");
							if (!(MyApplication.alarmClock_four
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_sunday)))) {
								MyApplication.clockRemindFour[5] = 0;
							} else {
								MyApplication.clockRemindFour[5] = 1;
							}
							if (!(MyApplication.alarmClock_four
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_monday)))) {
								MyApplication.clockRemindFour[6] = 0;
							} else {
								MyApplication.clockRemindFour[6] = 1;
							}
							if (!(MyApplication.alarmClock_four
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_tuesday)))) {
								MyApplication.clockRemindFour[7] = 0;
							} else {
								MyApplication.clockRemindFour[7] = 1;
							}
							if (!(MyApplication.alarmClock_four
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_wednesday)))) {
								MyApplication.clockRemindFour[8] = 0;
							} else {
								MyApplication.clockRemindFour[8] = 1;
							}
							if (!(MyApplication.alarmClock_four
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_thursday)))) {
								MyApplication.clockRemindFour[9] = 0;
							} else {
								MyApplication.clockRemindFour[9] = 1;
							}
							if (!(MyApplication.alarmClock_four
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_friday)))) {
								MyApplication.clockRemindFour[10] = 0;
							} else {
								MyApplication.clockRemindFour[10] = 1;
							}
							if (!(MyApplication.alarmClock_four
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_Saturday)))) {
								MyApplication.clockRemindFour[11] = 0;
							} else {
								MyApplication.clockRemindFour[11] = 1;
							}
							MyApplication.clockRemindFour[15] = AlarmclockSetting.this
									.getCRCvalue(MyApplication.clockRemindFour);
						}
						if (MyApplication.getInstance().mService != null) {
							MyApplication.getInstance().mService
									.writeDataToPedometer(
											BluetoothLeService.UUID_PEDOMETER_SERVICE,
											BluetoothLeService.UUID_PEDOMETER_SEND,
											MyApplication.clockRemindFour);
						}

					}
				}
				MyApplication.isAlarmClock_FOUR = false;
			}

		});
		timeSwitch_FIVE.setImageResource(R.drawable.bkg_switch,
				R.drawable.bkg_switch, R.drawable.btn_slip);
		timeSwitch_FIVE.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean paramBoolean) {
				if (paramBoolean) {
					MyApplication.isAlarmClock_FIVE = true;
					String[] str1;
					int j = 0;
					int m = 0;
					int k = 0;
					if (MyApplication.alarmClock_five != null) {
						SharedPreferences sharedPreferences1 = getSharedPreferences(
								"alarmClock_info5", Context.MODE_PRIVATE);
						str1 = TimeUtil
								.splitTimeStr(MyApplication.alarmClock_fiveTime);
						// Log.i("APP", "Alarmclocksetting   str1=====" +
						// Arrays.toString(str1));
						if (MyApplication.isAlarmClock_FIVE) {
							sharedPreferences1.edit()
									.putString("alarmClock_now5", "5").commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_fiveTime",
											MyApplication.alarmClock_fiveTime)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_fiveRemind",
											MyApplication.alarmClock_fiveRemind)
									.commit();
							sharedPreferences1
									.edit()
									.putString("alarmClock_five",
											MyApplication.alarmClock_five)
									.commit();
							time_Min5 = MyApplication.alarmClock_fiveTime;
							time_week5 = MyApplication.alarmClock_five;
							MyApplication.clockRemindFive = new byte[16];
							MyApplication.clockRemindFive[0] = 35;
							MyApplication.clockRemindFive[1] = 4;
							MyApplication.clockRemindFive[2] = 1;
							if (str1[0].length() != 1) {
								if (str1[0].length() == 2) {

									j = 16
											* Integer.parseInt(String
													.valueOf(str1[0].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[0].charAt(1)));
								}
							} else {
								j = Integer.parseInt(str1[0]);
							}
							if (str1[1].length() != 1) {
								if (str1[1].length() == 2) {
									k = 16
											* Integer.parseInt(String
													.valueOf(str1[1].charAt(0)))
											+ Integer.parseInt(String
													.valueOf(str1[1].charAt(1)));
								}
							} else {
								k = Integer.parseInt(str1[1]);
							}
							MyApplication.clockRemindFive[3] = (byte) j;
							MyApplication.clockRemindFive[4] = (byte) k;
//							Log.i("APP", MyApplication.clockRemindFive[3] + "");
							if (!(MyApplication.alarmClock_five
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_sunday)))) {
								MyApplication.clockRemindFive[5] = 0;
							} else {
								MyApplication.clockRemindFive[5] = 1;
							}
							if (!(MyApplication.alarmClock_five
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_monday)))) {
								MyApplication.clockRemindFive[6] = 0;
							} else {
								MyApplication.clockRemindFive[6] = 1;
							}
							if (!(MyApplication.alarmClock_five
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_tuesday)))) {
								MyApplication.clockRemindFive[7] = 0;
							} else {
								MyApplication.clockRemindFive[7] = 1;
							}
							if (!(MyApplication.alarmClock_five
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_wednesday)))) {
								MyApplication.clockRemindFive[8] = 0;
							} else {
								MyApplication.clockRemindFive[8] = 1;
							}
							if (!(MyApplication.alarmClock_five
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_thursday)))) {
								MyApplication.clockRemindFive[9] = 0;
							} else {
								MyApplication.clockRemindFive[9] = 1;
							}
							if (!(MyApplication.alarmClock_five
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_friday)))) {
								MyApplication.clockRemindFive[10] = 0;
							} else {
								MyApplication.clockRemindFive[10] = 1;
							}
							if (!(MyApplication.alarmClock_five
									.contains(AlarmclockSetting.this
											.getResources().getString(
													R.string.weeks_Saturday)))) {
								MyApplication.clockRemindFive[11] = 0;
							} else {
								MyApplication.clockRemindFive[11] = 1;
							}
							MyApplication.clockRemindFive[15] = AlarmclockSetting.this
									.getCRCvalue(MyApplication.clockRemindFive);
						}
						if (MyApplication.getInstance().mService != null) {
							MyApplication.getInstance().mService
									.writeDataToPedometer(
											BluetoothLeService.UUID_PEDOMETER_SERVICE,
											BluetoothLeService.UUID_PEDOMETER_SEND,
											MyApplication.clockRemindFive);
						}

					}
				}
				MyApplication.isAlarmClock_FIVE = false;
			}

		});

	}

	protected byte getCRCvalue(byte[] arr) {
		int i = arr[0];
		for (int j = 0; j < arr.length - 1; j++) {
			i = (byte) (i + arr[j + 1]);
		}
		return (byte) (i & 0xFF);
	}

	private void tileInit() {

		SharedPreferences sharedPreferences = getSharedPreferences(
				"alarmClock_info", Context.MODE_PRIVATE);
		st = sharedPreferences.getString("alarmClock_now", "");
		oneTime = sharedPreferences.getString("alarmClock_oneTime", "");
		oneRemind = sharedPreferences.getString("alarmClock_oneRemind", "");
		// Log.i("APP","oneRemind" + oneRemind);
		one = sharedPreferences.getString("alarmClock_one", "");
		workDay = sharedPreferences.getString("alarmClock_work", "");
		workend = sharedPreferences.getString("alarmClock_workend", "");
		everyday = sharedPreferences.getString("alarmClock_everyday", "");
		time_Min1 = oneTime;
		time_week1 = one;
		if ((!"".equals(st)) && (!"".equals(oneTime)) && (!"".equals(one))) {
			if (!"0".equals(st)) {
				timeSwitch_ONE.setSwitchState(true);
				MyApplication.isAlarmClock_ONE = true;

			} else {
				timeSwitch_ONE.setSwitchState(false);
				MyApplication.isAlarmClock_ONE = false;
			}

			String str5 = "";
			if ("true".equals(workDay)) {
				str5 = str5
						+ getResources().getString(R.string.alarmClock_work)
						+ "";
			}
			if ("true".equals(workend)) {
				str5 = str5
						+ getResources().getString(R.string.alarmClock_workend)
						+ "";
			}
			if ("true".equals(everyday)) {
				str5 = ""
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_ONE.setText(oneTime);
			parameter_clock_oneWeeks_txt.setText(str5);
		}

		//
		SharedPreferences sharedPreferences2 = getSharedPreferences(
				"alarmClock_info2", Context.MODE_PRIVATE);
		st2 = sharedPreferences.getString("alarmClock_now2", "");
		twoTime = sharedPreferences.getString("alarmClock_twoTime", "");
		twoRemind = sharedPreferences.getString("alarmClock_twoRemind", "");
		// Log.i("APP","oneRemind" + oneRemind);
		two = sharedPreferences.getString("alarmClock_two", "");
		workDay = sharedPreferences.getString("alarmClock_work", "");
		workend = sharedPreferences.getString("alarmClock_workend", "");
		everyday = sharedPreferences.getString("alarmClock_everyday", "");
		time_Min2 = twoTime;
		time_week2 = two;
		if ((!"".equals(st2)) && (!"".equals(twoTime)) && (!"".equals(two))) {
			if (!"0".equals(st2)) {
				timeSwitch_TWO.setSwitchState(true);
				MyApplication.isAlarmClock_TWO = true;

			} else {
				timeSwitch_TWO.setSwitchState(false);
				MyApplication.isAlarmClock_TWO = false;
			}

			String str2 = "";
			if ("true".equals(workDay)) {
				str2 = str2
						+ getResources().getString(R.string.alarmClock_work)
						+ "";
			}
			if ("true".equals(workend)) {
				str2 = str2
						+ getResources().getString(R.string.alarmClock_workend)
						+ "";
			}
			if ("true".equals(everyday)) {
				str2 = ""
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_TWO.setText(twoTime);
			parameter_clock_twoWeeks_txt.setText(str2);
		}

		SharedPreferences sharedPreferences3 = getSharedPreferences(
				"alarmClock_info3", Context.MODE_PRIVATE);
		st3 = sharedPreferences.getString("alarmClock_now3", "");
		threeTime = sharedPreferences.getString("alarmClock_threeTime", "");
		threeRemind = sharedPreferences.getString("alarmClock_threeRemind", "");
		// Log.i("APP","oneRemind" + oneRemind);
		three = sharedPreferences.getString("alarmClock_three", "");
		workDay = sharedPreferences.getString("alarmClock_work", "");
		workend = sharedPreferences.getString("alarmClock_workend", "");
		everyday = sharedPreferences.getString("alarmClock_everyday", "");
		time_Min3 = threeTime;
		time_week3 = three;
		if ((!"".equals(st3)) && (!"".equals(threeTime)) && (!"".equals(three))) {
			if (!"0".equals(st3)) {
				timeSwitch_THREE.setSwitchState(true);
				MyApplication.isAlarmClock_THREE = true;

			} else {
				timeSwitch_THREE.setSwitchState(false);
				MyApplication.isAlarmClock_THREE = false;
			}

			String str3 = "";
			if ("true".equals(workDay)) {
				str3 = str3
						+ getResources().getString(R.string.alarmClock_work)
						+ "";
			}
			if ("true".equals(workend)) {
				str3 = str3
						+ getResources().getString(R.string.alarmClock_workend)
						+ "";
			}
			if ("true".equals(everyday)) {
				str3 = ""
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_THREE.setText(threeTime);
			parameter_clock_threeWeeks_txt.setText(str3);
		}

		SharedPreferences sharedPreferences4 = getSharedPreferences(
				"alarmClock_info4", Context.MODE_PRIVATE);
		st4 = sharedPreferences.getString("alarmClock_now4", "");
		fourTime = sharedPreferences.getString("alarmClock_fourTime", "");
		fourRemind = sharedPreferences.getString("alarmClock_fourRemind", "");
		// Log.i("APP","oneRemind" + oneRemind);
		four = sharedPreferences.getString("alarmClock_four", "");
		workDay = sharedPreferences.getString("alarmClock_work", "");
		workend = sharedPreferences.getString("alarmClock_workend", "");
		everyday = sharedPreferences.getString("alarmClock_everyday", "");
		time_Min4 = fourTime;
		time_week4 = four;
		if ((!"".equals(st4)) && (!"".equals(fourTime)) && (!"".equals(four))) {
			if (!"0".equals(st4)) {
				timeSwitch_FOUR.setSwitchState(true);
				MyApplication.isAlarmClock_FOUR = true;

			} else {
				timeSwitch_FOUR.setSwitchState(false);
				MyApplication.isAlarmClock_FOUR = false;
			}

			String str4 = "";
			if ("true".equals(workDay)) {
				str4 = str4
						+ getResources().getString(R.string.alarmClock_work)
						+ "";
			}
			if ("true".equals(workend)) {
				str4 = str4
						+ getResources().getString(R.string.alarmClock_workend)
						+ "";
			}
			if ("true".equals(everyday)) {
				str4 = ""
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_FOUR.setText(fourTime);
			parameter_clock_fourWeeks_txt.setText(str4);
		}

		SharedPreferences sharedPreferences5 = getSharedPreferences(
				"alarmClock_info5", Context.MODE_PRIVATE);
		st5 = sharedPreferences.getString("alarmClock_now5", "");
		fiveTime = sharedPreferences.getString("alarmClock_fiveTime", "");
		fiveRemind = sharedPreferences.getString("alarmClock_fiveRemind", "");
		// Log.i("APP","oneRemind" + oneRemind);
		five = sharedPreferences.getString("alarmClock_five", "");
		workDay = sharedPreferences.getString("alarmClock_work", "");
		workend = sharedPreferences.getString("alarmClock_workend", "");
		everyday = sharedPreferences.getString("alarmClock_everyday", "");
		time_Min5 = fiveTime;
		time_week5 = five;
		if ((!"".equals(st5)) && (!"".equals(fiveTime)) && (!"".equals(five))) {
			if (!"0".equals(st5)) {
				timeSwitch_FIVE.setSwitchState(true);
				MyApplication.isAlarmClock_FIVE = true;

			} else {
				timeSwitch_FIVE.setSwitchState(false);
				MyApplication.isAlarmClock_FIVE = false;
			}

			String str51 = "";
			if ("true".equals(workDay)) {
				str51 = str51
						+ getResources().getString(R.string.alarmClock_work)
						+ "";
			}
			if ("true".equals(workend)) {
				str51 = str51
						+ getResources().getString(R.string.alarmClock_workend)
						+ "";
			}
			if ("true".equals(everyday)) {
				str51 = ""
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_FIVE.setText(fiveTime);
			parameter_clock_fiveWeeks_txt.setText(str51);
		}

		// 标题返回键
		title_pt.setLeftButton(R.string.title_settings,
				R.string.parameter_clock, new OnLeftButtonClickListener() {

					@Override
					public void onClick(View v) {
						if ((MyApplication.alarmClock_oneTime != null)
								&& (MyApplication.alarmClock_twoTime != null)
								&& (MyApplication.alarmClock_threeTime != null)
								&& (MyApplication.alarmClock_fourTime != null)
								&& (MyApplication.alarmClock_fiveTime != null)) {
							setAlarmClockData();
						}
						finish();

					}
				});

	};

	/**
	 * 点击跳入闹钟界面设置
	 * */
	@Override
	public void onClick(View v) {
		bundle = new Bundle();
		in = new Intent();
		switch (v.getId()) {
		case R.id.parameter_clock_oneTime_txt:
			bundle.putInt("clock", 1);
			in.putExtras(bundle);

			in.setClass(this, AlarmClockSettingValue.class);
			startActivity(this.in);
			isONE = true;
			isTWO = false;
			isTHREE = false;
			isFOUR = false;
			isFIVE = false;
			break;
		case R.id.parameter_clock_twoTime_txt:
			bundle.putInt("clock", 2);
			in.putExtras(bundle);
			in.setClass(this, AlarmClockSettingValue.class);
			startActivity(in);
			isONE = false;
			isTWO = true;
			isTHREE = false;
			isFOUR = false;
			isFIVE = false;
			break;
		case R.id.parameter_clock_threeTime_txt:
			bundle.putInt("clock", 3);
			in.putExtras(bundle);
			in.setClass(this, AlarmClockSettingValue.class);
			startActivity(in);
			isONE = false;
			isTWO = false;
			isTHREE = true;
			isFOUR = false;
			isFIVE = false;
			break;
		case R.id.parameter_clock_fourTime_txt:
			bundle.putInt("clock", 4);
			in.putExtras(bundle);
			in.setClass(this, AlarmClockSettingValue.class);
			startActivity(in);
			isONE = false;
			isTWO = false;
			isTHREE = false;
			isFOUR = true;
			isFIVE = false;
			break;
		case R.id.parameter_clock_fiveTime_txt:
			bundle.putInt("clock", 5);
			in.putExtras(bundle);
			in.setClass(this, AlarmClockSettingValue.class);
			startActivity(in);
			isONE = false;
			isTWO = false;
			isTHREE = false;
			isFOUR = false;
			isFIVE = true;
			break;
		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if ((MyApplication.alarmClock_oneTime != null)
				&& (MyApplication.alarmClock_one != null)) {
			boolean a = doWorkDay(MyApplication.alarmClock_one);
			boolean b = doWorkendDay(MyApplication.alarmClock_one);
			boolean c = doEveryDay(MyApplication.alarmClock_one);
			String str1 = "";
			if (a) {
				str1 = str1
						+ getResources().getString(R.string.alarmClock_work);
			}
			if (b) {
				str1 = str1
						+ getResources().getString(R.string.alarmClock_workend);
			}
			if (c) {
				str1 = str1
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_ONE.setText(MyApplication.alarmClock_oneTime);
			parameter_clock_oneWeeks_txt.setText(str1);
			SharedPreferences s1 = getSharedPreferences("alarmClock_info",
					Context.MODE_PRIVATE);
			s1.edit().putString("alarmClock_work", "a").commit();
			s1.edit().putString("alarmClock_workend", "b").commit();
			s1.edit().putString("alarmClock_everyday", "c").commit();
		}
		if ((MyApplication.alarmClock_twoTime != null)
				&& (MyApplication.alarmClock_two != null)) {
			boolean a = doWorkDay(MyApplication.alarmClock_two);
			boolean b = doWorkendDay(MyApplication.alarmClock_two);
			boolean c = doEveryDay(MyApplication.alarmClock_two);
			String str2 = "";
			if (a) {
				str2 = str2
						+ getResources().getString(R.string.alarmClock_work);
			}
			if (b) {
				str2 = str2
						+ getResources().getString(R.string.alarmClock_workend);
			}
			if (c) {
				str2 = str2
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_TWO.setText(MyApplication.alarmClock_twoTime);
			parameter_clock_twoWeeks_txt.setText(str2);
			SharedPreferences s2 = getSharedPreferences("alarmClock_info2",
					Context.MODE_PRIVATE);
			s2.edit().putString("alarmClock_work", "a").commit();
			s2.edit().putString("alarmClock_workend", "b").commit();
			s2.edit().putString("alarmClock_everyday", "c").commit();
		}
		if ((MyApplication.alarmClock_threeTime != null)
				&& (MyApplication.alarmClock_three != null)) {
			boolean a = doWorkDay(MyApplication.alarmClock_three);
			boolean b = doWorkendDay(MyApplication.alarmClock_three);
			boolean c = doEveryDay(MyApplication.alarmClock_three);
			String str3 = "";
			if (a) {
				str3 = str3
						+ getResources().getString(R.string.alarmClock_work);
			}
			if (b) {
				str3 = str3
						+ getResources().getString(R.string.alarmClock_workend);
			}
			if (c) {
				str3 = str3
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_THREE.setText(MyApplication.alarmClock_threeTime);
			parameter_clock_threeWeeks_txt.setText(str3);
			SharedPreferences s3 = getSharedPreferences("alarmClock_info3",
					Context.MODE_PRIVATE);
			s3.edit().putString("alarmClock_work", "a").commit();
			s3.edit().putString("alarmClock_workend", "b").commit();
			s3.edit().putString("alarmClock_everyday", "c").commit();
		}
		if ((MyApplication.alarmClock_fourTime != null)
				&& (MyApplication.alarmClock_four != null)) {
			boolean a = doWorkDay(MyApplication.alarmClock_four);
			boolean b = doWorkendDay(MyApplication.alarmClock_four);
			boolean c = doEveryDay(MyApplication.alarmClock_four);
			String str4 = "";
			if (a) {
				str4 = str4
						+ getResources().getString(R.string.alarmClock_work);
			}
			if (b) {
				str4 = str4
						+ getResources().getString(R.string.alarmClock_workend);
			}
			if (c) {
				str4 = str4
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_FOUR.setText(MyApplication.alarmClock_fourTime);
			parameter_clock_fourWeeks_txt.setText(str4);
			SharedPreferences s4 = getSharedPreferences("alarmClock_info3",
					Context.MODE_PRIVATE);
			s4.edit().putString("alarmClock_work", "a").commit();
			s4.edit().putString("alarmClock_workend", "b").commit();
			s4.edit().putString("alarmClock_everyday", "c").commit();
		}
		if ((MyApplication.alarmClock_fiveTime != null)
				&& (MyApplication.alarmClock_five != null)) {
			boolean a = doWorkDay(MyApplication.alarmClock_five);
			boolean b = doWorkendDay(MyApplication.alarmClock_five);
			boolean c = doEveryDay(MyApplication.alarmClock_five);
			String str5 = "";
			if (a) {
				str5 = str5
						+ getResources().getString(R.string.alarmClock_work);
			}
			if (b) {
				str5 = str5
						+ getResources().getString(R.string.alarmClock_workend);
			}
			if (c) {
				str5 = str5
						+ getResources()
								.getString(R.string.alarmClock_everyday);
			}
			timeBtn_FIVE.setText(MyApplication.alarmClock_fiveTime);
			parameter_clock_fiveWeeks_txt.setText(str5);
			SharedPreferences s5 = getSharedPreferences("alarmClock_info5",
					Context.MODE_PRIVATE);
			s5.edit().putString("alarmClock_work", "a").commit();
			s5.edit().putString("alarmClock_workend", "b").commit();
			s5.edit().putString("alarmClock_everyday", "c").commit();
		}
	}

	private boolean doEveryDay(String str) {
		if (str.contains(getResources().getString(R.string.weeks_sunday))
				&& str.contains(getResources().getString(
						R.string.weeks_Saturday))
				&& str.contains(getResources().getString(R.string.weeks_monday))
				&& str.contains(getResources()
						.getString(R.string.weeks_tuesday))
				&& str.contains(getResources().getString(
						R.string.weeks_wednesday))
				&& str.contains(getResources().getString(
						R.string.weeks_thursday))
				&& str.contains(getResources().getString(R.string.weeks_friday))) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 周末
	 * */
	private boolean doWorkendDay(String str) {
		if (str.contains(getResources().getString(R.string.weeks_sunday))
				&& str.contains(getResources().getString(
						R.string.weeks_Saturday))
				&& !str.contains(getResources()
						.getString(R.string.weeks_monday))
				&& !str.contains(getResources().getString(
						R.string.weeks_tuesday))
				&& !str.contains(getResources().getString(
						R.string.weeks_wednesday))
				&& !str.contains(getResources().getString(
						R.string.weeks_thursday))
				&& !str.contains(getResources()
						.getString(R.string.weeks_friday))) {
			return true;
		} else
			return false;
	}

	/**
	 * 工作日
	 * */
	private boolean doWorkDay(String str) {
		if (!str.contains(getResources().getString(R.string.weeks_sunday))
				&& !str.contains(getResources().getString(
						R.string.weeks_Saturday))
				&& str.contains(getResources().getString(R.string.weeks_monday))
				&& str.contains(getResources()
						.getString(R.string.weeks_tuesday))
				&& str.contains(getResources().getString(
						R.string.weeks_wednesday))
				&& str.contains(getResources().getString(
						R.string.weeks_thursday))
				&& str.contains(getResources().getString(R.string.weeks_friday))) {
			return true;
		} else
			return false;
	}
}
