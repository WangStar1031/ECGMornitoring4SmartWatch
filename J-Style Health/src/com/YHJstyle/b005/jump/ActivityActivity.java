package com.YHJstyle.b005.jump;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.ActivityRoundView;
import com.YHJstyle.b005.b005.view.DataTabView;
import com.YHJstyle.b005.b005.view.DatelistAdapter;
import com.YHJstyle.b005.b005.view.RoundView;
import com.YHJstyle.b005.j_style_Pro.b005.adapter.DateListAdapter;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarUtil;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData.OnMonthChangeListener;
import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.j_style_Pro.b005.tool.SaveDataBase;
import com.YHJstyle.b005.view.b005.layout.ShowDataView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ActivityActivity extends Activity implements OnMonthChangeListener {

	private LinearLayout activityComLayout;
	private LinearLayout activityDateLayout;
	private RelativeLayout activityRoundLayout;
	private LinearLayout activityYearAndMonth;
	private DatelistAdapter mAdapter;
	private CalendarData mCalendarData;
	private ListView mListView;
	private PedoMeter mPedoMeter;
	private ShowDataView mShowData;
	private RoundView mView;
	private LinearLayout sleepViewLayout;
	//后期加入
	private int replace;
	private CalendarData replaceData;
	private PedoMeter replacePedoMeter;
	
	
	
	private void getDefautDate() {
		int[] varInt = CalendarUtil.splitNowTime(CalendarUtil
				.getNowTime("yyyy-MM-dd"));
		mPedoMeter = SaveDataBase.getPeodeterInfo(this, varInt[0], varInt[1],
				varInt[2]);
		if (mPedoMeter == null) {
			mPedoMeter = new PedoMeter();
		}
		replaceData = mCalendarData;
		replacePedoMeter = mPedoMeter;
		
		// 数据显示 0为在Activity中
		mShowData.setUnit();
		mShowData.quiryDate(mCalendarData, 0, mPedoMeter);

		activityRoundLayout.removeView(mView);
		mView = new ActivityRoundView(this, null, sleepViewLayout,
				mPedoMeter.dailyGoal);
		activityRoundLayout.addView(mView);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// if(MyApplication.isUSA)
		// MyApplication.textUnit = getString(R.string.distance_eunit);
		// else
		// MyApplication.textUnit = getString(R.string.distance_unit);
		mShowData.setUnit();
		mShowData.quiryDate(replaceData, replace, replacePedoMeter);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page);
		initView();
	}

	private void initView() {
		mPedoMeter = new PedoMeter();
		activityRoundLayout = ((RelativeLayout) findViewById(R.id.activity_round_layout));
		activityComLayout = (LinearLayout) findViewById(R.id.activity_com_data_layout);
		sleepViewLayout = (LinearLayout) findViewById(R.id.sleep_view);
		activityYearAndMonth = (LinearLayout) findViewById(R.id.year_month_tab);
		mCalendarData = new CalendarData();
		activityYearAndMonth.addView(new DataTabView(this, this.mCalendarData,
				0));
		mView = new ActivityRoundView(this, null, sleepViewLayout, 0);
		activityRoundLayout.addView(mView);
		activityRoundLayout.setPadding(0, 0, 0, 0);
		mCalendarData.setOnMonthChangeListener(this);
		mListView = (ListView) findViewById(R.id.data_list);
		mAdapter = new DatelistAdapter(this, this.mCalendarData);
		mListView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		// 点击时 显示当天的的时间和数据
		int i = CalendarUtil
				.splitNowTime(CalendarUtil.getNowTime("yyyy-MM-dd"))[2];
		// 显示位置
		mListView.setSelection(i - 1);
		mListView.setSelector(R.drawable.bg_list_cell_selected);
		activityDateLayout = (LinearLayout) findViewById(R.id.activity_data_layout);
		mShowData = new ShowDataView(this, this.activityDateLayout, 0);
		replace = 0;
		getDefautDate();
		onClickItem();
	}

	private void onClickItem() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				mAdapter.setPosition(position);
				mAdapter.notifyDataSetChanged();
				mPedoMeter = SaveDataBase.getPeodeterInfo(
						ActivityActivity.this,
						Integer.valueOf(mCalendarData.getCurrentYear())
								.intValue(), Integer.parseInt(mCalendarData
								.getCurrentMonth()), position + 1);
				if (mPedoMeter == null) {
					mPedoMeter = new PedoMeter();
				}
				mShowData.setUnit();
				mShowData.quiryDate(mCalendarData, position, mPedoMeter);
				
				replace = position;
				replaceData = mCalendarData;
				replacePedoMeter = mPedoMeter;
				
				activityRoundLayout.removeView(mView);
				mView = new ActivityRoundView(ActivityActivity.this, null,
						sleepViewLayout, mPedoMeter.dailyGoal);
				activityRoundLayout.addView(mView);
				activityRoundLayout.setPadding(0, 0, 0, 0);
				activityRoundLayout.invalidate();

			}
		});
	}

	@Override
	public void onMonthChange() {
		this.mAdapter = new DatelistAdapter(this, this.mCalendarData);
		this.mListView.setAdapter(mAdapter);
		this.mAdapter.notifyDataSetChanged();

	}
}
