package com.YHJstyle.b005.jump;

import java.util.ArrayList;
import java.util.Arrays;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.CharSleepView;
import com.YHJstyle.b005.b005.view.DataTabView;
import com.YHJstyle.b005.b005.view.DatelistAdapter;
import com.YHJstyle.b005.b005.view.RoundView;
import com.YHJstyle.b005.b005.view.SleepRoundGoalView;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarUtil;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData.OnMonthChangeListener;
import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;
import com.YHJstyle.b005.j_style_Pro.b005.tool.SaveDataBase;

import android.app.Activity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SleepActivity extends Activity implements OnMonthChangeListener {
	private PedoMeter mPdemoMeter;//早
	private PedoMeter apPedometer;//晚
//	private CharSleepView charView;
	private int dayGoald;
	private DatelistAdapter mAdapter;
	private CalendarData mCalendarData;
	private CharSleepView mChar;
	private ListView mListView;
	private RoundView mRoundView;
	private TextView ringText;
	private TextView ringText2;
	private LinearLayout sleepDataLayout;
	private LinearLayout sleepYearAndMonth;
	private LinearLayout sleepCharLayout;
	private RelativeLayout sleepRoundLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sleep_page);
		initView();
	}

	private void initView() {
		mPdemoMeter = new PedoMeter();
		apPedometer = new PedoMeter();
		mChar = new CharSleepView(this);
		sleepCharLayout = (LinearLayout) findViewById(R.id.sleep_char_layout);
		sleepRoundLayout = (RelativeLayout) findViewById(R.id.sleep_round_layout);
		sleepDataLayout = (LinearLayout) findViewById(R.id.sleep_data_layout);
		sleepYearAndMonth = (LinearLayout) findViewById(R.id.year_month_tab);
		mCalendarData = new CalendarData();
		sleepYearAndMonth.addView(new DataTabView(this, mCalendarData, 0));
		getDefaultData();
		mCalendarData.setOnMonthChangeListener(this);
		mListView = (ListView) findViewById(R.id.data_list);
		mAdapter = new DatelistAdapter(this, mCalendarData);
		mListView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		onClikcItem();
		int i = CalendarUtil.splitNowTime(CalendarUtil.getNowTime("yyyy-MM-dd"))[2];
		mListView.setSelection(i -1);
		ringText = (TextView) findViewById(R.id.sleep_accom_text);
		ringText2 = (TextView) findViewById(R.id.sleep_accom_text2);
		

	}

	private void onClikcItem() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAdapter.setPosition(position);
				mAdapter.notifyDataSetChanged();
				mPdemoMeter = SaveDataBase.getPeodeterInfo(SleepActivity.this, Integer.parseInt(mCalendarData.getCurrentYear()), Integer.parseInt(mCalendarData.getCurrentMonth()), position+1);
				ArrayList arrayList = CalendarUtil.getTheDayAfter(Integer.parseInt(mCalendarData.getCurrentYear()), Integer.parseInt(mCalendarData.getCurrentMonth()), position+1);
				apPedometer = SaveDataBase.getPeodeterInfo(SleepActivity.this, ((Integer)arrayList.get(0)).intValue(), ((Integer)arrayList.get(1)).intValue(), ((Integer)arrayList.get(2)).intValue());
				sleepCharLayout.removeAllViews();
				sleepCharLayout.removeView(mChar.getView());
				
				mChar.setDATE(mPdemoMeter.slept, apPedometer.slept);
				
//				Log.i("APP", " mPdemoMeter.slept="+Arrays.toString(mPdemoMeter.slept));
//				Log.i("APP", " aPdemoMeter.slept="+Arrays.toString(apPedometer.slept));
				
				sleepCharLayout.addView(mChar.getView());
				sleepCharLayout.invalidate();
				
				int i = (int)(100.0D * (CharSleepView.currentBestSleepTime / CharSleepView.currentSleepTime));
				
				if((CharSleepView.currentBestSleepTime == 0.0D) && (CharSleepView.currentSleepTime  == 0.0D)){
					i = 0;
				}
//				Log.i("Test", "best = "+CharSleepView.currentBestSleepTime + "sleep=" + CharSleepView.currentSleepTime);
//				Log.i("Test", "百分比 i = "+ i);
				sleepRoundLayout.removeView(mRoundView);
				sleepRoundLayout.removeView(view);
				sleepRoundLayout.removeView(view);
				mRoundView = new SleepRoundGoalView(SleepActivity.this, null, sleepDataLayout, i, CharSleepView.currentBestSleepTime, CharSleepView.currentSleepTime);
				sleepRoundLayout.addView(mRoundView);
			}
		});
	}
	
	/*
	 * 得到时间段的数据
	 **
	 */
	
	private void getDefaultData() {
		int[] arr = CalendarUtil.splitNowTime(CalendarUtil
				.getNowTime("yyyy-MM-dd"));
		mPdemoMeter = SaveDataBase
				.getPeodeterInfo(this, arr[0], arr[1], arr[2]);
		ArrayList list = new ArrayList();
		//后一天
		list = CalendarUtil.getTheDayAfter(arr[0], arr[1], arr[2]);
		apPedometer = SaveDataBase.getPeodeterInfo(this, ((Integer)list.get(0)).intValue(), ((Integer)list.get(1)).intValue(), ((Integer)list.get(2)).intValue());
		//设置柱状图形描述
		
		mChar.setDATE(mPdemoMeter.slept, apPedometer.slept);
		
		sleepCharLayout.addView(mChar.getView());
		int i = (int)( CharSleepView.currentBestSleepTime / CharSleepView.currentSleepTime);
		if((CharSleepView.currentBestSleepTime == 0.0d) && (CharSleepView.currentSleepTime ==  0.0d)){
			i = 0;
		}
		mRoundView = new SleepRoundGoalView(this, null, sleepDataLayout, i, CharSleepView.currentBestSleepTime, CharSleepView.currentSleepTime);
		sleepRoundLayout.addView(mRoundView);
	}

	@Override
	public void onMonthChange() {
		mAdapter  = new DatelistAdapter(this, mCalendarData);
		mListView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}
}
