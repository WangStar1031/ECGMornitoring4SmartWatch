package com.YHJstyle.b005.j_style_Pro.b005.tool;

import java.util.List;

import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.util.DisplayMetrics;
//import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyApplication extends Application {
	
	//英制公制转换
//	public static boolean kmORIm = false;
	
	public static boolean hearttrue = false;//心电接收
	public static final int GOUND_ING = 1;
	public static final int GOUND_INITE = 0;
	public static final int HRP_PROFILE_CONNECTED = 20;
	public static final int HRP_PROFILE_DISCONNECTED = 21;
	public static String ParameterClock;
	public static String ParameterFatigue;
	public static final int STATE_OFF = 10;
	public static final int STATE_READY = 10;
	//连接蓝牙时候的状态
	public static boolean STATE = false;
	//
	
	//公制美制判断 默认true mile
	public static boolean isUSA = true;
	
//	private static final String TAG = "HomePageActivity";
	public static String alarmClock_five;
	public static String alarmClock_fiveRemind ;
	public static String alarmClock_fiveTime;
	public static String alarmClock_four;
	public static String alarmClock_fourRemind;
	public static String alarmClock_fourTime;
	public static String alarmClock_one;
	public static String alarmClock_oneRemind;
	public static String alarmClock_oneTime;
	public static String alarmClock_three;
	public static String alarmClock_threeRemind;
	public static String alarmClock_threeTime;
	public static String alarmClock_two;
	public static String alarmClock_twoRemind;
	public static String alarmClock_twoTime;
	public static int application_Hours;
	public static int application_Minutes;
	public static Button bt1;
	public static Button bt2;
	public static Button bt3;
	public static byte[] clockRemindFive;
	public static byte[] clockRemindFour;
	public static byte[] clockRemindOne;
	public static byte[] clockRemindThree;
	public static byte[] clockRemindTwo;
	public static Button connectBt;
	public static int cursorSteps;
	
	public static RelativeLayout dataGoaldLayout ;
	public static boolean homepageStartOrStop = false;
	
	
	public static LinearLayout dataLayout;
	
	public static String deviceAddrass;
	public static List<BluetoothDevice> deviceList;
	public static byte[] fatigueRemind;
	public static boolean firstRound;
	public static int goaldData;
	
	public static RelativeLayout homeProgressLayout;
	
	public static boolean isAlarmClock_FIVE;
	public static boolean isAlarmClock_FOUR;
	public static boolean isAlarmClock_ONE;
	public static boolean isAlarmClock_THREE;
	public static boolean isAlarmClock_TWO;
	public static boolean isBluetoothConnection;
	public static boolean isFirst;
	public static boolean isParameterFatigue;
	public static boolean isYearBtn;
	public static boolean isYearBtn_add;
	
	public static boolean homeboolean;//跳到settingActivity加一个判断
	
	public static BluetoothDevice mDevice;
	// 为了实现每次使用该类时不创建新的对象而创建的静态对象
	private static MyApplication mInstance;
	public static int mState = 21;
	public static int saveDataTimes;
	public static int sleepTotalTime;
	public static String sportStep;
	public static String userAge;
	public static String userHeight;
	public static String userSex;
	public static String userSteps;
	public static String userWeight;
	public static String yhDevice;
	private String action = "com.youhong.j_style_Pro.tool.myservice";
	public boolean isBind = false;
	private float mDensity;
	public MyHandler mHandler;
	public PedoMeter mPedoMeter;
//	public MyService mService ;
	public BluetoothLeService mService  = null;
	public static String saveCurDate;
	//设置里面个人信息的textview
	public static  EditText heightEt ;
	public static  EditText lengthEt ;
	public static  EditText weightEt;
	public static  EditText agetEt;
    public static boolean isMale =false;
//	public static boolean flag;//判断home界面蓝牙连接状态

    public static Button femaleBtn;
    public static Button maleBtn;
    
    
    
    //判断英制 公制
//    public static boolean isINch  = false;
	static {
		homeboolean = false;
		isYearBtn = false;
		isYearBtn_add = false;
		mDevice = null;
		isBluetoothConnection = false;
		sleepTotalTime = 0;
		userHeight = "";
		userWeight = "";
		userSteps = "";
		userAge = "";
		userSex = "";
		sportStep = "";
		cursorSteps = 0;
		isFirst = true;
		firstRound = true;
		goaldData = 0;
		alarmClock_one = null;
		alarmClock_two = null;
		alarmClock_three = null;
		alarmClock_four = null;
		alarmClock_five = null;
		alarmClock_oneTime = "00:00";
		alarmClock_twoTime = "00:00";
		alarmClock_threeTime = "00:00";
		alarmClock_fourTime = "00:00";
		alarmClock_fiveTime = "00:00";
		alarmClock_oneRemind = null;
		alarmClock_twoRemind = null;
		alarmClock_threeRemind = null;
		alarmClock_fourRemind = null;
		
		saveCurDate = "";
	}
	
	//关闭同步数据
	public static void CloseLoadingProgress(){
		if((homeProgressLayout != null ) &&(bt1 !=null) &&(bt2 != null)&&(bt3 != null)){
			homeProgressLayout.setVisibility(View.INVISIBLE);
			bt1.setClickable(true);
			bt2.setClickable(true);
			bt3.setClickable(true);
		}
	}
	
	//显示同步数据
	public static void showLoadingProgress(){
		if((homeProgressLayout != null ) &&(bt1 !=null) &&(bt2 != null)&&(bt3 != null)){
			homeProgressLayout.setVisibility(View.VISIBLE);
			bt1.setClickable(false);
			bt2.setClickable(false);
			bt3.setClickable(false);
		}
	}
	
	/*时间转换 *
	 * 判断是否小于10 
	 * 
	 * */
	
	public static String timeConversion(int i, int j){
		String str1,str2;
		if(i <10){
			str1 = "0"+i;
			}else{
				str1 = String.valueOf(i);
			}
		
		if(j>=10){
			str2 = String.valueOf(j);
		}else{
			str2 = "0" +j;
		}
		return str1+ ":" +str2;
	}
	
	public float getDensity(){
		return this.mDensity;
	}
	
	@SuppressWarnings("deprecation")
	public int getScreenWidth() {
		return ((WindowManager) getBaseContext().getSystemService("window"))
				.getDefaultDisplay().getWidth();
		// return
		// ((WindowManager)getBaseContext().getSystemService("window")).getDefaultDisplay().`;
	}
	
	public static MyApplication getInstance(){
		MyApplication myApplication = mInstance;
		if(mInstance == null){
			mInstance = new MyApplication();
		}
		return mInstance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		mPedoMeter = new PedoMeter();
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		
		mDensity = dm.density;
		deviceAddrass =SaveDataBase.getSaveDate(this, "deviceAddrass", "");
//		Log.i("APP", "deviceAddrass=="+deviceAddrass.toString());
	}
}
