package com.YHJstyle.b005.jump;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ThreadPoolExecutor;

import com.YHJstyle.b005.R;
import com.YHJstyle.b005.b005.view.DataTabView;
import com.YHJstyle.b005.b005.view.HomeRoundView;
import com.YHJstyle.b005.b005.view.ProgressCharView;
import com.YHJstyle.b005.b005.view.RoundView;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarData;
import com.YHJstyle.b005.j_style_Pro.b005.calendar.CalendarUtil;
import com.YHJstyle.b005.j_style_Pro.b005.entity.PedoMeter;
import com.YHJstyle.b005.j_style_Pro.b005.tool.MyApplication;
import com.YHJstyle.b005.view.b005.layout.ShowDataView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class ProgressActivity extends Activity implements OnClickListener {
	public static final String TAB_CALORIES = "calories";
	public static final String TAB_DISTANCE = "distance";
	public static final String TAB_GOAL = "gaol";
	public static final String TAB_SLEEP = "sleep";
	public static final String TAB_STEPS = "steps";
	public static final String TAB_TIME = "time";
	public static final int TYPE_MONTH = 1;
	public static final int TYPE_WEEK = 0;
	public static final int TYPE_YEAR = 2;
	public static int dateType = 0;
	private int dataGoald;
	private LinearLayout dateLayout;
	private String endStr = "";
	private RelativeLayout layout;
	private LinearLayout layout1;
	private LinearLayout layout2;
	private LinearLayout layout3;
	private LinearLayout layout4;
	private LinearLayout layout5;
	private CalendarUtil mCalendar;
	private CalendarData mCalendarData;
	private DataTabView mDateTabView;// ***
	private TextView mEndDate;
	private ArrayList<PedoMeter> mPedoMeter = new ArrayList<PedoMeter>();
	private ShowDataView mShowData;// 显示时间 步数 卡路里 etn
	private TextView mStarDate;
	private TabHost mTabHost;
	private ImageView monthBtn;
	private RoundView proRoundView;// 完成率的圆
	private LinearLayout progressDataLayout;
	private RadioGroup radioGroup;
	private String startStr = "";
	private ImageButton weekBtn;
	private ImageButton yearBtn;
	public String[] xWeekLable;
	public String[] xYearLable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_page);
		initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mShowData.setUnit();
		mShowData.showData(CalendarUtil.getTotalData(mPedoMeter));
	}

	private void initView() {
		xWeekLable = new String[] {
				getResources().getString(R.string.date_list_mon),
				getResources().getString(R.string.date_list_tue),
				getResources().getString(R.string.date_list_wed),
				getResources().getString(R.string.date_list_thu),
				getResources().getString(R.string.date_list_fri),
				getResources().getString(R.string.date_list_sat),
				getResources().getString(R.string.date_list_sun) };
		xYearLable = new String[] {
				getResources().getString(R.string.date_tab_month_jan),
				getResources().getString(R.string.date_tab_month_feb),
				getResources().getString(R.string.date_tab_month_mar),
				getResources().getString(R.string.date_tab_month_apr),
				getResources().getString(R.string.date_tab_month_may),
				getResources().getString(R.string.date_tab_month_jun),
				getResources().getString(R.string.date_tab_month_jul),
				getResources().getString(R.string.date_tab_month_aug),
				getResources().getString(R.string.date_tab_month_sep),
				getResources().getString(R.string.date_tab_month_oct),
				getResources().getString(R.string.date_tab_month_nov),
				getResources().getString(R.string.date_tab_month_dec) };
		mCalendar = new CalendarUtil();
		progressDataLayout = (LinearLayout) findViewById(R.id.show_progress_data_layout);
		// 展示数据、、历程1
		mShowData = new ShowDataView(this, this.progressDataLayout, 1);
		// 图形绘表
		dateLayout = (LinearLayout) findViewById(R.id.progress_date_layout);
		mCalendarData = new CalendarData();
		mDateTabView = new DataTabView(this, this.mCalendarData, 1);
		mDateTabView.setShowData(mShowData);
		dateLayout.addView(mDateTabView);
		weekBtn = (ImageButton) findViewById(R.id.week_ib);
		weekBtn.setOnClickListener(this);
		monthBtn = (ImageButton) findViewById(R.id.month_ib);
		monthBtn.setOnClickListener(this);
		yearBtn = (ImageButton) findViewById(R.id.year_ib);
		yearBtn.setOnClickListener(this);
		mStarDate = (TextView) findViewById(R.id.year_month_day_star);
		mEndDate = (TextView) findViewById(R.id.year_month_day_end);
		startStr = mCalendar.getMondayOFWeek();
		endStr = mCalendar.getCurrentWeekday();
		mStarDate.setText(startStr);
		mEndDate.setText(endStr);
		mPedoMeter = CalendarUtil.getPedometerDate(CalendarUtil
				.getBetweenDataString(startStr, endStr));
		// Log.i("APP", "mPedoMeter = 创建了几个"+mPedoMeter.size());
		mShowData.setUnit();
		mShowData.showData(CalendarUtil.getTotalData(mPedoMeter));
		initTab();
		initDateView();
		mDateTabView.setCharLayout(layout, layout1, layout2, layout3, layout4,
				layout5);

	}

	private void initDateView() {
		switch (dateType) {
		case TYPE_MONTH:
			monthBtn.setBackgroundResource(R.drawable.month_pressed_btn);
			weekBtn.setBackgroundResource(R.drawable.week_btn);
			yearBtn.setBackgroundResource(R.drawable.year_btn);
			break;
		case TYPE_WEEK:
			weekBtn.setBackgroundResource(R.drawable.week_pressed_btn);
			monthBtn.setBackgroundResource(R.drawable.month_btn);
			yearBtn.setBackgroundResource(R.drawable.year_btn);
			break;
		case TYPE_YEAR:
			yearBtn.setBackgroundResource(R.drawable.year_pressed_btn);
			weekBtn.setBackgroundResource(R.drawable.week_btn);
			monthBtn.setBackgroundResource(R.drawable.month_btn);
			break;
		}

	}

	private void initTab() {
		mTabHost = (TabHost) findViewById(R.id.tabs);
		mTabHost.setup();
		layout = (RelativeLayout) findViewById(R.id.layout6);
		dataGoald = CalendarUtil.getTotalData(mPedoMeter)[6] / 7;
		// 目标达成率 圆
		// Log.i("APP", "dataGoald = "+dataGoald);
		proRoundView = new HomeRoundView(this, null, layout, 0, dataGoald);
		layout.addView(proRoundView);
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		layout3 = (LinearLayout) findViewById(R.id.layout3);
		layout4 = (LinearLayout) findViewById(R.id.layout4);
		layout5 = (LinearLayout) findViewById(R.id.layout5);
		addView();
		mTabHost.addTab(mTabHost.newTabSpec("steps").setIndicator("tab1")
				.setContent(R.id.layout1));
		mTabHost.addTab(mTabHost.newTabSpec("time").setIndicator("tab2")
				.setContent(R.id.layout2));
		mTabHost.addTab(mTabHost.newTabSpec("distance").setIndicator("tab3")
				.setContent(R.id.layout3));
		mTabHost.addTab(mTabHost.newTabSpec("calories").setIndicator("tab4")
				.setContent(R.id.layout4));
		mTabHost.addTab(mTabHost.newTabSpec("sleep").setIndicator("tab5")
				.setContent(R.id.layout5));
		mTabHost.addTab(mTabHost.newTabSpec("goal").setIndicator("tab6")
				.setContent(R.id.layout6));
		int i = MyApplication.getInstance().getScreenWidth() / 6;
		((RadioButton) findViewById(R.id.steps)).setWidth(i);
		((RadioButton) findViewById(R.id.time)).setWidth(i);
		((RadioButton) findViewById(R.id.distance)).setWidth(i);
		((RadioButton) findViewById(R.id.calories)).setWidth(i);
		((RadioButton) findViewById(R.id.sleep)).setWidth(i);
		((RadioButton) findViewById(R.id.goal)).setWidth(i);
		radioGroup = (RadioGroup) findViewById(R.id.progerss_radiogroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.steps:
					ProgressActivity.this.mTabHost.setCurrentTabByTag("steps");
					break;
				case R.id.time:
					ProgressActivity.this.mTabHost.setCurrentTabByTag("time");
					break;
				case R.id.distance:
					ProgressActivity.this.mTabHost
							.setCurrentTabByTag("distance");
					break;
				case R.id.calories:
					ProgressActivity.this.mTabHost
							.setCurrentTabByTag("calories");
					break;
				case R.id.sleep:
					ProgressActivity.this.mTabHost.setCurrentTabByTag("sleep");
					break;
				case R.id.goal:
					ProgressActivity.this.mTabHost.setCurrentTabByTag("goal");
					break;
				}
			}
		});

	}

	private void addView() {
		proRoundView = new HomeRoundView(this, null, layout, 0, this.dataGoald);
		layout.addView(proRoundView);
		layout1.addView(new ProgressCharView(xWeekLable, xYearLable).execute(
				this, dateType, 0, "steps", mPedoMeter));
		layout2.addView(new ProgressCharView(xWeekLable, xYearLable).execute(
				this, dateType, 1, "time", mPedoMeter));
		layout3.addView(new ProgressCharView(xWeekLable, xYearLable).execute(
				this, dateType, 2, "distance", mPedoMeter));
		layout4.addView(new ProgressCharView(xWeekLable, xYearLable).execute(
				this, dateType, 3, "calories", mPedoMeter));
		layout5.addView(new ProgressCharView(xWeekLable, xYearLable).execute(
				this, dateType, 4, "sleep", mPedoMeter));

	}

	public static int getDateType() {
		return dateType;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.week_ib:
			MyApplication.isYearBtn = false;
			dateType = 0;
			startStr = mCalendar.getMondayOFWeek();
			endStr = mCalendar.getCurrentWeekday();
			onTabButton();
			break;
		case R.id.month_ib:
			MyApplication.isYearBtn = false;
			dateType = 1;
			startStr = mCalendar.getFirstDayOfMonth();
			endStr = mCalendar.getDefaultDay();
			onTabButton();
			break;
		case R.id.year_ib:
			MyApplication.isYearBtn = true;
			dateType = 2;
			startStr = mCalendar.getCurrentYearFirst();
			endStr = mCalendar.getCurrentYearEnd();
			mStarDate.setText(startStr);
			mEndDate.setText(endStr);
			dataGoald = CalendarUtil.getTotalData(mPedoMeter)[6] / 365;
			onTabYear();
			break;
		}

	}

	/**
	 * 按钮点击
	 * */
	private void onTabButton() {
		mStarDate.setText(startStr);
		mEndDate.setText(endStr);
		mPedoMeter = CalendarUtil.getPedometerDate(CalendarUtil
				.getBetweenDataString(startStr, endStr));
		// Log.i("APP", "mPedoMeter = " + mPedoMeter.size());
		if (dateType != TYPE_WEEK) {
			dataGoald = CalendarUtil.getTotalData(mPedoMeter)[6] / 31;
		} else {
			dataGoald = CalendarUtil.getTotalData(mPedoMeter)[6] / 7;
		}
		changeCharView();

	}

	// 年
	private void onTabYear() {
		Calendar calendar1 = Calendar.getInstance();
		if (mCalendarData == null) {
			mCalendarData = new CalendarData();
		}
		calendar1.set(Calendar.YEAR,
				Integer.parseInt(mCalendarData.getCurrentYear()));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		mPedoMeter.clear();
		for (int i = 0; i < 12; i++) {
			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(Calendar.MONTH, i);
			int j = calendar2.getActualMaximum(Calendar.DATE);// 传入的参数代表的意思（年、月、周等）查询当前（年、月、周）拥有的最大值
			calendar2.set(Calendar.DATE, 1);
			String str = simpleDateFormat.format(calendar2.getTime());
			calendar2.set(Calendar.DATE, j);
			ArrayList arrayList = CalendarUtil.getPedometerDate(CalendarUtil
					.getBetweenDataString(str,
							simpleDateFormat.format(calendar2.getTime())));
			mPedoMeter.add(CalendarUtil.getTotalYearData(arrayList));
		}
		changeCharView();

	}

	private void changeCharView() {
		if ((mShowData == null) && (proRoundView == null)) {
			progressDataLayout = (LinearLayout) findViewById(R.id.show_progress_data_layout);
			mShowData = new ShowDataView(this, progressDataLayout, 1);
			proRoundView = new HomeRoundView(this, null, layout, 0, dataGoald);

		}
		mShowData.setUnit();
		mShowData.showData(CalendarUtil.getTotalData(mPedoMeter));
		initDateView();
		layout.removeView(proRoundView);
		layout1.removeAllViews();
		layout2.removeAllViews();
		layout3.removeAllViews();
		layout4.removeAllViews();
		layout5.removeAllViews();
		addView();
		layout.invalidate();
		layout1.invalidate();
		layout2.invalidate();
		layout3.invalidate();
		layout4.invalidate();
		layout5.invalidate();
	}
}
