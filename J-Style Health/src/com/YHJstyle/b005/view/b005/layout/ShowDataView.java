package com.YHJstyle.b005.view.b005.layout;

import java.text.DecimalFormat;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData;
import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

	/**
	 *展示数据 时间 步数 卡路里。 
	 * */
public class ShowDataView extends LinearLayout {
	private static final int TYPE_ACTIVITY = 0;
	private static final int TYPE_PROGRESS = 1;
	private int[] image = new int[5];
	private Context mContext;
	private LinearLayout mDataLayout;
	private PedoMeter mPdeoMeter;
	private String[] showData = new String[5];
	private String[] tag = new String[5];
	private TextView[] tvData;
	private String[] unit = new String[5];
	private int viewType;
	private int widthCell;
	//公制英制切换
	private TextView[] changeUnit;
	public ShowDataView(Context context, LinearLayout mLinearLayout,
			int paramInt) {
		super(context);
		this.mContext = context;
		this.viewType = paramInt;
		this.mDataLayout = mLinearLayout;
		this.mPdeoMeter = new PedoMeter();
		this.tvData = new TextView[6];
		//后面i加入
		this.changeUnit = new  TextView[6];
		initData();
		initView();
	}

	private String getStr(int i) {
		return getResources().getString(i);
	}

	private void initView() {
		for(int i =0; i<5; i++){
			View view = LayoutInflater.from(this.mContext).inflate(R.layout.show_data_view, null);
			view.setLayoutParams(new LinearLayout.LayoutParams(this.widthCell,LinearLayout.LayoutParams.WRAP_CONTENT));
			((ImageView)view.findViewById(R.id.tag_iv)).
			setImageResource(this.image[i]);
			((TextView)view.findViewById(R.id.tag_textView)).setText(tag[i]);
			//
//			((TextView)view.findViewById(R.id.unit_textView)).setText(unit[i]);
			changeUnit[i] = (TextView)view.findViewById(R.id.unit_textView);
			tvData[i] = ((TextView)view.findViewById(R.id.data_textView));
			mDataLayout.addView(view);
		}
		
	}

	private void initData() {
		int i = MyApplication.getInstance().getScreenWidth();
		switch (this.viewType) {
		
		case TYPE_PROGRESS:
			this.widthCell = i / 5;
			break;

		case TYPE_ACTIVITY://历程的时候
			this.widthCell = (-4 + (-4 + (i - i * 16 / 100)) / 5);
			break;
		}
		setDrawView();
		setTag();
		//
//		setUnit();

	}

	public void setUnit() {
		this.unit[0]  = getStr(R.string.time_unit);
		this.unit[1]  = getStr(R.string.steps_unit);
		this.unit[2]  = getStr(R.string.calories_unit);
		if(MyApplication.isUSA){
			this.unit[3]  = getStr(R.string.distance_eunit);
//			Log.i("time", "美制");
		}else{
			this.unit[3]  = getStr(R.string.distance_unit);
//			Log.i("time", "英制");
		}
//		if(MyApplication.isUSA)
//			MyApplication.textUnit = getStr(R.string.distance_eunit);
//		else
//			MyApplication.textUnit = getStr(R.string.distance_unit);
//		this.unit[3] = MyApplication.textUnit;
//		Log.i("time", "运行到换单位");
		
		
		
		this.unit[4]  = getStr(R.string.time_unit);
		for(int i = 0; i< 5; i++){
			changeUnit[i].setText(unit[i]);
		}
	}

	private void setTag() {
		this.tag[0] = getStr(R.string.progress_time_tab);
		this.tag[1] = getStr(R.string.progress_steps_tab);
		this.tag[2] = getStr(R.string.progress_calories_tab);
		this.tag[3] = getStr(R.string.progress_distance_tab);
		this.tag[4] = getStr(R.string.progress_slept_tab);

	}

	private void setDrawView() {
		this.image[0] = R.drawable.activity_time;
		this.image[1] = R.drawable.activity_steps;
		this.image[2] = R.drawable.activity_calories;
		this.image[3] = R.drawable.activity_distance;
		this.image[4] = R.drawable.slept_icon;
	}
	
	/**
	 *查询数据
	 * */
	public void quiryDate(CalendarData calendarData, int var,PedoMeter pedoMeter){
		this.mPdeoMeter = pedoMeter;
		setShowData();
		for(int i = 0; i< 5; i++){
			tvData[i].setText(showData[i]);
		}
	}
	
	
	
	/**
	 *设置显示的数据
	 * */
	private void setShowData() {
		String str1 = String.valueOf(mPdeoMeter.activityTtime/60);
		String str2 = String.valueOf(mPdeoMeter.activityTtime % 60);
		if(str1.length() ==1){
			str1 = "0" + str1;
		}
		if(str2.length() ==1){
			str2 = "0" + str2;
		}
		
		showData[0] = String.valueOf(str1 +":" +str2);
		showData[1] = String.valueOf(mPdeoMeter.steps);
		DecimalFormat localDecimalFormat1 = new DecimalFormat("#.#");
	    this.showData[2] = String.valueOf(localDecimalFormat1.format(mPdeoMeter.calories / 100.0D));
//	    Log.e("=====", "showData[3]   " + mPdeoMeter.fuel);
	    DecimalFormat localDecimalFormat2 = new DecimalFormat("#.##");
	    if(MyApplication.isUSA){
	    	
	    	this.showData[3] = String.valueOf(localDecimalFormat2.format(mPdeoMeter.distance * 621 / 10000.0D));
	    }else
	    	this.showData[3] = String.valueOf(localDecimalFormat2.format(mPdeoMeter.distance / 100.0D));
	    
	    String str3 = String.valueOf(mPdeoMeter.sleepTime / 60);
	    String str4 = String.valueOf(mPdeoMeter.sleepTime % 60);
	    if (str3.length() == 1) {
	      str3 = "0" + str3;
	    }
	    if (str4.length() == 1) {
	      str4 = "0" + str4;
	    }
	    this.showData[4] = String.valueOf(str3 + ":" + str4);
		
	}
	
	/**
	 * 查询某天的时候
	 * 显示出来的数据
	 * */
	public void showData(int[] paramArrayOfInt)
	  {
	    if (paramArrayOfInt.length != 0)
	    {
	      String str1 = String.valueOf(paramArrayOfInt[0] / 60);
	      String str2 = String.valueOf(paramArrayOfInt[0] % 60);
	      if (str1.length() == 1) {
	        str1 = "0" + str1;
	      }
	      if (str2.length() == 1) {
	        str2 = "0" + str2;
	      }
	      this.tvData[0].setText(str1 + ":" + str2);
	      this.tvData[1].setText(String.valueOf(paramArrayOfInt[1]).trim());
	      DecimalFormat localDecimalFormat1 = new DecimalFormat("#.#");
	      this.tvData[2].setText(String.valueOf(localDecimalFormat1.format(paramArrayOfInt[2] / 100.0D)));
	      DecimalFormat localDecimalFormat2 = new DecimalFormat("#.##");
	      if(MyApplication.isUSA){
	    	  this.tvData[3].setText(String.valueOf(localDecimalFormat2.format(paramArrayOfInt[4] * 621/ 10000.0D)));
	      }else{
	    	  this.tvData[3].setText(String.valueOf(localDecimalFormat2.format(paramArrayOfInt[4] / 100.0D)));
	      }
	      
	      String str3 = String.valueOf(paramArrayOfInt[5] / 60);
	      String str4 = String.valueOf(paramArrayOfInt[5] % 60);
	      if (str3.length() == 1) {
	        str3 = "0" + str3;
	      }
	      if (str4.length() == 1) {
	        str4 = "0" + str4;
	      }
	      this.tvData[4].setText(str3 + ":" + str4);
	    }
	  }
	
}
